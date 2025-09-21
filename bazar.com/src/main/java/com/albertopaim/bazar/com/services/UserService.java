package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.controllers.dtos.EmailDto;
import com.albertopaim.bazar.com.entities.User.User;
import com.albertopaim.bazar.com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;


    public void forgotPassword(String email) {
        User userFound = (User) userRepository.findByEmail(email);

        if (userFound != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime tokenExpiration = LocalDateTime.now().plusHours(1);

            userFound.setPasswordToken(token);
            userFound.setPasswordTokenExpiration(tokenExpiration);
            userRepository.save(userFound);

            String resetLink = "http://localhost:3000/reset?token=" + token;

            emailService.EmailSender(new EmailDto(email, "Redefinição de senha", "Clique aqui para redefinir sua senha: " + resetLink));
        }
    }

    public void resetPassword(String token, String password) {
        Optional<User> userFound = userRepository.findBypasswordToken(token);

        if (userFound.isEmpty()) {
            throw new RuntimeException("Token inválido");
        }

        User user = userFound.get();

        if (user.getPasswordTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de redefinição de senha expirado");
        }

        String newPassword = bCryptPasswordEncoder.encode(password);

        user.setPassword(newPassword);
        user.setPasswordToken(null);
        user.setPasswordTokenExpiration(null);

        userRepository.save(user);
    }

}
