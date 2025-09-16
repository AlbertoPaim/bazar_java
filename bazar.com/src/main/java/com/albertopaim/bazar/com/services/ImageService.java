package com.albertopaim.bazar.com.services;


import com.albertopaim.bazar.com.controllers.ItemController;
import com.albertopaim.bazar.com.entities.Item.Item;
import com.albertopaim.bazar.com.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {



    @Autowired
    private ImagesRepository imagesRepository;

    public void uploadImages(String id){
    }


}
