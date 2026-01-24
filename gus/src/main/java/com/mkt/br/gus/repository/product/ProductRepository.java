package com.mkt.br.gus.repository.product;

import com.mkt.br.gus.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    //Optional = Pode ou não existir esse valor - Evita retorno NULL
    Optional<Product> findById(String barcode);
}
