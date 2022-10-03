package com.borishop.controller;

import com.borishop.service.ProductService;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import com.borishop.web.dto.product.ProductResponseDto;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> save(@Valid @RequestBody ProductCreateRequestDto requestDto){
        return ResponseEntity.ok(productService.save(requestDto));
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateInfo(@PathVariable Long id, @RequestBody ProductUpdateRequestDto requestDto){
        return ResponseEntity.ok(productService.updateInfo(id, requestDto));
    }

    @GetMapping("/product/{id}")
    public ProductResponseDto findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @GetMapping("/product")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
}
