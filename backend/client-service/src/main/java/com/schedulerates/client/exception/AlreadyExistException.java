package com.schedulerates.client.exception;

import java.io.Serial;

/**
 * Exception class named {@link CompanyAlreadyExistException} thrown when attempting to create a company that already exists.
 */
public class AlreadyExistException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 53457089789182737L;

    private static final String DEFAULT_MESSAGE = """
            Produit already exist!
            """;

    /**
     * Constructs a new AlreadyExistException with a default message.
     */
    public AlreadyExistException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new AlreadyExistException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public AlreadyExistException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}