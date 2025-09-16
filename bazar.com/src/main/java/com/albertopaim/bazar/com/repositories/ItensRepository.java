package com.albertopaim.bazar.com.repositories;


import com.albertopaim.bazar.com.entities.Item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItensRepository extends JpaRepository<Item, String> {

    Optional<Item> findByName(String name);
}
