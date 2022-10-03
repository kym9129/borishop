package com.borishop.web.dto.product;

import com.borishop.constant.ProductSellStatus;
import com.borishop.domain.product.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductResponseDto {
    private Long productId;
    private String productName; // 상품명
    private int price;
    private int stockNumber; // 재고수량
    private String productDetail; // 상품 상세 설명
    private ProductSellStatus productSellStatus; // 상품 판매상태
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime regDate; // 등록 시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modDate; // 수정 시간

    public ProductResponseDto(Product entity){
        this.productId = entity.getId();
        this.productName = entity.getName();
        this.price = entity.getPrice();
        this.stockNumber = entity.getStockNumber();
        this.productDetail = entity.getDetail();
        this.productSellStatus = entity.getSellStatus();
        this.regDate = entity.getRegDate();
        this.modDate = entity.getModDate();
    }
}
