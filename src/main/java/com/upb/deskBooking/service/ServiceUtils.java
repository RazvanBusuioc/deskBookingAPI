package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.model.RoomComponent;

import java.util.List;

public enum ServiceUtils {
    INSTANCE;

    public void addNameToComponents(List<RoomComponent> components) {
        int idx = 0;
        for (RoomComponent component : components)
            if (component.getName() == null && component.getType().equals(RoomComponent.DESK))
                component.setName(RoomComponent.DESK + idx++);
    }
}
