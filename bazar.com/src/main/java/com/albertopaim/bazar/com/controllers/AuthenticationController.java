package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.config.TokenService;
import com.albertopaim.bazar.com.controllers.dtos.AuthenticationDto;
import com.albertopaim.bazar.com.controllers.dtos.LoginResponseDto;
import com.albertopaim.bazar.com.controllers.dtos.RegisterDto;
import com.albertopaim.bazar.com.controllers.dtos.UserUpdatePasswordDto;
import com.albertopaim.bazar.com.entities.User.UserRole;
import com.albertopaim.bazar.com.entities.User.User;
import com.albertopaim.bazar.com.repositories.UserRepository;
import com.albertopaim.bazar.com.services.UserService;
import jakarta.validation.Valid;
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
        User newUser = new User(null, data.email(), encryptedPassword,null, UserRole.ADMIN);

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();

    }

    @PutMapping("/reset")
    public ResponseEntity updateUser(Authentication authentication, @RequestBody UserUpdatePasswordDto dto) {
        String login = authentication.getName();
        userService.updateUser(login, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
