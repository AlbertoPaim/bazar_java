package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.controllers.dtos.ItemDto;
import com.albertopaim.bazar.com.controllers.dtos.ItemUpdateDto;
import com.albertopaim.bazar.com.entities.Images.ImagesItens;
import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.repositories.ImagesRepository;
import com.albertopaim.bazar.com.repositories.ItensRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItensRepository itensRepository;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public Item createItem(ItemDto itemDto, List<MultipartFile> files) throws IOException {
        Item item = new Item();
        item.setName(itemDto.name());
        item.setDescription(itemDto.description());
        item.setPrice(itemDto.price());
        item.setCategory(itemDto.category());


        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                Map<String, String> imageUrl = cloudinaryService.upload(file);

                ImagesItens image = new ImagesItens();
                image.setImageUrl(imageUrl.get("secure_url"));
                image.setCloudinaryId(imageUrl.get("public_id"));
                image.setItem(item);
                item.getImages().add(image);
            }
        }

        return itensRepository.save(item);
    }


    public Item updateItem(String id, ItemUpdateDto itemDto, List<MultipartFile> files) throws IOException {

        Item item = itensRepository.findById(id).orElseThrow(() -> new RuntimeException("teste"));

        item.setName(itemDto.name());
        item.setDescription(itemDto.description());
        item.setPrice(itemDto.price());
        item.setAvailable(itemDto.available());

        if (files != null && !files.isEmpty()) {

            List<ImagesItens> oldImages = item.getImages();

            for (ImagesItens image : oldImages) {
                if (image.getCloudinaryId() != null) {
                    cloudinaryService.delete(image.getCloudinaryId());
                }
            }

            item.getImages().clear();

            for (MultipartFile file : files) {
                Map<String, String> imageUrl = cloudinaryService.upload(file);

                ImagesItens image = new ImagesItens();
                image.setImageUrl(imageUrl.get("secure_url"));
                image.setCloudinaryId(imageUrl.get("public_id"));
                image.setItem(item);
                item.getImages().add(image);

            }
        }

        return itensRepository.save(item);

    }


    public List<Item> getItens() {
        return itensRepository.findAll();
    }

    public Optional<Item> getItem(String id) {
        return itensRepository.findById(id);
    }


    public void deleteItem(String id) throws IOException {
        Item item = itensRepository.findById(id).orElseThrow(()-> new RuntimeException("Item não encontrado"));

        for(ImagesItens images : item.getImages()){
            if(images.getCloudinaryId() != null && !images.getCloudinaryId().isEmpty()){
                cloudinaryService.delete(images.getCloudinaryId());
            }
        }

        itensRepository.deleteById(id);
    }

}