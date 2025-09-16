package com.albertopaim.bazar.com.repositories;

import com.albertopaim.bazar.com.entities.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Users, String> {
   UserDetails findByLogin(String login);
}
