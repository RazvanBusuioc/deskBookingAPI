package com.upb.deskBooking.service;

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

    public List<Booking> getAll(Date date, Long userId, Long roomId) {
        if (roomId == null)
            return getAllByExample(date, null, userId);

        Room room = roomService.getById(roomId);
        if (room == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        List<RoomComponent> components = room.getComponents();
        List<Booking> result = new ArrayList<>();

        for (RoomComponent component : components) {
            result.addAll(getAllByExample(date, component, userId));
        }
        return result;
    }

    public List<Booking> getAllByExample(Date date, RoomComponent component, Long userId) {
        Booking booking = new Booking();
        booking.setUserId(userId);
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

    public Booking save(BookingRequestParam bookingRequestParam) {
        RoomComponent roomComponent = roomService.getRoomComponentById(bookingRequestParam.getRoomComponentId());

        if (roomComponent == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (bookingRepository.existsById(new BookingId(roomComponent, bookingRequestParam.getDate())))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        Booking booking = new Booking(bookingRequestParam.getUserId(), bookingRequestParam.getDate(), roomComponent);
        return bookingRepository.save(booking);
    }

    public boolean deleteBooking(BookingRequestParam bookingRequestParam) {
        RoomComponent roomComponent = roomService.getRoomComponentById(bookingRequestParam.getRoomComponentId());

        if (roomComponent == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        BookingId bookingId = new BookingId(roomComponent, bookingRequestParam.getDate());
        boolean exists = bookingRepository.existsById(bookingId);
        if (!exists)
            return false;

        bookingRepository.deleteById(bookingId);
        return true;
    }
}
