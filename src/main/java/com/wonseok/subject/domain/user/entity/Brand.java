package com.wonseok.subject.domain.user.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Brand {

    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String logoUrl;

    @Column(name = "activated", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean activated;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    /*==비즈니스 로직*/
    public void addItem(Item item){
        this.items.add(item);
    }

}
