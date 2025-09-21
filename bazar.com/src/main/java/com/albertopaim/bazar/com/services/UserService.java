package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.controllers.dtos.UserUpdatePasswordDto;
import com.albertopaim.bazar.com.entities.User.User;
import com.albertopaim.bazar.com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void updateUser(String email, UserUpdatePasswordDto dto) {
        User userFound = (User) userRepository.findByEmail(email);

        if (userFound != null) {
            String encriptedPassword = bCryptPasswordEncoder.encode(dto.password());
            userFound.setPassword(encriptedPassword);
            userRepository.save(userFound);
        } else {
            throw new RuntimeException("Usuário não encontrado.");
        }
    }
}
