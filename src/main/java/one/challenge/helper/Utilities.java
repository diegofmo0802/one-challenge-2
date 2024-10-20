package one.challenge.helper;

import java.util.ArrayList;

import one.challenge.Logger;

public class Utilities {
    public static int menu(Input input, String[] options, String motd) {
        int maxChars = 0;
        ArrayList<String> optionsToShow = new ArrayList<>();
        for (int index = 0; index < options.length; index ++) {
            String line = "" + index + " - " + options[index];
            optionsToShow.add(line);
            if (line.length() > maxChars) {
                maxChars = line.length();
            }
        }
        String line = "-".repeat(maxChars);
        Logger.print(line);
        Logger.print(optionsToShow.toArray(String[]::new));
        Logger.print(line);
        return input.getInt("- " + motd);
    }
}