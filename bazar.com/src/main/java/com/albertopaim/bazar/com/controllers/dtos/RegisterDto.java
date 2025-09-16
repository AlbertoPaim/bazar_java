package com.albertopaim.bazar.com.controllers.dtos;

import com.albertopaim.bazar.com.entities.User.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
