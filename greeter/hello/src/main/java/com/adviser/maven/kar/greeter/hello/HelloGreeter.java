package com.adviser.maven.kar.greeter.hello;

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
public class HelloGreeter implements GreeterService {

    /** {@inheritDoc} */
    @Override
    public String greet(String recipient) {
        return "Hello " + recipient;
    }

}
