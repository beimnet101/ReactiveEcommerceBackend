package com.ReactiveEcommerce.user_service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String username;
    private String password;

    private Role role; // Enum field

    @CreatedDate
    private LocalDateTime createdDate;



    public enum Role {
        ADMIN,
        USER
    }
}
