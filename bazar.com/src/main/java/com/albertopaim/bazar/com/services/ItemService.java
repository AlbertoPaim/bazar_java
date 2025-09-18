package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.controllers.dtos.ItemDto;
import com.albertopaim.bazar.com.entities.Images.ImagesItens;
import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.repositories.ImagesRepository;
import com.albertopaim.bazar.com.repositories.ItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItensRepository itensRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public Item createItem(ItemDto itemDto, List<MultipartFile> files) throws IOException {
        Item item = new Item();
        item.setName(itemDto.name());
        item.setDescription(itemDto.description());
        item.setPrice(itemDto.price());
        item.setCategory(itemDto.category());

        Item savedItem = itensRepository.save(item);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                String imageUrl = cloudinaryService.upload(file);

                ImagesItens image = new ImagesItens();
                image.setImageUrl(imageUrl);
                image.setItem(savedItem);

                imagesRepository.save(image);
            }
        }
        return itensRepository.findById(savedItem.getId()).orElseThrow();
    }


    public List<Item> getItens() {
        return itensRepository.findAll();
    }

    public Optional<Item> getItem(String id) {
        return itensRepository.findById(id);
    }

    public void updateItem(Item item) {

        itensRepository.save(item);
    }

    public void deleteItem(String id) {
        itensRepository.deleteById(id);
    }

}