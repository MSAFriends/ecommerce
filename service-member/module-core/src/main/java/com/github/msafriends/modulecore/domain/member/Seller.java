package com.github.msafriends.modulecore.domain.member;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seller_id")
    private Long id;

    @Column(unique = true)
    private String nickName;

    private String userName;
    private int grd;

    @Builder(builderClassName = "BySellerAccountBuilder", builderMethodName = "BySellerAccountBuilder")
    public Seller(String nickName, String userName, int grd) {
        this.nickName = nickName;
        this.userName = userName;
        this.grd = grd;
    }
}
