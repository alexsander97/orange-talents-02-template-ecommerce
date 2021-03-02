package com.example.mercadolivre.getDetailsProduct;

import com.example.mercadolivre.newProduct.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/api/product-details")
public class DetailsProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public DetailsProductResponse getProduct(@PathVariable("id") Long id) {
        Product product = entityManager.find(Product.class, id);
        return new DetailsProductResponse(product);
    }
}
