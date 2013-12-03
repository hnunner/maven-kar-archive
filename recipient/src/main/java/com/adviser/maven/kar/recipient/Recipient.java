package com.adviser.maven.kar.recipient;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Requires;
import org.apache.felix.ipojo.annotations.Validate;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
@Component(immediate = true)
@Instantiate
public class Recipient {

    /** iPOJO greeter service */
    @Requires
    private GreeterService greeters[];

    /**
     * Called on bundle startup.
     */
    @Validate
    private void start() {
    }

    /**
     * Called on bundle shutdown.
     */
    @Invalidate
    private void stop() {
    }


}
