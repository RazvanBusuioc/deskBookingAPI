package com.upb.deskBooking.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@IdClass(BookingId.class)
public class Booking implements Cloneable {

    @NotNull
    Long userId;
    @Id @NotNull @OneToOne(cascade = {CascadeType.MERGE})
    RoomComponent roomComponent;
    @Id @NotNull @JsonFormat(pattern="dd.MM.yyyy")
    Date date;

    public Booking() {
    }

    public Booking(Long userId, Date date, RoomComponent roomComponent) {
        this.userId = userId;
        this.date = date;
        this.roomComponent = roomComponent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RoomComponent getRoomComponent() {
        return roomComponent;
    }

    public void setRoomComponent(RoomComponent roomComponent) {
        this.roomComponent = roomComponent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
