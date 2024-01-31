package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.CategoryDTO;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.Category;
import com.uevitondev.deliveryapp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insertNewCategory(CategoryDTO dto) {
        Category category = new Category(null, dto.getName());
        category = categoryRepository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategoryById(Long id, CategoryDTO dto) {
        try {
            Category category = categoryRepository.getReferenceById(id);
            category.setName(dto.getName());
            category = categoryRepository.save(category);
            return new CategoryDTO(category);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity constraint violation");
        }
    }
}
