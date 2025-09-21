package com.albertopaim.bazar.com.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(@NotNull @Email String email,@NotNull @Min(4) String password) {
}
