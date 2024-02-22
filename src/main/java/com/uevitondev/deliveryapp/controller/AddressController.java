package com.uevitondev.deliveryapp.controller;

import com.uevitondev.deliveryapp.dto.AddressDTO;
import com.uevitondev.deliveryapp.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/adresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        return ResponseEntity.ok().body(addressService.findAllAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok().body(addressService.findAddressById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insertNewAddress(@RequestBody @Valid AddressDTO dto) {
        dto = addressService.insertNewAddress(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateCategoryById(@PathVariable Long id, @RequestBody AddressDTO dto) {
        dto = addressService.updateAddressById(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }
}

