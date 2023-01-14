package com.upb.deskBooking.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import java.util.Date;

public class BookingRequest {
    @NotNull
    String username;
    @NotNull
    Long roomComponentId;
    @NotNull @JsonFormat(pattern="dd.MM.yyyy")
    Date date;

    public BookingRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoomComponentId() {
        return roomComponentId;
    }

    public void setRoomComponentId(Long roomComponentId) {
        this.roomComponentId = roomComponentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
