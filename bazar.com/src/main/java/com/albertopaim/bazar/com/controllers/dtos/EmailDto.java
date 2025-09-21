package com.albertopaim.bazar.com.controllers.dtos;

import jakarta.validation.constraints.Email;

public record EmailDto(@Email String to, String subject, String body) {
}
