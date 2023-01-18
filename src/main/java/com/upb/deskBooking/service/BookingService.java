package com.upb.deskBooking.service;

import com.upb.deskBooking.payload.request.BookingRequest;
import com.upb.deskBooking.repository.BookingRepository;
import com.upb.deskBooking.repository.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    public List<Booking> getAll(Date date, String username, Long roomId) {
        if (roomId == null)
            return getAllByExample(date, null, username);

        Room room = roomService.getById(roomId);
        if (room == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        List<RoomComponent> components = room.getComponents();
        List<Booking> result = new ArrayList<>();

        for (RoomComponent component : components) {
            result.addAll(getAllByExample(date, component, username));
        }
        return result;
    }

    public List<Booking> getAllByExample(Date date, RoomComponent component, String username) {
        Booking booking = new Booking();
        booking.setUser(userService.getByUsername(username));
        booking.setRoomComponent(component);
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues();
        Example<Booking> example = Example.of(booking, matcher);
        List<Booking> matches = bookingRepository.findAll(example);
        if (date == null)
            return matches;

        List<Booking> result = new ArrayList<>();
        for (Booking bk : matches) {
            if (ServiceUtils.INSTANCE.isSameDay(bk.getDate(), date))
                result.add(bk);
        }
        return result;
    }

    public Booking save(BookingRequest bookingRequest) {
        RoomComponent roomComponent = roomService.getRoomComponentById(bookingRequest.getRoomComponentId());

        if (roomComponent == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (bookingRepository.existsByDateAndRoomComponent(bookingRequest.getDate(), roomComponent))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        String username = bookingRequest.getUsername();
        User user = userService.getByUsername(username);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Booking booking = new Booking(user, bookingRequest.getDate(), roomComponent);
        return bookingRepository.save(booking);
    }

    public boolean deleteBooking(BookingRequest bookingRequest) {
        RoomComponent roomComponent = roomService.getRoomComponentById(bookingRequest.getRoomComponentId());

        if (roomComponent == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        BookingId bookingId = new BookingId(roomComponent, bookingRequest.getDate());
        boolean exists = bookingRepository.existsByDateAndRoomComponent(bookingRequest.getDate(), roomComponent);
        if (!exists)
            return false;

        bookingRepository.deleteByDateAndRoomComponent(bookingRequest.getDate(), roomComponent);
        return true;
    }
}
