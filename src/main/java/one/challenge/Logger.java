package one.challenge;

public class Logger {
    public static void log(String[] messages) {
        for (String message : messages) log(message);
    }
    public static void log(Object message) {
        log(message.toString(), true);
    } 
    public static void log(String message) {
        log(message, true);
    } 
    public static void log(String message, boolean endLine) {
        System.out.print("[log]: " + message + (endLine ? "\n" : ""));
    }
    public static void error(String[] messages) {
        for (String message : messages) error(message);
    }
    public static void error(Object message) {
        error(message.toString(), true);
    } 
    public static void error(String message) {
        error(message, true);
    } 
    public static void error(String message, boolean endLine) {
        System.err.print("[error]: " + message + (endLine ? "\n" : ""));
    }
}
