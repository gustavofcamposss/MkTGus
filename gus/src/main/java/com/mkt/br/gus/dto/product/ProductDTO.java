package com.mkt.br.gus.dto.product;


import jakarta.validation.constraints.NotBlank;


public record ProductDTO(

        @NotBlank(message = "O código não pode ser Nulo")
        String  ean,

        @NotBlank(message = "O nome não pode ser Nulo")
        String name,

        Double price,

        String image

) {
}
