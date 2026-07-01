package com.tms.core.repository.trip;

import com.tms.core.entity.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {
}

