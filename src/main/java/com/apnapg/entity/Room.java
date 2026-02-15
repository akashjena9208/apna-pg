package com.apnapg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "rooms", indexes = {@Index(name = "idx_room_pg", columnList = "pg_id"), @Index(name = "idx_room_number_pg", columnList = "roomNumber, pg_id", unique = true)})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"pg", "tenants"})
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Room number must be unique within a PG
    @Column(nullable = false, length = 20)
    private String roomNumber;

    @Column(nullable = false)
    @Min(1)
    private Integer totalBeds;

    @Column(nullable = false)
    @Min(0)
    private Integer availableBeds;

    // Room belongs to a PG
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pg_id", nullable = false, foreignKey = @ForeignKey(name = "fk_room_pg"))
    private PG pg;

    // Tenants assigned to this room
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tenant> tenants;

    // Prevent race conditions when assigning beds
    @Version
    private Long version;

    protected void validate() {
        if (totalBeds == null || availableBeds == null) throw new IllegalStateException("Beds cannot be null");

        if (availableBeds > totalBeds) throw new IllegalStateException("Available beds cannot exceed total beds");
    }

    @PrePersist
    @PreUpdate
    private void beforeSave() {
        validate();
    }


}
