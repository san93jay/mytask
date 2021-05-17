package com.heptagon.userdetailstask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.heptagon.userdetailstask.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

}
