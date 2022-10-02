package com.borishop.domain.product;

import com.borishop.constant.ProductSellStatus;
import com.borishop.domain.BaseTimeEntity;
import com.borishop.web.dto.product.ProductUpdateRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "product")
@ToString
public class Product extends BaseTimeEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, length = 50)
    private String name; // 상품명

    @Column(name="detail", nullable = false)
    private String detail; // 상품 상세 설명

    @Column(name="price", nullable = false)
    private int price;

    @Column(name="stock_number", nullable = false)
    private int stockNumber; // 재고수량

    @Column(name="sell_status")
    @Enumerated(EnumType.STRING)
    private ProductSellStatus sellStatus; // 상품 판매상태

    @Builder
    public Product (Long id, String name, int price, int stockNumber, String detail, ProductSellStatus sellStatus){
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.stockNumber = stockNumber;
        this.sellStatus = sellStatus;
    }

    /* 도메인에서 처리하는 비즈니스 로직 */
    public void update(ProductUpdateRequestDto requestDto){
        this.name = requestDto.getProductName();
        this.detail = requestDto.getProductDetail();
        this.price = requestDto.getPrice();
        this.stockNumber = requestDto.getStockNumber();
        this.sellStatus = requestDto.getProductSellStatus();
    }
}
