package com.apnapg.entity;

import com.apnapg.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", indexes = {@Index(columnList = "email")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Tenant tenant;
}
