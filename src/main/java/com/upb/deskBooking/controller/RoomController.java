package com.upb.deskBooking.controller;

import com.upb.deskBooking.repository.model.Room;
import com.upb.deskBooking.service.RoomService;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Room>> getRooms() {
        return new ResponseEntity<>(roomService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Room> getById(@PathVariable(name = "id") Long roomId) {
        Room room = roomService.getById(roomId);
        if (room == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PostMapping(path     = "",
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Room> createRoom(@Valid @RequestBody Room newRoom) {
        Room room = roomService.save(newRoom);

        if (room == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(room, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable(name = "id") Long roomId) {
        boolean exists = roomService.deleteById(roomId);
        if (exists)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "")
    public ResponseEntity<Long> deleteAll() {
        roomService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // EXCEPTION HANDLERS
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<List<String>> handleConstraintViolationException(ConstraintViolationException cve) {
            List<String> errorMessages = cve.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException manve) {
        List<String> errorMessages = manve
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

}
