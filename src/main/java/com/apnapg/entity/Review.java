package com.apnapg.entity;
//package com.apnapg.entity;
////
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import lombok.*;

import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "reviews")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Review {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "tenant_id")
//    private Tenant tenant;
//
//    @ManyToOne
//    @JoinColumn(name = "pg_id")
//    private PG pg;
//
//    private Integer rating; // 1 to 5
//
//    private String comment;
//
//    private LocalDateTime createdAt;
//}


import com.apnapg.entity.BaseEntity;
import com.apnapg.entity.PG;
import com.apnapg.entity.Tenant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reviews", indexes = {
        @Index(name = "idx_review_tenant", columnList = "tenant_id"),
        @Index(name = "idx_review_pg", columnList = "pg_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
//@SuperBuilder
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
