package main.Actions;

import main.VarsMethods;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
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

    public int getParamCount()
    {
        return params.size();
    }

    public int getParam(int paramIndex) {
        return Integer.parseInt(params.get(paramIndex));
    }

    public Action(String action, ArrayList<JTextArea> inputs) {
        this.action = action;
        for (JTextArea input : inputs) {
            for (String p : input.getText().split(","))
            {
                if (!p.equals(""))
                {
                    params.add(p);
                }
            }
        }
    }

    public Action() {
        this.action = "";
    }

    public Action(String fromString) {
        this.action = readAction(fromString);
        int index = 0;
        while (!readParam(fromString, index).equals(""))
        {
            this.params.add(readParam(fromString, index++));
        }
    }

    @Override
    public String toString() {
        return action.replace(" ", "-") + "(" + getCommaSeperatedParameters() + ")";
    }

    private String getCommaSeperatedParameters() {
        StringBuilder paramsString = new StringBuilder();
        boolean first = true;
        for (String p : params)
        {
            if (!first)
            {
                paramsString.append(",");
            } else {
                first = false;
            }
            paramsString.append(p);
        }

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

        if (i == 0)
        {
            return getRegex("[^\\(]*\\(([^,)]*).*", str, 1);
        }
        String pattern = "";
        String basePattern = "[^,]*,";
        for (int j = 0; j < i; j++) {
            pattern += basePattern;
        }
        pattern += "([^,)]*).*";
        return getRegex(pattern, str, 1);
    }

    private String getRegex(String pattern, String str, int match) {
        Matcher m = Pattern.compile(pattern).matcher(str);
        return m.matches() ? m.group(match) : "";
    }
}
