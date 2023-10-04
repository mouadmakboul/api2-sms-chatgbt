package com.example.demo.Repositories;

import com.example.demo.Entities.UserEntity.ERole;
import com.example.demo.Entities.UserEntity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(ERole name);

}
