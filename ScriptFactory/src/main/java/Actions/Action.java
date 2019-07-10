package main.java.Actions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.VarsMethods.log;


/**
 * Created by SRH on 1/9/2018.
 */
public class Action {

    private final String action;
    private final ArrayList<String> params = new ArrayList<>();

    /**
     * Gets the full action, i.e. type and method name
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets only the method name
     */
    public String getMethod() {
        return action;
    }

    public String getParamAsString(int paramIndex) {
        try {
            return params.get(paramIndex);
        } catch (IndexOutOfBoundsException e) {
            log("Error parsing parameter in the following action - did you fill them all out properly?");
            log("Action: " + this.toString());
            return "1";
        }
    }

    public int getParamCount()
    {
        return params.size();
    }

    public int getParam(int paramIndex) {
        return Integer.parseInt(getParamAsString(paramIndex));
    }

    public int[] getParamArray()
    {
        return params.stream().mapToInt(Integer::parseInt).toArray();
    }

    public Action(String action, ArrayList<JTextArea> inputs) {
        if (action.equals("Comment"))
        {
            this.action = "//";
            params.add(inputs.get(0).getText());
            return;
        }
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
        if (fromString.startsWith("//"))
        {
            this.action = "Comment";
            params.add(fromString.replaceFirst("//", ""));
            return;
        }
        this.action = readAction(fromString);
        int index = 0;
        while (!readParam(fromString, index).equals(""))
        {
            this.params.add(readParam(fromString, index++));
        }
    }

    @Override
    public String toString() {
        if (action.equals("//"))
            return "//" + params.get(0);
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

        String pattern = "(If(Not)? )?(.*)\\(.*";
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
