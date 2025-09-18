package com.albertopaim.bazar.com.controllers;

import com.albertopaim.bazar.com.controllers.dtos.ItemDto;
import com.albertopaim.bazar.com.entities.Images.ImagesItens;
import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.repositories.ItensRepository;
import com.albertopaim.bazar.com.services.ItemService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private ItensRepository itensRepository;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Item> createItem(@RequestPart("item") ItemDto dto, @RequestPart("images")List<MultipartFile> images) throws IOException {
    Item createdItem = itemService.createItem(dto, images);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
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

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable String id, @RequestBody ItemDto itemDto) {
        Item itemFound = itensRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));

        Item item = itemFound;

        item.setName(itemDto.name());
        item.setDescription(itemDto.description());
        item.setPrice(itemDto.price());
        item.setCategory(itemDto.category());

        itemService.updateItem(item);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
