package com.adviser.maven.kar.greeter.hello;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.logLevel;

import java.io.File;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.karaf.options.LogLevelOption.LogLevel;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
@RunWith(PaxExam.class)
public class HelloGreeterTest {

    /** logger */
    private static final Logger LOG = LoggerFactory.getLogger(HelloGreeterTest.class);

    @Inject
    private BundleContext context;

    /**
     * Test configurations.
     *
     * @return the configurations as array of Option
     */
    @Configuration
    public Option[] config() {
        return new Option[] {
                // provision and launch a container based on a Apache Karaf distribution
                karafDistributionConfiguration().frameworkUrl(
                        maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip").versionAsInProject())
                        .karafVersion("2.3.3").name("Apache Karaf").unpackDirectory(new File("target/pax")),
                // keeps the runtime folder after finishing the tests
                keepRuntimeFolder(),
                // don't bother with local console output as it just ends up cluttering the logs
                configureConsole().ignoreLocalConsole(),
                // default is WARN
                logLevel(LogLevel.INFO),
                // feature required by the following tests
                features("mvn:com.adviser.maven.kar/maven-kar-archive-greeter-hello/0.0.1-SNAPSHOT/xml",
                        "maven-kar-archive-greeter-hello")
                // test executes in another process, so Pax Exam must be launched with debugging enabled
                // debugConfiguration("5000", true),
        };
    }

    /**
     * Tests if the HelloGreeter is being provided as OSGi service.
     *
     * @throws Exception
     *          on fail
     */
    @Test
    public void testHelloGreeterService() throws Exception {
        LOG.info("Retrieving " + HelloGreeter.class.getName() + "..");
        GreeterService helloGreeter = waitForService(HelloGreeter.class);

        LOG.info("Testing retrieved " + HelloGreeter.class.getName() + "..");
        String testMessage = helloGreeter.greet("test");
        Assert.assertNotNull("The HelloGreeter service did not return a greet message.", testMessage);
        Assert.assertEquals("Unexpected greet: " + testMessage, "Hello test", testMessage);
    }

    /**
     * Waits and returns a service from the registry defined by its class.
     *
     * @param serviceClazz
     *          the clazz of the service to be waited for
     * @return the service from the registry defined by its class
     */
    protected <T extends GreeterService> T waitForService(Class<T> serviceClazz) {
        try {
            ServiceTracker st = new ServiceTracker(context, serviceClazz.getName(), null);

            st.open();
            LOG.info("Waiting for " + serviceClazz.getName() + "..");
            T service = (T) st.waitForService(30 * 1000);
            Assert.assertNotNull("No service of the type " + serviceClazz.getName() + " was registered.", service);
            LOG.info("Succesfully retrieved " + serviceClazz.getName() + "..");
            st.close();

            return service;
        } catch (Exception e) {
            LOG.error("Failed to retrieve service " + serviceClazz.getName(), e);
        return null;
        }
    }

}
