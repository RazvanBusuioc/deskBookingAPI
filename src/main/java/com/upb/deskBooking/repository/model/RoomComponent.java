package com.upb.deskBooking.repository.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class RoomComponent {

    public static final String DESK  = "desk";
    public static final String CHAIR = "chair";
    public static final String EMPTY = "empty";
    public static final String WALL  = "wall";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "desk|chair|empty|wall")
    String type;

    String name;

    public RoomComponent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
