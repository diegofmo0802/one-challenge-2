package one.challenge.helper;

import java.util.Scanner;

import one.challenge.Logger;

public class Input {
    private final Scanner scanner;
    public Input() {
        scanner = new Scanner(System.in);
    }
    public String get(String motd) {
        Logger.print(motd + " > ", false);
        return scanner.nextLine();
    }
    public int getInt(String motd) {
        while (true) {
            int result;
            try {
                String input = get(motd);
                result = Integer.parseInt(input);
                return result;
            } catch (NumberFormatException e) {
                Logger.print("the provided content is not a number");
            }
        }
    }
    public double getDouble(String motd) {
        while (true) {
            Double result;
            try {
                String input = get(motd);
                result = Double.parseDouble(input);
                return result;
            } catch (NumberFormatException e) {
                Logger.print("the provided content is not a number");
            }
        }
    }
}
