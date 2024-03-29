package com.uevitondev.deliveryapp.dto;

import com.uevitondev.deliveryapp.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private Long id;

    @NotBlank(message = "invalid: name is empty")
    @NotNull(message = "invalid: name is null")
    private String name;
    private String imageUrl;
    @NotBlank(message = "invalid: description is empty")
    @NotNull(message = "invalid: description is null")
    private String description;

    @NotNull(message = "invalid: price is null")
    private Double price;

    @NotNull(message = "invalid: categoryId is null")
    private Long categoryId;

    @NotNull(message = "invalid: storeId is null")
    private Long storeId;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String imageUrl, String description, Double price, Long categoryId, Long storeId) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.storeId = storeId;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.imageUrl = product.getImageUrl();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId();
        this.storeId = product.getStore().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
