package giedronowicz.console;

public class Logger {
    private static Logger instance = null;
    private final String name;


    private Logger(Class c ) {
        name = c.getSimpleName();
    }
    private Logger() {
        name = Logger.class.getSimpleName();
    }

    public static Logger getInstance() {
        if( instance == null )
            instance = new Logger();
        return instance;
    }

    public static Logger of(Class c) {
        instance = new Logger(c);
        return getInstance();
    }

    public void info(String msg) {
        System.out.format("%-30s %-20s %n", "["+ name +"]", msg);
    }
    public void info(String msg, boolean nextLine) {
        if(nextLine)
            System.out.format("%-30s %-20s %n", "", msg);
        else
            info(msg);
    }
    public void info(String msg, String from) {
        System.out.format("%-30s %-20s %n", "["+ from +"]", msg);
    }
    public void error(String msg) {
        System.out.format("%-30s %-20s %n", "[ERROR]["+ name +"]", msg);
    }

    public void help(String msg) {
        System.out.format("%-30s %-20s %n", "[HELP]", msg);
    }
    public void help(String msg, boolean nextLine) {
        this.info(msg, true);
    }


    public void response(String msg) {
        this.info(msg,"ServerResponse");
    }

    public void type(String msg) {
        System.out.format("%-30s %-20s", "", msg);
    }
}
