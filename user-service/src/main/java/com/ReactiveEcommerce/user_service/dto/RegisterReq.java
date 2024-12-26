package com.ReactiveEcommerce.user_service.dto;

import com.ReactiveEcommerce.user_service.model.User;
import lombok.Data;

@Data

public class RegisterReq {
    private String username;
    private String password;
    private String email;
}
