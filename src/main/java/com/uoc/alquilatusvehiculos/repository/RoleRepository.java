package com.uoc.alquilatusvehiculos.repository;

import com.uoc.alquilatusvehiculos.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
