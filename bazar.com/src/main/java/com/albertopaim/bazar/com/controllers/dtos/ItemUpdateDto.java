package com.albertopaim.bazar.com.controllers.dtos;

import com.albertopaim.bazar.com.entities.Images.ImagesItens;
import com.albertopaim.bazar.com.entities.Item.ItemCategory;

public record ItemUpdateDto(String id, String name, String description, Double price, ItemCategory category, Boolean available, ImagesItens images) {
}
