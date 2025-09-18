package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.config.TokenService;
import com.albertopaim.bazar.com.controllers.dtos.AuthenticationDto;
import com.albertopaim.bazar.com.controllers.dtos.LoginResponseDto;
import com.albertopaim.bazar.com.controllers.dtos.RegisterDto;
import com.albertopaim.bazar.com.entities.User.UserRole;
import com.albertopaim.bazar.com.entities.User.Users;
import com.albertopaim.bazar.com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto data) {
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(userNamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDto data) {

        if (this.userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Users newUser = new Users(null, data.login(), encryptedPassword, UserRole.CLIENT);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }
}
