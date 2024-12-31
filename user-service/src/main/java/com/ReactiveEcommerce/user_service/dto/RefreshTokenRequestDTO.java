package com.ReactiveEcommerce.user_service.dto;

import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
