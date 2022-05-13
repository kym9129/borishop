package com.jibsakim.playgroundspringbootjpa.controller;

import com.jibsakim.playgroundspringbootjpa.service.ProductService;
import com.jibsakim.playgroundspringbootjpa.web.dto.ProductAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductController {
    private final ProductService productService;

    @PostMapping("/product")
    public Long save(@Valid @RequestBody ProductAddRequestDto requestDto){
        return productService.save(requestDto);
//        Long productId = productService.save(requestDto);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(productId);
    }
}
