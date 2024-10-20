package one.challenge;

import java.io.IOException;
import java.util.ArrayList;
import one.challenge.helper.Api;
import one.challenge.helper.ExchangeResponse;
import one.challenge.helper.Input;
import one.challenge.helper.Utilities;

public class Main {
    private final String token;
    private final Api api;
    private Input input;
    private History history;
    public Main(String token) throws IOException {
        this.token = token;
        api = new Api();
        input = new Input();
        history = new History("./history.csv", 5);
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
                case 1 -> showHistory();
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
        ExchangeResponse result = apiQuery(base.getCode(), target.getCode(), quantity);
        if (result == null) {
            Logger.print(new String[]{"Error: Result is null"});
            return;
        }
        showResult(base.getCode(), target.getCode(), result.conversion_result);
        history.saveEntry(base.getCode(), target.getCode(), quantity, result.conversion_result);
    }
    public void showHistory() {
        Logger.print(history);
    }
    public ExchangeResponse apiQuery(String base, String target, double quantity) {
        String Url = getApiUrl(base, target, quantity);
        ExchangeResponse result = api.GET(Url, ExchangeResponse.class);
        return result;
    }
    public void showResult(String base, String target, double result) {
        String conversionResultStr = String.format("%,.2f", result);
        int realLength = conversionResultStr.length();
        int contentWidth = Math.max(realLength + 4, Math.max(base.length() + 4, 14));
        int totalLength = contentWidth + 4;
        String border = "*".repeat(totalLength);
        String padding = " ".repeat((totalLength - realLength - 2) / 2);
        String title = String.format("* %-" + contentWidth + "s *", base + " -> " + target);
        String value = String.format("*%s%s%s*", padding, conversionResultStr, padding);
        String emptyLine = String.format("* %-" + contentWidth + "s *", " ");
        Logger.print(new String[] {
            border, title, emptyLine,
            value, emptyLine, border
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
        try {
            Main app = new Main("96d63637d4c745788f9c4448");
            app.start();
        } catch (IOException e) {
            Logger.error("error starting the app :c");
        }
    }
}