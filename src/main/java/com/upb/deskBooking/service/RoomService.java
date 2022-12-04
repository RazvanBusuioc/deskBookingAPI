package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.RoomRepository;
import com.upb.deskBooking.repository.model.Room;
import com.upb.deskBooking.repository.model.RoomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        if (roomRepository.findById(id).isPresent())
            return roomRepository.findById(id).get();
        return null;
    }

    public Room save(Room room) {
        List<RoomComponent> components = room.getComponents();
        addNameToComponents(components);
        return roomRepository.save(room);
    }

    private static void addNameToComponents(List<RoomComponent> components) {
        int idx = 0;
        for (RoomComponent component : components)
            if (component.getName() == null && component.getType().equals(RoomComponent.DESK))
                component.setName(RoomComponent.DESK + idx++);
    }
}
