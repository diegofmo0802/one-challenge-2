package one.challenge;

import java.util.ArrayList;

import one.challenge.helper.Api;
import one.challenge.helper.ExchangeResponse;
import one.challenge.helper.Input;
import one.challenge.helper.Utilities;

public class Main {
    private final String token;
    private final Api api;
    public Input input;
    public Main(String token) {
        this.token = token;
        api = new Api();
        input = new Input();
    }
    public void start() {
        while (true) {
            int selected = Utilities.menu(input, new String[] {
                "currency exchange",
                "exchange history",
                "exit"
            }, "select an option");
            Logger.log(selected);
            switch (selected) {
                case 0 -> exchangeMenu();
                default -> Logger.print("invalid option");
            }
        }
    }
    public void exchangeMenu() {
        ArrayList<String> optionList = new ArrayList<>();
        for (Currency item : Currency.values()) {
            optionList.add("[" + item.getCode() + "] " + item.getName());
        }
        int baseIndex = Utilities.menu(input, optionList.toArray(String[]::new), "select base currency");
        int targetIndex = Utilities.menu(input, optionList.toArray(String[]::new), "select target currency");
        Double quantity = input.getDouble("- select the quantity");
        Currency base = Currency.get(baseIndex);
        Currency target = Currency.get(targetIndex);
        String Url = getApiUrl(base.getCode(), target.getCode(), quantity);
        ExchangeResponse result = api.GET(Url, ExchangeResponse.class);
        showResult(result);
    }
    public void showResult(ExchangeResponse result) {
        if (result == null) {
            Logger.print(new String[]{"Error: Result is null"});
            return; // Salir si el resultado es nulo
        }
    
        // Formatear el resultado de la conversión con comas como separadores de miles
        String conversionResultStr = String.format("%,.2f", result.conversion_result);
        int realLength = conversionResultStr.length();
    
        // Definir el ancho mínimo y calcular dinámicamente según el contenido
        int contentWidth = Math.max(realLength + 4, Math.max(result.base_code.length() + 4, 14));
        int totalLength = contentWidth + 4; // Añadir margen a los lados (borde izquierdo y derecho)
        
        // Crear bordes y padding
        String border = "*".repeat(totalLength);
        String padding = " ".repeat((totalLength - realLength - 2) / 2);
    
        // Encabezado y cuerpo centrados
        String title = String.format("* %-" + contentWidth + "s *", result.base_code + " -> " + result.target_code);
        String value = String.format("*%s%s%s*", padding, conversionResultStr, padding);
    
        // Crear una línea vacía con el mismo ancho
        String emptyLine = String.format("* %-" + contentWidth + "s *", " ");
    
        // Imprimir el resultado decorado
        Logger.print(new String[] {
            border,
            title,
            emptyLine, // Línea vacía decorativa
            value,
            emptyLine, // Línea vacía decorativa
            border
        });
    }    
    /**
     * get the Exchange Rate API url
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