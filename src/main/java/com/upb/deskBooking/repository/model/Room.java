package com.upb.deskBooking.repository.model;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.List;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    String name;
    @NotBlank
    String location;
    @NotBlank
    String description;
    @NotNull
    Integer length;
    @NotNull
    Integer width;
    @NotNull
    @OneToMany(cascade = {CascadeType.ALL})
    List<RoomComponent> components;

    @AssertTrue(message = "Components size should match the given length and width")
    public boolean isSizeValid() {
        return this.length * this.width == this.components.size();
    }

    public Room() {}

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public List<RoomComponent> getComponents() {
        return components;
    }

    public void setComponents(List<RoomComponent> components) {
        this.components = components;
    }
}
