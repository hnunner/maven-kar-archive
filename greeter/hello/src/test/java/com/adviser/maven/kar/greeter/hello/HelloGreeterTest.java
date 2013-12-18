package com.adviser.maven.kar.greeter.hello;

import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
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
import org.osgi.framework.BundleContext;

/**
 * @author h.nunner
 */
@RunWith(PaxExam.class)
public class HelloGreeterTest {

    /** logger */
    private static final Logger LOG = Logger.getLogger(HelloGreeterTest.class.getName());

    @Inject
    protected FeaturesService featuresService;

    @Inject
    protected BundleContext context;

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
                karafDistributionConfiguration()
                        .frameworkUrl(
                                maven().groupId("org.apache.karaf").artifactId("apache-karaf").type("zip").versionAsInProject())
                        .useDeployFolder(false).karafVersion("2.3.3").unpackDirectory(new File("target/paxexam/unpack/")),

                logLevel(LogLevel.INFO),



                features(
                        maven().groupId("com.adviser.maven.kar").artifactId("maven-kar-archive-greeter-hello").type("xml")
                                .classifier("features").versionAsInProject(), "greeter-hello")
//                features(
//                        maven().groupId("org.apache.karaf.assemblies.features").artifactId("enterprise").type("xml")
//                                .classifier("features").versionAsInProject(), "transaction", "jpa", "jndi"),
//                features(maven().groupId("org.apache.activemq").artifactId("activemq-karaf").type("xml").classifier("features")
//                        .versionAsInProject(), "activemq-blueprint", "activemq-camel"),
//                features(maven().groupId("org.apache.cxf.karaf").artifactId("apache-cxf").type("xml").classifier("features")
//                        .versionAsInProject(), "cxf-jaxws"),
//                features(maven().groupId("org.apache.camel.karaf").artifactId("apache-camel").type("xml").classifier("features")
//                        .versionAsInProject(), "camel-blueprint", "camel-jms", "camel-jpa", "camel-mvel", "camel-jdbc",
//                        "camel-cxf", "camel-test"),
//
//                KarafDistributionOption.editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg",
//                        "org.ops4j.pax.url.mvn.proxySupport", "true"),
//                keepRuntimeFolder(),
//
//                mavenBundle().groupId("com.h2database").artifactId("h2").version("1.3.167"),
//                mavenBundle().groupId("de.nierbeck.camel.exam.demo").artifactId("entities").versionAsInProject(),
//                mavenBundle().groupId("org.ops4j.pax.tipi").artifactId("org.ops4j.pax.tipi.hamcrest.core").versionAsInProject(),
//                streamBundle(
//                        bundle().add("OSGI-INF/blueprint/datasource.xml", new File("src/sample/resources/datasource.xml").toURL())
//                                .set(Constants.BUNDLE_SYMBOLICNAME, "de.nierbeck.camel.exam.demo.datasource")
//                                .set(Constants.DYNAMICIMPORT_PACKAGE, "*").build()).start(),
//                streamBundle(
//                        bundle().add("OSGI-INF/blueprint/mqbroker.xml", new File("src/sample/resources/mqbroker-test.xml").toURL())
//                                .set(Constants.BUNDLE_SYMBOLICNAME, "de.nierbeck.camel.exam.demo.broker")
//                                .set(Constants.DYNAMICIMPORT_PACKAGE, "*").build()).start(),
//                streamBundle(
//                        bundle().add(JmsDestinations.class)
//                                .add(WebServiceOrder.class)
//                                .add(CamelMessageBean.class)
//                                .add(RouteID.class)
//                                .add(OrderWebServiceRoute.class)
//                                .add(OutMessageProcessor.class)
//                                .add(MessageLogConverter.class)
//                                .add("OSGI-INF/blueprint/camel-main-context.xml",
//                                        new File("src/main/resources/OSGI-INF/blueprint/camel-context.xml").toURL())
//                                .add("OSGI-INF/blueprint/jms-context.xml",
//                                        new File("src/main/resources/OSGI-INF/blueprint/jms-config.xml").toURL())
//                                .add("wsdl/WebServiceOrder.wsdl", new File("target/generated/wsdl/WebServiceOrder.wsdl").toURL())
//                                .set(Constants.BUNDLE_SYMBOLICNAME, "de.nierbeck.camel.exam.demo.route-control")
//                                .set(Constants.DYNAMICIMPORT_PACKAGE, "*")
//                                .set(Constants.EXPORT_PACKAGE, "wsdl, de.nierbeck.camel.exam.demo.control").build()).start()
        };
    }

    @Test
    public void startup() throws Exception {
        Feature greeterHelloFeature = featuresService.getFeature("maven-kar-archive-greeter-hello");
        if (greeterHelloFeature != null) {
            LOG.info("Feature found: " + greeterHelloFeature.getName());
        } else {
            LOG.warning("Feature not found.");
        }

        boolean isInstalled = featuresService.isInstalled(greeterHelloFeature);
        if (isInstalled) {
            LOG.info("Feature installed: " + greeterHelloFeature.getName());
        } else {
            LOG.warning("Feature not installed.");
        }


        Assert.assertTrue(isInstalled);
    }


//        context.get
//
//        Bundle bundle = getInstalledBundle(ctx, "fancyfoods.department.cheese");
//        try {
//        bundle.start();
//        } catch (BundleException e) {
//        fail(e.toString()); #1
//        }
//        SpecialOffer offer = waitForService(bundle, SpecialOffer.class);
//        assertNotNull("The special offer gave a null food.", #2
//        offer.getOfferFood());
//        assertEquals("Did not expect " + offer.getOfferFood().getName(),
//        "Wensleydale cheese", offer.getOfferFood().getName());
//        }
//
//    protected  T waitForService(Bundle b, Class clazz) {
//        try {
//        BundleContext bc = b.getBundleContext();
//        ServiceTracker st = new ServiceTracker(bc, clazz.getName(), null);
//        st.open();
//        Object service = st.waitForService(30 * 1000); #3
//        assertNotNull("No service of the type " + clazz.getName()
//        + " was registered.", service);
//        st.close();
//        return (T) service;
//        } catch (Exception e) {
//        fail("Failed to register services for " + b.getSymbolicName()
//        + e.getMessage());
//        return null;
//        }
//    }
}
