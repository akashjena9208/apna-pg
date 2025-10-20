package com.apnapg.entity;

import com.apnapg.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "complaints")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private PG pg;

    private String message;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status = ComplaintStatus.NEW;
}
