package com.mkt.br.gus.model.product;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Product {

    public Product(String ean, String name, Double price, Boolean isOver18, String image) {
        this.ean = ean;
        this.name = name;
        this.price = price;
        this.isOver18 = isOver18;
        this.image = image;
    }

    @Id
    @Column(length = 13)
    private String  ean;

    @Column(length = 120)
    private String name;

    @PositiveOrZero()
    private Double price;

    private Boolean isOver18;

    @Column(length = 200)
    private String image;


}