package com.apnapg.entity;
import com.apnapg.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name="complaints")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor

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
