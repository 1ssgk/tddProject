package com.wonseok.subject.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wonseok.subject.domain.common.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item extends BaseTimeEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;


    /**
     * 브랜드가 있다는 설정
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    /**
     * 카테고리
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


    //==비즈니스 로직==//

    /**
     * name 변경
     */
    public void changeName(String name) {
        this.name = name;
    }

    /**
     * price 변경
     */
    public void changePrice(int price) {
        this.price = price;
    }

    /**
     * stockQuantity 변경
     */
    public void changeQuantity(int quantity) {
        this.stockQuantity = quantity;
    }

    /**
     * stock 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if(this.stockQuantity == 0){
            throw new IllegalStateException("["+this.name+"] 해당 제품은 매진 되었습니다.");
        }
        if (restStock < 0) {
            // 에러 발생
            throw new IllegalStateException("["+this.name+"] 주문 수량이 남아있는 수량보다 큽니다.");
        }
        this.stockQuantity = restStock;
    }
    public void setBrand(Long brandId) {
        this.brand = Brand.builder()
                .id(brandId)
                .build();
    }

    public void setCategory(Long categoryId) {
        this.category = Category.builder()
                .id(categoryId)
                .build();
    }


}
