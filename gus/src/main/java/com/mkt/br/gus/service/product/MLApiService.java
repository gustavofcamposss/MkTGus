package com.mkt.br.gus.service.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mkt.br.gus.model.product.Product;
import com.mkt.br.gus.service.auth.TokenService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class MLApiService {

    private static final String PRODUCT_SEARCH_URL = "https://api.mercadolibre.com/products/search";
    //private static final String PRODUCT_ITEMS_URL = "https://api.mercadolibre.com/products/%s/items";
    private static final String SITE_ID = "MLB";

    //private final List<Product> listaDeProdutos = new ArrayList<>(); EXCLUIR PROVAVELMENTE
    private Product productFound;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final TokenService tokenService;

    public MLApiService(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    public Product getProductByBarcode(String barcode) {

        // Passo 1: Buscar o ID do produto usando /products/search
        String productUrl = PRODUCT_SEARCH_URL + "?site_id=" + SITE_ID + "&status=active&product_identifier=" + barcode;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenService.getAcessToken());


        try {
            ResponseEntity<String> productResponse = restTemplate.exchange(
                    productUrl, HttpMethod.GET,
                    new org.springframework.http.HttpEntity<>(headers),
                    String.class
            );

            JsonNode productRoot = objectMapper.readTree(productResponse.getBody());
            JsonNode productResults = productRoot.path("results");

            if (!productResults.isArray() || productResults.isEmpty()) {
                System.out.println("Nenhum produto encontrado em /products/search para o código de barras: " + barcode);
                return null;
            }

            JsonNode productNode = productResults.get(0);
            String productId = productNode.path("id").asText();
            String name = productNode.path("name").asText();

            // Acessar a URL da imagem do primeiro elemento do array "pictures"
            String thumbnail = productNode.path("pictures").isArray() && !productNode.path("pictures").isEmpty()
                    ? productNode.path("pictures").get(0).path("url").asText(null)
                    : null;

            // Passo 2: Buscar o preço do produto usando /products/$idmercadolivre/items
            //double price = getProductPrice(productId);

            System.out.println(
                    "productId=" + productId +
                            " | name=" + name +
                            " | Imagem=" + thumbnail
            );


            productFound = new Product(productId, name, null, null, thumbnail);

        } catch (Exception e){

        }
        return productFound;
    }

}