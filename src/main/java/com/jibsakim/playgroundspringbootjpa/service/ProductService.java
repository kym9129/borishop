package com.jibsakim.playgroundspringbootjpa.service;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import com.jibsakim.playgroundspringbootjpa.domain.product.Product;
import com.jibsakim.playgroundspringbootjpa.domain.product.ProductRepository;
import com.jibsakim.playgroundspringbootjpa.web.dto.product.ProductCreateRequestDto;
import com.jibsakim.playgroundspringbootjpa.web.dto.product.ProductResponseDto;
import com.jibsakim.playgroundspringbootjpa.web.dto.product.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public Long save(ProductCreateRequestDto requestDto) {
        return productRepository.save(requestDto.toEntity()).getId();
    }

    public ProductResponseDto findById(Long id) {
        Product product = findProductById(id);
        return new ProductResponseDto(product);
    }

    @Transactional
    public Long updateInfo(Long id, ProductUpdateRequestDto requestDto) {
        Product product = findProductById(id);
        // 도메인에서 비즈니스로직 수행
        product.update(requestDto);
        return id;
    }

    public Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id="+id));
    }
}
