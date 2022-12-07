package com.upb.deskBooking.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class BookingRequestParam {
    @NotNull
    Long userId;
    @NotNull
    Long roomComponentId;
    @NotNull @JsonFormat(pattern="dd.MM.yyyy")
    Date date;

    public BookingRequestParam() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
