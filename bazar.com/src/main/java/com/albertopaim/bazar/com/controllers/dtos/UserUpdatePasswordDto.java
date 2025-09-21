package com.albertopaim.bazar.com.controllers.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserUpdatePasswordDto(@NotNull @Min(4) String password) {
}
