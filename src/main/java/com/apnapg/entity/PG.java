package com.apnapg.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "pgs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String contactNumber;
    private Double rentPerMonth;
    private Integer totalRooms;

    @ElementCollection
    @CollectionTable(name = "pg_images", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Owner owner;

    @OneToMany(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Room> rooms;
}
