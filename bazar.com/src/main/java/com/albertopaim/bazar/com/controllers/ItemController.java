package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.controllers.dtos.ItemDto;
import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.services.ItemService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<List<Item>> getItens() {
        List<Item> itens = itemService.getItens();
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        return itemService.getItem(id).map(item -> ResponseEntity.ok(item)).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable String id){
        itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
