//package com.apnapg.entity;
//import com.apnapg.enums.Gender;
//import com.apnapg.enums.Occupation;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDate;
//
//@Entity
//@Table(name="tenants",  uniqueConstraints = @UniqueConstraint(name="uk_tenant_email", columnNames="email"),indexes = {
//        @Index(name="idx_tenant_email", columnList="email"),
//        @Index(name="idx_tenant_user", columnList="user_id"),
//        @Index(name="idx_tenant_room", columnList="room_id")
//})
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//@ToString(exclude = {"user","room"})
//public class Tenant extends BaseEntity {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable=false, length=50)
//    private String firstName;
//
//    @Column(nullable=false, length=50)
//    private String lastName;
//
//
////    @Column(nullable=false, unique=true)
////    private String email;
//
//    @Column(length=15)
//    private String phoneNumber;
//
//    @Column(length=500)
//    private String aadhaarUrl;
//
//
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//    @Enumerated(EnumType.STRING)
//    private Occupation occupation;
//
//    private LocalDate dateOfBirth;
//    private String address;
//    private String emergencyContactName;
//    private String emergencyContactNumber;
//
//    @OneToOne(fetch=FetchType.LAZY, optional=false)
//    @JoinColumn(name="user_id", nullable=false, unique=true)
//    private User user;
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="room_id")
//    private Room room;
//}
package com.apnapg.entity;

import com.apnapg.enums.Gender;
import com.apnapg.enums.Occupation;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(
        name = "tenants",
        indexes = {
                @Index(name = "idx_tenant_user", columnList = "user_id"),
                @Index(name = "idx_tenant_room", columnList = "room_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
//@SuperBuilder
@ToString(exclude = {"user", "room"})
public class Tenant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 500)
    private String aadhaarUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    private LocalDate dateOfBirth;

    private String address;

    private String emergencyContactName;

    private String emergencyContactNumber;

    // 🔐 Identity linked here
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_tenant_user")
    )
    private User user;

    // 🏠 Room relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "room_id",
            foreignKey = @ForeignKey(name = "fk_tenant_room")
    )
    private Room room;
}
