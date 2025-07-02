package com.schedulerates.setting.exception;

import java.io.Serial;

/**
 * Exception class named {@link NotFoundException} thrown when a requested class cannot be found.
 */
public class NotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5854010258697200749L;

    private static final String DEFAULT_MESSAGE = """
            Class not found!
            """;

    /**
     * Constructs a new NotFoundException with a default message.
     */
    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    /**
     * Constructs a new NotFoundException with a custom message appended to the default message.
     *
     * @param message the custom message indicating details about the exception
     */
    public NotFoundException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

}