package com.borishop.controller;

import com.borishop.service.ProductService;
import com.borishop.web.dto.product.ProductCreateRequestDto;
import com.borishop.web.dto.product.ProductResponseDto;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public Long save(@Valid @RequestBody ProductCreateRequestDto requestDto){
        return productService.save(requestDto);
//        Long productId = productService.save(requestDto);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(productId);
    }

    @PutMapping("/product/{id}")
    public Long updateInfo(@PathVariable Long id, @RequestBody ProductUpdateRequestDto requestDto){
        return productService.updateInfo(id, requestDto);
    }

    @GetMapping("/product/{id}")
    public ProductResponseDto findById(@PathVariable Long id){
        return productService.findById(id);
    }
}
