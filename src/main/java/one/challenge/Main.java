package one.challenge;

import java.net.http.HttpClient;

import one.challenge.helper.Api;
import one.challenge.helper.ExchangeResponse;

public class Main {
    private final String token;
    private final Api api;
    public Main(String token) {
        this.token = token;
        api = new Api();
    }
    public void start() {

    }
    /**
     * get the Exchange Rate API url
     * @param Token your api token
     * @param base you base currency code
     * @param target your target currency code
     * @param amount the amount to convert
     */
    public String getApiUrl(String base, String target, Double amount) {
        
        return String.join("/", new String [] {
            "https://v6.exchangerate-api.com/v6",
            token, "pair", base, target, amount.toString()
        });
    }
    public static void main(String[] args) {
        // El token es visible unicamente por agilizar la revision del challenge por parte de Alura/oracle
        // Luego de la revisión del mismo este sera deshabilitado
         Main app = new Main("96d63637d4c745788f9c4448");
         app.start();
    }
}