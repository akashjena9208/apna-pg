package com.apnapg.entity;

import com.apnapg.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private Tenant tenant;

    @OneToOne(mappedBy = "user")
    @JsonBackReference //back side
    private Owner owner;


}
/*
* In my PG management system, I had a bi-directional one-to-one relationship between Tenant and User. Each entity referenced the other, which caused infinite recursion during JSON serialization — especially when returning data from REST APIs.
This happened because the JSON serializer (Jackson) kept following the references: Tenant → User → Tenant → User → ..., leading to a stack overflow or huge payloads.
To solve it, I used @JsonManagedReference on the forward side (Tenant → User) and @JsonBackReference on the reverse side (User → Tenant). This broke the loop during serialization.
Additionally, I introduced DTOs to control what data gets exposed in API responses. This made the system safer, cleaner, and easier to maintain

* I also excluded sensitive fields like passwords using @ToString(exclude = "password") and @JsonIgnore where needed. This ensured security and prevented accidental data leaks

* */