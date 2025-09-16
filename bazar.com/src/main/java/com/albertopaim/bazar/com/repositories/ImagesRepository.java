package com.albertopaim.bazar.com.repositories;

import com.albertopaim.bazar.com.entities.Images.ImagesItens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImagesRepository extends JpaRepository<ImagesItens, UUID> {

}
