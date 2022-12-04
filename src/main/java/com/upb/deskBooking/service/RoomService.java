package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.RoomRepository;
import com.upb.deskBooking.repository.model.Room;
import org.springframework.beans.BeanUtils;
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
        ServiceUtils.INSTANCE.addNameToComponents(room.getComponents());
        return roomRepository.save(room);
    }

    public Room update (Long id, Room updatedRoom) {
        ServiceUtils.INSTANCE.addNameToComponents(updatedRoom.getComponents());
        Room deprecatedRoom = getById(id);
        if (deprecatedRoom == null)
            return null;

        BeanUtils.copyProperties(updatedRoom, deprecatedRoom, "id");
        return roomRepository.save(deprecatedRoom);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }


}
