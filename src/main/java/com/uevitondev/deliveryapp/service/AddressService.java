package com.uevitondev.deliveryapp.service;

import com.uevitondev.deliveryapp.model.Address;
import com.uevitondev.deliveryapp.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

}
