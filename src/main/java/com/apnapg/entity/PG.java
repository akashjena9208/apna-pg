package com.apnapg.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//import java.util.List;
//@Entity
//@Table(name = "pgs", indexes = {
//        @Index(name = "idx_pg_city", columnList = "city"),
//        @Index(name = "idx_pg_owner", columnList = "owner_id")
//})
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
////@SuperBuilder
//@ToString(exclude = {"owner", "rooms"})
//public class PG extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(nullable = false, length = 100)
//    private String name;
//    private String address;
//    @Column(nullable = false, length = 150)
//    private String city;
//    private String contactNumber;
//    //    @Column(nullable=false)
////    @Positive
////    private Double rentPerMonth;
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal rentPerMonth;
//
//    private Integer totalRooms;
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(name = "pg_images", joinColumns = @JoinColumn(name = "pg_id"))
//    @Column(name = "image_url")
//    private List<String> imageUrls;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    private Owner owner;
//
//    @OneToMany(mappedBy = "pg", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Room> rooms;
//
//
//}
//
@Entity
@Table(name = "pgs", indexes = {
        @Index(name = "idx_pg_city", columnList = "city"),
        @Index(name = "idx_pg_owner", columnList = "owner_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"owner", "rooms"})
public class PG extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 150)
    private String city;

    private String contactNumber;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rentPerMonth;

    private Integer totalRooms;

    // 🔥 FIX 1: Initialize imageUrls
    @Builder.Default
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "pg_images", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();

    // 🔥 FIX 2: Initialize rooms list
    @Builder.Default
    @OneToMany(
            mappedBy = "pg",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Room> rooms = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
}
