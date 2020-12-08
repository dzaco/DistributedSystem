package giedronowicz.server;

import giedronowicz.console.Logger;

public class PowService{
    private static final Logger logger = Logger.of(PowService.class);

    public static int pow(int number) {
        int tmp = number * number;
        logger.info("Calculating: " + number + "^2 = " + tmp);
        return tmp;
    }
    public static int pow(String number) {
        try {
            int tmp = Integer.parseInt(number);
            return pow(tmp);

        } catch (NumberFormatException e) {
            logger.error(number + " is not a number");
            return 0;   // TODO 0 or null or what?
        }
    }
}
