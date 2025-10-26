package com.mkt.br.gus.controller;

import com.mkt.br.gus.service.auth.EdgeProfileAuth;
import com.mkt.br.gus.service.auth.TokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TesteController implements CommandLineRunner {

    private final TokenService tokenService;
    private final EdgeProfileAuth edgeProfileAuth;

    public TesteController(TokenService tokenService, EdgeProfileAuth edgeProfileAuth) {
        this.tokenService = tokenService;
        this.edgeProfileAuth = edgeProfileAuth;
    }

    @Override
    public void run(String... args) {
        String authUrl = "https://auth.mercadolivre.com.br/authorization?response_type=code&client_id=156296852273235&redirect_uri=https://github.com/gustavofcamposss";

        // Abre Edge e salva o code no atributo da classe
        edgeProfileAuth.fetchCode(authUrl);

        // Pega o code
        String code = edgeProfileAuth.getCodeTG();
        System.out.println("Code no controller: " + code);

        // Aqui você pode chamar seu tokenService passando o code se precisar
        tokenService.obterAcessoToken();
    }
}
