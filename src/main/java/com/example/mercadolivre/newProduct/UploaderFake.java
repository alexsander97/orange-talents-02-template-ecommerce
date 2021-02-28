package com.example.mercadolivre.newProduct;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

//    return links para imagens que foram enviadas para algum bucket.
    public Set<String> send(List<MultipartFile> images) {
        return images.stream()
                .map(image -> "http://bucket.io/" + image.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
