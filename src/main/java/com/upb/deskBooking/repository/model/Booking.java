package com.upb.deskBooking.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    User user;
    @OneToOne()
    RoomComponent roomComponent;
    @JsonFormat(pattern="dd.MM.yyyy")
    Date date;

    public Booking() {
    }

    public Booking(User user, Date date, RoomComponent roomComponent) {
        this.user = user;
        this.date = date;
        this.roomComponent = roomComponent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
