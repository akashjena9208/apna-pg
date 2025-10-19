package com.apnapg.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private Integer totalBeds;
    private Integer availableBeds;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private PG pg;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Tenant> tenants;
}

