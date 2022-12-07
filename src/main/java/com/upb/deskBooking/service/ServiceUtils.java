package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.model.RoomComponent;

import java.util.*;

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
    public boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

}
