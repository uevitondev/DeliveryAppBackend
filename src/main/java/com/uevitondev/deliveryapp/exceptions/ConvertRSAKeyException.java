package com.uevitondev.deliveryapp.exceptions;

public class ConvertRSAKeyException extends RuntimeException {

    public ConvertRSAKeyException() {
        super("Error in convert RSA key");
    }

    public ConvertRSAKeyException(String msg) {
        super(msg);
    }
}
