package com.moricion.qa.common.webservices;


public class WebServicesException extends RuntimeException {

    public WebServicesException(String message ) {
        super(message);
    }

    public WebServicesException(String message, Throwable cause) {
        super(message, cause);
    }
}