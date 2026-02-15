package com.apnapg.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(
        name = "refresh_token",
        indexes = {
                @Index(name = "idx_refresh_user", columnList = "user_id"),
                @Index(name = "idx_refresh_hash", columnList = "tokenHash")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Store ONLY the SHA-256 hash of the refresh token,
     * never the raw token string.
     */
    @Column(nullable = false, unique = true, length = 128)
    private String tokenHash;

    // Token must belong to a user
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refresh_user")
    )
    private User user;

    // Expiration is mandatory
    @Column(nullable = false)
    private Instant expiryDate;

    // Revocation flag for logout / security events
    @Column(nullable = false)
    private boolean revoked = false;
}
