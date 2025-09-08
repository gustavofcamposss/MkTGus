package com.mkt.br.gus.service.produto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Service
public class TokenService {

    private String acessToken;

    private String refreshToken;

    private final String BASE_URL = "https://api.mercadolibre.com";

    private final String TOKEN_URL = BASE_URL + "/oauth/token";


    // Dados para Authenticação
    private final String GRANT_TYPE = "authorization_code";
    private final String CLIENT_ID = System.getenv("ML_CLIENT_ID");
    private final String CLIENT_SECRET = System.getenv("ML_CLIENT_SECRET");
    private final String REDIRECT_URI = System.getenv("ML_REDIRECT_URI");
    private final String CODE = "TG-68be3634eef4f80001f9146b-658444467"; // FAZER O CÓDIGO MANDAR A URL REDIRECT E PEGAR ESSE CARA
    private final String CODE_VERIFIER = "$CODE_VERIFIER";

    // ⚡ RestTemplate para enviar requisições HTTP
    private final RestTemplate restTemplate = new RestTemplate();


    public void obterAcessoToken() {

        //Monta o Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); //x-www-form-urlencoded

        //Monta o Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", CLIENT_ID);
        body.add("client_secret", CLIENT_SECRET);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", CODE);
        body.add("code_verifier", CODE_VERIFIER);

        //Requisição (Body+Header)
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            /*
            Faz a Requisição;
            ResponseEntity: contêiner para a resposta HTTP completa (StatusCode, Headers e Body)
            <TokenResponse>: O corpo da resposta JSON deve ser convertido para essa classe.
            TokenResponse.class: Referência à classe real que o Spring usará para converter o JSON da API para um objeto */
            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(TOKEN_URL, request, TokenResponse.class);

            // Salva os valores retornados
            TokenResponse tokenResponse = response.getBody();
            acessToken = tokenResponse.getAccessToken();
            refreshToken = tokenResponse.getRefreshToken();


            System.out.println("Access Token: " + acessToken);
            System.out.println("Refresh Token: " + refreshToken);

        } catch (Exception e) {
            System.err.println("Erro ao obter token: " + e.getMessage());
        }

    }



    // 🔹 Classe interna para mapear JSON da resposta da API
    @Getter
    public static class TokenResponse {

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("expires_in")
        private long expiresIn;

    }
}
