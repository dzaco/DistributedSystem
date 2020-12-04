package giedronowicz;

import java.util.Scanner;

public class UIConsole {

    private static final Logger logger = Logger.of(UIConsole.class);
    private final Scanner keyboard;

    public UIConsole() {
        this.keyboard = new Scanner(System.in);
    }

    public String getRequest() {
        logger.type("Please type your request:");
        return keyboard.nextLine();
    }

    public void help() {
        logger.help("Available commands:");
        logger.help("exit\t- request for close client process",true);
        logger.help("help or h\t- request for display this help section",true);
        logger.help("off\t- request for shutdown server",true);
        logger.help("pow\t-e.g. pow 3 - request for power method", true);
    }

    public void exit() {
        logger.info("Ending process");
    }
}
