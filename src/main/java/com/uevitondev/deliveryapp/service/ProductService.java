package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.ProductDTO;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.Category;
import com.uevitondev.deliveryapp.model.Product;
import com.uevitondev.deliveryapp.model.Store;
import com.uevitondev.deliveryapp.repository.CategoryRepository;
import com.uevitondev.deliveryapp.repository.ProductRepository;
import com.uevitondev.deliveryapp.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, StoreRepository storeRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        return productRepository.findAllProductsPaged(pageable).map(ProductDTO::new);
    }


    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProductsByStoreId(Long id, Pageable pageable) {
        return productRepository.findAllProductsPagedByStoreId(id, pageable).map(ProductDTO::new);
    }


    @Transactional(readOnly = true)
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO insertNewProduct(ProductDTO dto) {
        Product product = productRepository.save(convertProductDtoToProduct(dto));
        return new ProductDTO(product);
    }

    public Product convertProductDtoToProduct(ProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(ResourceNotFoundException::new);
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(ResourceNotFoundException::new);

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setStore(store);

        return product;
    }


    @Transactional
    public ProductDTO updateProductById(Long id, ProductDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(ResourceNotFoundException::new);

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(ResourceNotFoundException::new);

        try {
            Product product = productRepository.getReferenceById(id);
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setCategory(category);
            product.setStore(store);

            return new ProductDTO(product);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity constraint violation");
        }
    }


}
