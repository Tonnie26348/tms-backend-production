package com.tms.core.service.booking;

import com.tms.api.dto.booking.BookingDTO;
import com.tms.core.entity.booking.Booking;
import com.tms.infrastructure.repository.UserRepository;
import com.tms.infrastructure.repository.trip.TripRepository;
import com.tms.infrastructure.repository.booking.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, 
                          TripRepository tripRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        mapToEntity(bookingDTO, booking);
        booking = bookingRepository.save(booking);
        return mapToDTO(booking);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(UUID id) {
        return bookingRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public BookingDTO updateBooking(UUID id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        mapToEntity(bookingDTO, booking);
        booking = bookingRepository.save(booking);
        return mapToDTO(booking);
    }

    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

    private BookingDTO mapToDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setTripId(booking.getTrip().getId());
        dto.setSeatNumber(booking.getSeatNumber());
        dto.setAmountPaid(booking.getAmountPaid());
        dto.setStatus(booking.getStatus());
        return dto;
    }

    private void mapToEntity(BookingDTO dto, Booking booking) {
        booking.setUser(userRepository.findById(dto.getUserId()).orElseThrow());
        booking.setTrip(tripRepository.findById(dto.getTripId()).orElseThrow());
        booking.setSeatNumber(dto.getSeatNumber());
        booking.setAmountPaid(dto.getAmountPaid());
        booking.setStatus(dto.getStatus());
    }
}
