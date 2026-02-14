package com.apnapg.entity;

import com.apnapg.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

//@Entity
//@Table(name = "complaints")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Complaint {
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
//    private String message;
//
//    @Enumerated(EnumType.STRING)
//    private ComplaintStatus status = ComplaintStatus.NEW;
//}
@Entity
@Table(name="complaints")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
//@SuperBuilder
@Builder
public class Complaint extends BaseEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pg_id", nullable = false)
    private PG pg;

    @Column(nullable = false, length = 2000)
    private String message;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    @Builder.Default
    private ComplaintStatus status = ComplaintStatus.NEW;

}
