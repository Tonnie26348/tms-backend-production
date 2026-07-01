package com.tms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stops", uniqueConstraints = {@UniqueConstraint(columnNames = {"route_id", "sequence_number"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "route")
@EqualsAndHashCode(exclude = "route")
public class Stop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    @JsonIgnore
    private Route route;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "distance_from_origin_km", nullable = false, precision = 6, scale = 2)
    private BigDecimal distanceFromOriginKm;

    @Column(name = "estimated_time_from_origin_minutes", nullable = false)
    private Integer estimatedTimeFromOriginMinutes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
