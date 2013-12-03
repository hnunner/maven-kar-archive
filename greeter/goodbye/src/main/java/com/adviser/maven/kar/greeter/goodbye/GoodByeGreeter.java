package com.adviser.maven.kar.greeter.goodbye;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
@Component
@Provides
@Instantiate
public class GoodByeGreeter implements GreeterService {

    /** {@inheritDoc} */
    @Override
    public String greet(String recipient) {
        return "Good bye " + recipient;
    }

}
