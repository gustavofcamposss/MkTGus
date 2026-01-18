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

    @Id
    @Column(length = 13)
    private String  ean;

    @Column(length = 50)
    private String name;

    @PositiveOrZero()
    private Double price;

    @Column(length = 80)
    private String image;
}