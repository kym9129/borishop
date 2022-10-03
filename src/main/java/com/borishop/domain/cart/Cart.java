package com.borishop.domain.cart;

import com.borishop.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    /**
     * 카트에 회원 정보 연결
     * @param user
     */
    public void setUser(User user){
        this.user = user;
    }
}
