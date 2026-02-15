package com.apnapg.entity;

import com.apnapg.enums.AuthProvider;
import com.apnapg.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@ToString(exclude = {"password", "tenant", "owner"})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔐 Identity belongs ONLY here
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(nullable = false)
    private boolean accountNonLocked = true;

    @Column(nullable = false)
    private int failedLoginAttempts = 0;

    private Instant lockTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider = AuthProvider.LOCAL;

//    @Column(nullable = false)
//    @org.hibernate.annotations.ColumnDefault("false")
//    private Boolean profileCompleted = false;


    // 🔁 Reverse mappings
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Tenant tenant;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Owner owner;
}

