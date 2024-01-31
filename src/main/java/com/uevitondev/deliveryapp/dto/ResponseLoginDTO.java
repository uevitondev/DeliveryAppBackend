package com.uevitondev.deliveryapp.dto;

import java.io.Serializable;

public class ResponseLoginDTO implements Serializable {
    private String token;

    public ResponseLoginDTO() {
    }

    public ResponseLoginDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
