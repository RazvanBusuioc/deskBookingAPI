package com.upb.deskBooking.repository;

import com.upb.deskBooking.repository.model.Booking;
import com.upb.deskBooking.repository.model.BookingId;
import com.upb.deskBooking.repository.model.RoomComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByDateAndRoomComponent(Date date, RoomComponent roomComponent);

    void deleteByDateAndRoomComponent(Date date, RoomComponent roomComponent);
}
