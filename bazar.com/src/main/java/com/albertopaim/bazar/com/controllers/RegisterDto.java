package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.entities.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
