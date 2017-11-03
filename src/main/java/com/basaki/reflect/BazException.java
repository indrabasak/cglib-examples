package com.basaki.reflect;

public class BazException extends Exception {
    public BazException() {
        super();
    }

    public BazException(String message) {
        super(message);
    }

    public BazException(String message, Throwable cause) {
        super(message, cause);
    }
}
