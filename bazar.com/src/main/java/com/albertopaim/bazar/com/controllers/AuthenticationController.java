package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.config.TokenService;
import com.albertopaim.bazar.com.controllers.dtos.*;
import com.albertopaim.bazar.com.entities.User.UserRole;
import com.albertopaim.bazar.com.entities.User.User;
import com.albertopaim.bazar.com.repositories.UserRepository;
import com.albertopaim.bazar.com.services.EmailService;
import com.albertopaim.bazar.com.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto data) {

        if (this.userRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(null, data.email(), encryptedPassword, null, null, UserRole.ADMIN);

        this.userRepository.save(newUser);

        EmailDto email = new EmailDto(data.email(), "Cadastro usuario - Vende e Passa", "Parabéns, você acaba de se cadastrar no Vende e Passa! Seja bem vindo(a) e aproveite os itens de nosso Bazar");
        emailService.EmailSender(email);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/reset")
    public ResponseEntity<Void> updateUser(@RequestBody ResetPasswordDto dto) {
        userService.resetPassword(dto.token(), dto.password());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<Void> resetForgotPassword(@RequestBody String email) {
        userService.forgotPassword(email);
        return ResponseEntity.noContent().build();
    }


}
