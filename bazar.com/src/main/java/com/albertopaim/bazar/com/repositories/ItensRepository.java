package com.albertopaim.bazar.com.repositories;


import com.albertopaim.bazar.com.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ItensRepository extends JpaRepository<Item, UUID> {

    Optional<Item> findByName(String name);
}
