package com.mkt.br.gus.service.auth;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class EdgeProfileAuth {

    // Atributo que vai guardar o code obtido
    private String codeTG;

    /**
     * Abre o Edge usando seu perfil real e navega para a URL de OAuth2.
     * Salva o parâmetro 'code' no atributo codeTG assim que a URL é carregada.
     *
     * @param authUrl URL de autorização
     */
    public void fetchCode(String authUrl) {

        // Defina manualmente o caminho para o EdgeDriver
        System.setProperty("webdriver.edge.driver", "C:\\DriverEdge\\edgedriver_win64\\msedgedriver.exe");

        EdgeOptions options = new EdgeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\gusta\\AppData\\Local\\Microsoft\\Edge\\User Data");
        options.addArguments("--profile-directory=Default");

        WebDriver driver = null;
        try {
            driver = new EdgeDriver(options);
            driver.get(authUrl);

            System.out.println("Edge aberto com seu perfil. Navegando para: " + authUrl);

            // Pega a URL final imediatamente
            String finalUrl = driver.getCurrentUrl();
            this.codeTG = extractQueryParam(finalUrl, "code");

            System.out.println("Code obtido: " + this.codeTG);

        } catch (Exception ex) {
            ex.printStackTrace();
            this.codeTG = null;
        } finally {
            if (driver != null) {
                try { driver.quit(); } catch (Exception ignored) {}
            }
        }
    }

    // Getter para o codeTG
    public String getCodeTG() {
        return codeTG;
    }

    // Extrai o valor de um query param da URL
    private String extractQueryParam(String url, String param) {
        if (url == null) return null;
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            if (query != null) {
                for (String pair : query.split("&")) {
                    int idx = pair.indexOf('=');
                    if (idx > 0) {
                        String name = pair.substring(0, idx);
                        String value = pair.substring(idx + 1);
                        if (name.equals(param)) return value;
                    }
                }
            }
            String frag = uri.getFragment();
            if (frag != null) {
                for (String pair : frag.split("&")) {
                    int idx = pair.indexOf('=');
                    if (idx > 0) {
                        String name = pair.substring(0, idx);
                        String value = pair.substring(idx + 1);
                        if (name.equals(param)) return value;
                    }
                }
            }
        } catch (URISyntaxException e) {
            int idx = url.indexOf(param + "=");
            if (idx >= 0) {
                int start = idx + param.length() + 1;
                int end = url.indexOf('&', start);
                if (end == -1) end = url.length();
                return url.substring(start, end);
            }
        }
        return null;
    }
}
