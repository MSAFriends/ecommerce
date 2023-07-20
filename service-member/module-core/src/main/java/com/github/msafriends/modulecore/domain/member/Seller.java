package com.github.msafriends.modulecore.domain.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seller_id")
    private Long id;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<FavoriteSellerSubscription> favoriteSellerSubscriptions = new ArrayList<>();

    @Column(unique = true)
    private String nickName;

    private String userName;
    private int grade;

    @Builder
    public Seller(String nickName, String userName, int grade) {
        this.nickName = nickName;
        this.userName = userName;
        this.grade = grade;
    }
}
