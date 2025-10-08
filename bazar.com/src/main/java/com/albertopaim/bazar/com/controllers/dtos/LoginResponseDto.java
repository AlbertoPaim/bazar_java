package com.albertopaim.bazar.com.controllers.dtos;

import java.util.List;

public record LoginResponseDto(String id, String email, List<String> roles) {
}
