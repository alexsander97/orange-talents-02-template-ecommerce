package com.example.mercadolivre.security;

public class TokenDto {

    private String token;

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
