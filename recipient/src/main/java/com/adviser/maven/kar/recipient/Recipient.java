package com.adviser.maven.kar.recipient;

import java.util.logging.Logger;

import org.apache.felix.ipojo.annotations.Bind;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Unbind;
import org.apache.felix.ipojo.annotations.Validate;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
@Component(immediate = true)
@Instantiate
public class Recipient {

    /** logger */
    private static final Logger LOG = Logger.getLogger(Recipient.class.getName());

    /** iPOJO greeter services */
    @Requires(id = "greeters")
    private GreeterService greeters[];


    /**
     * Called on bundle startup.
     */
    @Validate
    private void start() {
        greet();
        LOG.info(getClass() + "::Start");
    }

    /**
     * Called on bundle shutdown.
     */
    @Invalidate
    private void stop() {
        greet();
        LOG.info(getClass() + "::Stop");
    }


    /**
     * Callback for registration of GreeterService.
     */
    @Bind(id = "greeters")
    private void bindGreeter(GreeterService greeter) {
        LOG.info("Bound a new service (" + greeter.getClass().getName() + ").");
    }

    /**
     * Callback for deregistration of GreeterService.
     */
    @Unbind(id = "greeters")
    private void unbindGreeter(GreeterService greeter) {
        LOG.info("Unbound a lost service (" + greeter.getClass().getName() + ").");
    }


    /**
     * Greets by invoking all available greeters.
     */
    private void greet() {
        if (greeters == null || greeters.length == 0) {
            LOG.info("No greeters available..");
            return;
        }
        for (GreeterService greeter : greeters) {
            LOG.info(greeter.greet(getClass().getSimpleName()));
        }
    }

}
