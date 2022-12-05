package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.model.RoomComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ServiceUtils {
    INSTANCE;

    public void addNameToComponents(List<RoomComponent> components) {
        Map<String, Integer> componentToIndex  = new HashMap<>() {{
            put(RoomComponent.DESK,  0);
            put(RoomComponent.CHAIR, 0);
            put(RoomComponent.EMPTY, 0);
            put(RoomComponent.WALL,  0);
        }};
        for (RoomComponent component : components)
            if (component.getName() == null) {
                String componentType = component.getType();
                if(!componentToIndex.containsKey(componentType))
                    continue;
                int index = componentToIndex.get(component.getType());
                component.setName(componentType + index++);
                componentToIndex.put(componentType, index);
            }
    }
}
