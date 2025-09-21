package com.albertopaim.bazar.com.controllers.dtos;

import jakarta.validation.constraints.Min;

public record ResetPasswordDto(String token, @Min(4) String password) {
}
