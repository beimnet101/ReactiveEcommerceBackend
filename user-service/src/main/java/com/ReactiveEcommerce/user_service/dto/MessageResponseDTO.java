package com.ReactiveEcommerce.user_service.dto;

import lombok.Data;

@Data
public class MessageResponseDTO {
    String message;
    boolean status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
