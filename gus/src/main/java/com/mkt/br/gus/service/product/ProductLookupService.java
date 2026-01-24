package com.mkt.br.gus.service.product;

import com.mkt.br.gus.model.product.Product;
import com.mkt.br.gus.repository.product.ProductRepository;
import com.mkt.br.gus.util.scanner.events.BarcodeDetectedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProductLookupService {

    private final MLApiService mlApiService;
    private final ProductRepository productRepository;

    public ProductLookupService(MLApiService mlApiService, ProductRepository productRepository) {
        this.mlApiService = mlApiService;
        this.productRepository = productRepository;
    }

    //Escuta eventos publicados @EventListener (Disparado pelo Scanner)
    @EventListener
    public void onBarcodeDetected(BarcodeDetectedEvent eventEan) {

        //Obtendo EAN
        String ean = eventEan.ean();

        // 1. buscar no banco
        Optional<Product> productFromDb = productRepository.findById(ean);

        if (productFromDb.isPresent()) {
            // achou no banco
            Product product = productFromDb.get();
            System.out.println("Produto encontrado no banco: " + product.getName());
            return;
        } else {
            System.out.println("Produto NÃO encontrado no banco: " + ean);
        }

        // 2. se não existir, chamar API

        Product productFromApi = mlApiService.getProductByBarcode(ean);


        if (productFromApi != null) {
            // 3 salva no banco
            productRepository.save(productFromApi);
            System.out.println("Produto buscado na API e salvo no banco");
        }
    }

}