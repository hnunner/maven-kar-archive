package com.adviser.maven.kar.greeter.goodbye;

import com.adviser.maven.kar.greeter.service.GreeterService;

/**
 * @author h.nunner
 */
public class GoodByeGreeter implements GreeterService {

    /** {@inheritDoc} */
    @Override
    public String greet(String recipient) {
        return "Good bye " + recipient;
    }

}
