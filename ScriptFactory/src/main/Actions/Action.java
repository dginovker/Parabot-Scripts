package main.Actions;

import main.VarsMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SRH on 1/9/2018.
 */
public class Action {

    private final String action;
    private final String param1;
    private final String param2;
    private final String param3;

    public String getAction() {
        return action;
    }

    public String getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public String getParam3() {
        return param3;
    }

    public Action(String action, String param1, String param2, String param3)
    {
        this.action = action;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public Action() {
        this.action = null;
        this.param1 = null;
        this.param2 = null;
        this.param3 = null;
    }

    public Action(String fromString)
    {
        String pattern = ".*action='([^']*)'.*param1='([^']*)'.*param2='([^']*)'.*param3='([^']*)'.*";//param3='([^']*)'.*'";

        Matcher m  = Pattern.compile(pattern).matcher(fromString);
        m.matches();

        this.action = m.group(1);
        this.param1 = m.group(2);
        this.param2 = m.group(3);
        this.param3 = m.group(4);
    }

    @Override
    public String toString() {
        return "Action{" +
                "action='" + action + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                '}';
    }

}
