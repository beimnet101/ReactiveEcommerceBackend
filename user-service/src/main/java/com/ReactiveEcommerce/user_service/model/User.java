package com.ReactiveEcommerce.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")  // MongoDB equivalent of the @Table annotation
public class User {

    @Id
    private String id;  // MongoDB uses String for the ID by default

    private String username;
    private String password;

    private Role role; // Enum field

    @CreatedDate
    private LocalDateTime createdDate;  // Keep this if you want automatic date creation handling by MongoDB

    public enum Role {
        ADMIN,
        USER
    }
}
