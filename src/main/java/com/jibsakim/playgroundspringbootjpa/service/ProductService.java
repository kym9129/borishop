package com.jibsakim.playgroundspringbootjpa.service;

import com.jibsakim.playgroundspringbootjpa.domain.product.ProductRepository;
import com.jibsakim.playgroundspringbootjpa.web.dto.ProductAddRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long save(ProductAddRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity()).getId();
    }
}
