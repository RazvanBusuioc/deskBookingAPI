package com.upb.deskBooking.controller;

import com.upb.deskBooking.repository.model.Booking;
import com.upb.deskBooking.repository.model.BookingRequestParam;
import com.upb.deskBooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("")
    public ResponseEntity<List<Booking>> getBookings(
            @RequestParam(name="date", required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") Date date,
            @RequestParam(name="userId", required = false) Long userId,
            @RequestParam(name="roomId", required = false) Long roomId)
    {
        return new ResponseEntity<>(bookingService.getAll(date, userId, roomId), HttpStatus.OK);
    }

    @PostMapping(path     = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequestParam newBooking) {
        Booking booking = bookingService.save(newBooking);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "")
    public ResponseEntity<Long> deleteBooking(@RequestBody BookingRequestParam bookingRequestParam) {
        boolean exists = bookingService.deleteBooking(bookingRequestParam);
        if (exists)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
