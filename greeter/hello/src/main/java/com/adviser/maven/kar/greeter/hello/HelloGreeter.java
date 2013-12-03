package com.adviser.maven.kar.greeter.hello;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
public class HelloGreeter implements GreeterService {

    /** {@inheritDoc} */
    @Override
    public String greet(String recipient) {
        return "Hello " + recipient;
    }

}
