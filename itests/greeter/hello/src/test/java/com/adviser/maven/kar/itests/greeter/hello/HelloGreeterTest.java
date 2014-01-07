package com.adviser.maven.kar.itests.greeter.hello;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerSuite;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import com.adviser.maven.kar.greeter.hello.HelloGreeter;
import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(value=PerSuite.class)
public class HelloGreeterTest {

    /** logger */
    private static final Logger LOG = Logger.getLogger(HelloGreeterTest.class.getName());
    /** feature name */
    private static final String FEATURE_NAME = "maven-kar-archive-greeter-hello";
    /** service class name */
    private static final String SERVICE_CLAZZ_NAME = GreeterService.class.getName();
    /** success delimiter */
    private static final String SUCCESS_DELIMITER = "\n########################################################################";

    /** features service */
    @Inject
    private FeaturesService featuresService;

    /** bundle context */
    @Inject
    private BundleContext context;

    /**
     * General test configuration setting up configurations.
     *
     * @return the configurations
     * @throws Exception
     *          on fail
     */
    @Configuration
    public static Option[] configure() throws Exception {
        return new Option[] {
                // provision and launch a container based on a Apache Karaf distribution
                karafDistributionConfiguration()
                        .frameworkUrl(
                                maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip").versionAsInProject())
                        .useDeployFolder(false).karafVersion("2.3.3").unpackDirectory(new File("target/pax/")),
                // if local maven repository is not found in ~/.m2
                // editConfigurationFilePut("org.ops4j.pax.url.mvn.cfg", "org.ops4j.pax.url.mvn.settings", "/home/hnunner/.m3/settings.xml"),
                // editConfigurationFilePut("org.ops4j.pax.url.mvn.cfg", "org.ops4j.pax.url.mvn.localRepository", "/home/hnunner/.m3/repository"),
                // keeps the runtime folder after finishing the tests
                keepRuntimeFolder(),
                // don't bother with local console output as it just ends up cluttering the logs
                // configureConsole().ignoreLocalConsole(),
                // default is WARN
                logLevel(LogLevel.INFO),
                // feature required by the following tests
                features(
                        maven().groupId("com.adviser.maven.kar").artifactId("maven-kar-archive-greeter-hello").type("xml")
                                .versionAsInProject(),
                                FEATURE_NAME)
                // test executes in another process, so Pax Exam must be launched with debugging enabled
                // debugConfiguration("5000", true),
        };
    }

    /**
     * Tests if the feature has been installed correctly.
     *
     * @throws Exception
     *          on fail
     */
    @Test
    public void featureTest() throws Exception {
        Feature greeterFeature = featuresService.getFeature(FEATURE_NAME);
        Assert.assertNotNull("Feature not found: " + FEATURE_NAME, greeterFeature);
        Assert.assertTrue("Feature not installed: " + FEATURE_NAME, featuresService.isInstalled(greeterFeature));
        logSuccess("Feature successfully installed: " + FEATURE_NAME);
    }

    /**
     * Tests if the service can be retrieved correctly.
     *
     * @throws Exception
     *          on fail
     */
    @Test
    public void serviceTest() throws Exception {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        ServiceTracker serviceTracker = new ServiceTracker(context, SERVICE_CLAZZ_NAME, null);

        serviceTracker.open();
        HelloGreeter greeterService = (HelloGreeter) serviceTracker.waitForService(30 * 1000);
        serviceTracker.close();

        Assert.assertNotNull("No service registered: " + SERVICE_CLAZZ_NAME , greeterService);
        Assert.assertEquals("Unexpected message: " + greeterService.greet("test") + ", expected: Hello test",
                "Hello test", greeterService.greet("test"));
        logSuccess("Service successfully installed: " + SERVICE_CLAZZ_NAME);
    }

    /**
     * Logs the given message on test success.
     *
     * @param message
     *          the message to be logged
     */
    private void logSuccess(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUCCESS_DELIMITER).append("\n").append(message).append(SUCCESS_DELIMITER);
        LOG.info(sb.toString());
    }

}
