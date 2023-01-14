package com.upb.deskBooking.repository;

import java.util.Optional;

import com.upb.deskBooking.repository.model.ERole;
import com.upb.deskBooking.repository.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
