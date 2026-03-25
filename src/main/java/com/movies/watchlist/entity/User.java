package com.movies.watchlist.entity;

import com.movies.watchlist.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore  // password will NEVER appear in API responses
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)  // stores "USER" or "ADMIN" as text in DB
    @Column(nullable = false)
    private Role role; // Object type from package enums
}