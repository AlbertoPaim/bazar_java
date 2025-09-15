package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.entities.Item;
import com.albertopaim.bazar.com.services.ItemService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
@AllArgsConstructor
@NoArgsConstructor
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto body) {
        Item itemToSave = new Item();

        itemToSave.setName(body.name());
        itemToSave.setDescription(body.description());
        itemToSave.setPrice(body.price());
        itemToSave.setCategory(body.category());
        itemToSave.setAvailable(body.available());

        Item savedItem = this.itemService.createItem(itemToSave);

        ItemDto responseDto = new ItemDto(
                savedItem.getId(),
                savedItem.getName(),
                savedItem.getDescription(),
                savedItem.getPrice(),
                savedItem.getCategory(),
                savedItem.getAvailable());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItens (){
        List<Item> itens = itemService.getItens();
        return ResponseEntity.ok(itens);
    }

}
