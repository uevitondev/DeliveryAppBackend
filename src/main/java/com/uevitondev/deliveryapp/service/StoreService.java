package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.dto.StoreDTO;
import com.uevitondev.deliveryapp.exceptions.DatabaseException;
import com.uevitondev.deliveryapp.exceptions.ResourceNotFoundException;
import com.uevitondev.deliveryapp.model.Store;
import com.uevitondev.deliveryapp.repository.StoreRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional(readOnly = true)
    public List<StoreDTO> findAllStores() {
        return storeRepository.findAll().stream().map(StoreDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public StoreDTO findStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new StoreDTO(store);
    }

    @Transactional
    public StoreDTO insertNewStore(StoreDTO dto) {
        Store store = new Store();
        store.setName(dto.getName());
        store = storeRepository.save(store);

        return new StoreDTO(store);
    }

    @Transactional
    public StoreDTO updateStoreById(Long id, StoreDTO dto) {
        try {
            Store store = storeRepository.getReferenceById(id);
            store.setName(dto.getName());
            store = storeRepository.save(store);
            return new StoreDTO(store);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteStoreById(Long id) {
        if (!storeRepository.existsById(id)) {
            throw new ResourceNotFoundException();
        }
        try {
            storeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referential integrity constraint violation");
        }
    }
}