package com.mkt.br.gus.model.produto;


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
public class Produto {

    @Id
    @Column(length = 13)
    private String  ean;

    @Column(length = 50)
    private String nome;

    @PositiveOrZero()
    private Double preco;

    @Column(length = 80)
    private String imagem;
}