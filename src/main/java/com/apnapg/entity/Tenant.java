package com.apnapg.entity;

import com.apnapg.enums.Gender;
import com.apnapg.enums.Occupation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tenants", indexes = {@Index(columnList = "email")})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;
    private String aadhaarUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    private LocalDate dateOfBirth;
    private String address;

    private String emergencyContactName;
    private String emergencyContactNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;


    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;
}
