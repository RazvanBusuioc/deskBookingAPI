package com.upb.deskBooking.controller;

import com.upb.deskBooking.repository.model.Room;
import com.upb.deskBooking.service.RoomService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("")
    public List<Room> getRooms() {
        return roomService.getAll();
    }

    @GetMapping("/{id}")
    public Room getById(@PathVariable(name = "id") Long roomId) {
        return roomService.getById(roomId);
    }

    @PostMapping(path     = "",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room newRoom) {
        Room room = roomService.save(newRoom);

        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(room, HttpStatus.CREATED);
        }
    }

    @PutMapping(path     = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> updateRoom(@PathVariable(name = "id") Long roomId, @Valid @RequestBody Room updatedRoom) {
        Room room = roomService.update(roomId, updatedRoom);

        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(room, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteById(@PathVariable(name = "id") Long roomId) {
        roomService.deleteById(roomId);
    }


    // EXCEPTION HANDLERS
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException cve) {
        List<String> errorMessages = cve.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
