package com.mkt.br.gus.service.auth.orchestrator;

import com.mkt.br.gus.service.auth.EdgeProfileAuth;
import com.mkt.br.gus.service.auth.TokenService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service //Se tornar um bean
public class AuthOrchestrator {

    private final EdgeProfileAuth edgeProfileAuth;

    private final TokenService tokenService;

    String authUrl = "https://auth.mercadolivre.com.br/authorization?response_type=code&client_id=" + System.getenv("ML_CLIENT_ID") +
            "&redirect_uri=" + System.getenv("ML_REDIRECT_URI");

    public AuthOrchestrator(EdgeProfileAuth edgeProfileAuth, TokenService tokenService) {
        this.edgeProfileAuth = edgeProfileAuth;
        this.tokenService = tokenService;
    }

    @PostConstruct
    public void runAuthFlow() {

        //Obtém o Código Incial
        String codeInitial = edgeProfileAuth.fetchCode(authUrl);

        //Envia o código para class de Token
        tokenService.getAccessToken(codeInitial);
    }
}
