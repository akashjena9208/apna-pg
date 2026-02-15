package com.apnapg.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(
        name = "owners",
        indexes = {
                @Index(name = "idx_owner_user", columnList = "user_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
@ToString(exclude = {"user", "pgs"})
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 255)
    private String address;

    @Column(length = 100)
    private String businessName;

    @Column(length = 20)
    private String gstNumber;

    @Column(length = 500)
    private String profileImageUrl;

    // 🔐 Identity link
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_owner_user")
    )
    private User user;

    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PG> pgs;
}
