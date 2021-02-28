package com.example.mercadolivre.newProduct;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NewImagesRequest {

    @Size(min = 1)
    @NotNull
    private List<MultipartFile> images;

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }
}
