package com.borishop.domain;

import com.borishop.constant.ProductSellStatus;
import com.borishop.domain.product.Product;
import com.borishop.domain.product.ProductRepository;
import com.borishop.domain.product.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @PersistenceContext // [QueryDsl]영속성 컨텍스트를 사용하기 위해 EntityManager 빈 주입
    EntityManager em;

    @MockBean
    JPAQueryFactory jpaQueryFactory;
//    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    // 일부 테스트에서만 사용하기 때문에 @BeforeEach하지 않았음
    void create_product_list(){
        List<Product> productList = new ArrayList<>();
        for(int i = 1 ; i <= 10; i++){
            productList.add(Product.builder()
                    .name("[무료배송] 보리의 깃털장난감 "+i)
                    .price(5000)
                    .stockNumber(500)
                    .detail("보리가 아주 좋아하는 깃털 장난감입니다."+i)
                    .sellStatus(ProductSellStatus.SELL)
                    .build());
        }
        productRepository.saveAll(productList);
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void create_product_test(){
        // given
        Product givenProduct = Product.builder()
                .name("[무료배송] 보리의 깃털장난감")
                .price(5000)
                .stockNumber(500)
                .detail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .sellStatus(ProductSellStatus.SELL)
                .build();

        // when
        Product savedProduct = productRepository.save(givenProduct);

        // then
        assertAll(
                () -> assertThat(savedProduct.getName()).isEqualTo(givenProduct.getName()),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(givenProduct.getPrice()),
                () -> assertThat(savedProduct.getStockNumber()).isEqualTo(givenProduct.getStockNumber()),
                () -> assertThat(savedProduct.getDetail()).isEqualTo(givenProduct.getDetail()),
                () -> assertThat(savedProduct.getSellStatus()).isEqualTo(givenProduct.getSellStatus())
        );
    }

    void create_product_list2(){
        List<Product> productList = new ArrayList<>();
        for(int i = 1 ; i <= 5; i++){
            productList.add(Product.builder()
                    .name("[무료배송] 보리의 깃털장난감 "+i)
                    .price(5000 + i)
                    .stockNumber(500)
                    .detail("보리가 아주 좋아하는 깃털 장난감입니다."+i)
                    .sellStatus(ProductSellStatus.SELL)
                    .build());
        }
        for(int i = 6 ; i <= 10; i++){
            productList.add(Product.builder()
                    .name("[무료배송] 보리의 깃털장난감 "+i)
                    .price(5000 + i)
                    .stockNumber(500)
                    .detail("보리가 아주 좋아하는 깃털 장난감입니다."+i)
                    .sellStatus(ProductSellStatus.SOLD_OUT)
                    .build());
        }
        productRepository.saveAll(productList);
    }

    @Test
    @DisplayName("상품명으로 상품 조회 테스트")
    void find_by_product_name_test(){
         create_product_list();
        String productName = "[무료배송] 보리의 깃털장난감 1";
        List<Product> productList = productRepository.findByName(productName);
        assertThat(productList).extracting("name").containsOnly(productName);
    }

    @Test
    @DisplayName("JPQL로 상품 조회 테스트")
    void find_by_product_detail_using_jpql(){
        create_product_list();
        List<String> productDetailList = new ArrayList<>();
        String productDetail = "보리가 아주 좋아하는 깃털 장난감입니다.";
        for(int i = 1; i <= 10; i++){
            productDetailList.add(productDetail+i);
        }
        List<Product> productList = productRepository.findByDetail(productDetail);
        assertThat(productList).extracting("detail").containsAll(productDetailList);
    }

    @Test
    @DisplayName("BaseTimeEntity 테스트")
    void base_time_entity_test(){
        productRepository.save(Product.builder()
                .name("[무료배송] 보리의 깃털장난감 300")
                .price(5000)
                .stockNumber(500)
                .detail("보리가 아주 좋아하는 깃털 장난감입니다.")
                .sellStatus(ProductSellStatus.SELL)
                .build());

        // when
        List<Product> productList = productRepository.findByName("[무료배송] 보리의 깃털장난감 300");
        Product product = productList.get(0);

        // then
        assertThat(product.getRegDate()).isNotNull();
        assertThat(product.getModDate()).isNotNull();

    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    void querydsl_test(){
        create_product_list();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QProduct qProduct = QProduct.product;
        JPAQuery<Product> query = queryFactory.selectFrom(qProduct)
                .where(qProduct.sellStatus.eq(ProductSellStatus.SELL))
                .where(qProduct.detail.contains("아주 좋아하는"))
                .orderBy(qProduct.id.desc());

        List<Product> productList = query.fetch();
        System.out.println(productList);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    void querydsl_test2(){
        this.create_product_list2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QProduct product = QProduct.product;
        String productDetail = "보리가 아주 좋아하는 깃털 장난감입니다.";
        int price = 5003;

        // when
        booleanBuilder.and(product.detail.contains(productDetail));
        booleanBuilder.and(product.price.gt(price)); // greater than
        booleanBuilder.and(product.sellStatus.eq(ProductSellStatus.SELL));

        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> productPagingResult = productRepository.findAll(booleanBuilder, pageable);
        List<Product> productList = productPagingResult.getContent();

        System.out.println(productList);
    }

}
