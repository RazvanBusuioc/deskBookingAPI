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

    @NotNull @OneToOne(cascade = {CascadeType.MERGE})
    User user;
    @Id @NotNull @OneToOne(cascade = {CascadeType.MERGE})
    RoomComponent roomComponent;
    @Id @NotNull @JsonFormat(pattern="dd.MM.yyyy")
    Date date;

    public Booking() {
    }

    public Booking(User user, Date date, RoomComponent roomComponent) {
        this.user = user;
        this.date = date;
        this.roomComponent = roomComponent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
