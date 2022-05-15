package com.jibsakim.playgroundspringbootjpa.domain.product;

import com.jibsakim.playgroundspringbootjpa.constant.ProductSellStatus;
import com.jibsakim.playgroundspringbootjpa.web.dto.product.ProductUpdateRequestDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "PRODUCT")
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String productName; // 상품명

    @Column(name="price", nullable = false)
    private int price;

    @Column(nullable = false)
    private int stockNumber; // 재고수량

    @Lob
    @Column(nullable = false)
    private String productDetail; // 상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus; // 상품 판매상태

    private LocalDateTime createdAt; // 등록 시간

    private LocalDateTime updatedAt; // 수정 시간

    @Builder
    public Product (Long id, String productName, int price, int stockNumber, String productDetail, ProductSellStatus productSellStatus){
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.stockNumber = stockNumber;
        this.productDetail = productDetail;
        this.productSellStatus = productSellStatus;
        this.createdAt = LocalDateTime.now();
    }

    /* 도메인에서 처리하는 비즈니스 로직 */
    public void update(ProductUpdateRequestDto requestDto){
        this.productName = requestDto.getProductName();
        this.price = requestDto.getPrice();
        this.stockNumber = requestDto.getStockNumber();
        this.productDetail = requestDto.getProductDetail();
        this.productSellStatus = requestDto.getProductSellStatus();
        this.updatedAt = LocalDateTime.now();
    }
}
