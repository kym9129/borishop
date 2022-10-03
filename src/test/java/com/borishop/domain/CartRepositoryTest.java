package com.borishop.domain;

import com.borishop.domain.cart.Cart;
import com.borishop.domain.cart.CartRepository;
import com.borishop.domain.user.User;
import com.borishop.domain.user.UserRepository;
import com.borishop.web.dto.auth.UserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    JPAQueryFactory jpaQueryFactory;

    public User createUser(){
        UserDto dto = UserDto.builder()
                .email("test@gmail.com")
                .password("1234")
                .nickname("테스트")
                .build();
        return User.create(dto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    void find_cart_and_user_test(){
        User user = createUser();
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        em.flush();
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);
        assertThat(savedCart.getUser().getId()).isEqualTo(user.getId());
    }
}
