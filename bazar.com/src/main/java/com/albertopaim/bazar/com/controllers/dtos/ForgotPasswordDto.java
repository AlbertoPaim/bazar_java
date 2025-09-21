package com.albertopaim.bazar.com.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ForgotPasswordDto(@Email @NotNull String email) {
}
