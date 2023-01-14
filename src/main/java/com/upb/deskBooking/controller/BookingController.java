package com.upb.deskBooking.controller;

import com.upb.deskBooking.repository.model.Booking;
import com.upb.deskBooking.payload.request.BookingRequest;
import com.upb.deskBooking.service.BookingService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Booking>> getBookings(
            @RequestParam(name="date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date date,
            @RequestParam(name="username", required = false) String username,
            @RequestParam(name="roomId", required = false) Long roomId)
    {
        return new ResponseEntity<>(bookingService.getAll(date, username, roomId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping(path     = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest newBooking) {
        Booking booking = bookingService.save(newBooking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @DeleteMapping(path = "")
    public ResponseEntity<Long> deleteBooking(@RequestBody BookingRequest bookingRequest) {
        boolean exists = bookingService.deleteBooking(bookingRequest);
        if (exists)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
