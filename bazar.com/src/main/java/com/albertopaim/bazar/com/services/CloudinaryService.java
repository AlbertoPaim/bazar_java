package com.albertopaim.bazar.com.services;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map<String, String> upload(MultipartFile file) throws IOException {

        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString())
            );

            return Map.of("secure_url", (String) uploadResult.get("secure_url"),
                    "public_id", (String) uploadResult.get("public_id"));

        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel fazer o upload da imagem");
        }

    }

    public void delete(String publicId) throws IOException {

        try {
            cloudinary.uploader().destroy(publicId, Map.of());

        } catch (IOException e) {
            throw new RuntimeException("Não foi possivel deletar imagem", e);
        }

    }

}
