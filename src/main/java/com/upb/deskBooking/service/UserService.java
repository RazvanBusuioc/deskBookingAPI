package com.upb.deskBooking.service;

import com.upb.deskBooking.repository.RoomRepository;
import com.upb.deskBooking.repository.UserRepository;
import com.upb.deskBooking.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getByUsername(String username) {
        Optional<User> byUsername = this.userRepository.findByUsername(username);
        if (byUsername.isPresent())
            return byUsername.get();
        return null;
    }
}
