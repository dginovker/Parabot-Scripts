package main.Actions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by SRH on 1/9/2018.
 */
public class Action {

    private final String action;
    private final ArrayList<String> params = new ArrayList<>();

    public String getAction() {
        return action;
    }

    public String getParamAsString(int paramIndex) {
        return params.get(paramIndex);
    }

    public int getParam(int paramIndex) {
        return Integer.parseInt(params.get(paramIndex));
    }

    public Action(String action, JTextArea[] inputs) {
        this.action = action;
        for (JTextArea input : inputs) {
            params.add(input.getText());
        }
    }

    public Action() {
        this.action = "";
    }

    public Action(String fromString) {
        this.action = readAction(fromString);
        for (int i = 0; i < 3; i++) {
            this.params.add(readParam(fromString, i));
        }
    }

    @Override
    public String toString() {
        return action.replace(" ", "-") + "(" + getCommaSeperatedParameters() + ")";
    }

    private String getCommaSeperatedParameters() {
        StringBuilder paramsString = new StringBuilder();
        for (String p : params)
            paramsString.append(p);

        return paramsString.toString();
    }

    private String readAction(String str)
    {
        if (str.equals("Endif"))
            return "Endif";

        String pattern = "((Inverse)?If )?(.*)\\(.*";
        return getRegex(pattern, str, 3);
    }

    private String readParam(String str, int i) {
        if (str.equals("Endif"))
            return "";

        switch (i)
        {
            case 0:
                return getRegex("[^\\(]*\\(([^,)]*).*", str, 1);
            case 1:
                return getRegex("[^,]*,([^,)]*).*", str, 1);
            case 2:
                return getRegex("[^,]*,[^,]*,([^,)]*).*", str, 1);
            default:
                return "";

        }
    }

    private String getRegex(String pattern, String str, int match) {
        Matcher m = Pattern.compile(pattern).matcher(str);
        return m.matches() ? m.group(match) : "";
    }
}
