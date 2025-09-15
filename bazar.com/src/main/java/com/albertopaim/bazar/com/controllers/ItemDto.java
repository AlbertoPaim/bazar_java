package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.entities.ItemCategory;

public record ItemDto(String id, String name, String description, Double price, ItemCategory category, Boolean available) {
}
