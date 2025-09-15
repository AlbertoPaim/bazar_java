package com.albertopaim.bazar.com.repositories;

import com.albertopaim.bazar.com.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, String> {
   UserDetails findByLogin(String login);
}
