package main.Actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.VarsMethods.log;

/**
 * Created by SRH on 1/9/2018.
 */
public class Action {

    private final String action;
    private final String param0;
    private final String param1;
    private final String param2;

    public String getAction() {
        return action;
    }

    public String getParam0() {
        return param0;
    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public Action(String action, String param0, String param1, String param2) {
        this.action = action;
        this.param0 = param0;
        this.param1 = param1;
        this.param2 = param2;
    }

    public Action() {
        this.action = "";
        this.param0 = "";
        this.param1 = "";
        this.param2 = "";
    }

    public Action(String fromString) {
        this.action = readAction(fromString);
        this.param0 = readParam(fromString, 0);
        this.param1 = readParam(fromString, 1);
        this.param2 = readParam(fromString, 2);
    }

    @Override
    public String toString() {
        return action.replace(" ", "-") +
                "(" + param0 + "," + param1 + "," + param2 + ")";
    }

    String readAction(String str)
    {
        if (str.equals("Endif"))
            return "Endif";

        String pattern = "((Inverse)?If )?(.*)\\(.*";
        return getRegex(pattern, str, 3);
    }

    String readParam(String str, int i) {
        if (str.equals("Endif"))
            return "";

        String pattern = ".*\\(";
        for (int j = 0; j < i; j++)
        {
            pattern += ".*,";
        }
        pattern += "(.*?)[,)].*";
        log("Tried to get parameter " + i + " of " + str + ", got " + getRegex(pattern, str, 1));
        return getRegex(pattern, str, 1);
    }

    private String getRegex(String pattern, String str, int match) {
        Matcher m = Pattern.compile(pattern).matcher(str);
        m.matches();
        return m.group(match);
    }
}
