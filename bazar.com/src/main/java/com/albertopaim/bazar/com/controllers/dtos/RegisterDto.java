package com.albertopaim.bazar.com.controllers.dtos;

import com.albertopaim.bazar.com.entities.User.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull @Email String email, @NotNull @Min(4) String password, UserRole role) {
}
