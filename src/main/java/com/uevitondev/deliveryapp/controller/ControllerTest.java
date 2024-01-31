package com.uevitondev.deliveryapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class ControllerTest {

    @GetMapping("/public")
    public ResponseEntity<String> endpointPublic() {
        return ResponseEntity.ok().body("acesso liberado - public");
    }


    @GetMapping("/authenticated")
    public ResponseEntity<String> endpointPrivate() {
        return ResponseEntity.ok().body("acesso liberado - authenticated");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> endpointAdmin() {
        return ResponseEntity.ok().body("acesso liberado - admin");
    }


}
