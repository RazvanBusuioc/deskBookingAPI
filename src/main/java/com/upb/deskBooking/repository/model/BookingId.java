package com.upb.deskBooking.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

public class BookingId implements Serializable {
    private RoomComponent roomComponent;
    private Date date;

    public BookingId(RoomComponent roomComponent, Date date) {
        this.roomComponent = roomComponent;
        this.date = date;
    }

    public BookingId() {
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
