package com.apnapg.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_tenant", columnList = "tenant_id"),
        @Index(name = "idx_review_pg", columnList = "pg_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔒 Review must belong to a tenant
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tenant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_tenant")
    )
    private Tenant tenant;

    // 🔒 Review must belong to a PG
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "pg_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_review_pg")
    )
    private PG pg;

    // ⭐ Rating required and bounded
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(length = 2000)
    private String comment;
}
