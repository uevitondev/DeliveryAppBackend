package com.uevitondev.deliveryapp.controller;

import com.uevitondev.deliveryapp.dto.ProductDTO;
import com.uevitondev.deliveryapp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAllProducts(Pageable pageable) {
        return ResponseEntity.ok().body(productService.findAllProducts(pageable));
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<Page<ProductDTO>> getAllProductsPagedByStoreId(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok().body(productService.getAllProductsByStoreId(id, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insertNewProduct(@RequestBody @Valid ProductDTO dto) {
        dto = productService.insertNewProduct(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProductById(@PathVariable Long id, @RequestBody ProductDTO dto) {
        dto = productService.updateProductById(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}

