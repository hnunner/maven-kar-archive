package com.adviser.maven.kar.greeter.service;

/**
 * @author h.nunner
 */
public interface GreeterService {

    /**
     * Performs a greet action.
     *
     * @param recipient
     *          the recipient to be greeted
     * @return the greetings
     */
    String greet(String recipient);

}
