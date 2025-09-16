package com.albertopaim.bazar.com.services;

import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.repositories.ItensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItensRepository itensRepository;

    public Item createItem(Item item) {
        return itensRepository.save(item);
    }

    public List<Item> getItens() {
        return itensRepository.findAll();
    }

    public Optional<Item> getItem(String id) {
        return itensRepository.findById(id);
    }

    public void updateItem(Item item){

       itensRepository.save(item);
    }

    public void deleteItem(String id) {
        itensRepository.deleteById(id);
    }
}
