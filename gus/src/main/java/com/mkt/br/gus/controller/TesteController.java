package com.mkt.br.gus.controller;

import com.mkt.br.gus.service.produto.TokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class TesteController implements CommandLineRunner {

    private final TokenService teste;

    public TesteController(TokenService teste) {
        this.teste = teste;
    }

    @Override
    public void run(String... args) {
        teste.obterAcessoToken();
        System.out.println("Token gerado automaticamente!");
    }
}