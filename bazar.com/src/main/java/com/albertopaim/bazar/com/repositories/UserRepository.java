package com.albertopaim.bazar.com.repositories;

import com.albertopaim.bazar.com.entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
   UserDetails findByEmail(String email);
   Optional<User> findByToken(String token);
}
