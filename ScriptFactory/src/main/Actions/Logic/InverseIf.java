package main.Actions.Logic;

import main.Actions.Action;

import javax.swing.*;

public class InverseIf extends Action {

    public InverseIf(String action, JTextArea[] inputs) {
        super(action, inputs);
    }

    public InverseIf(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "InverseIf " + getAction().replace(" ", "-") +
                "(" + getParamAsString(0) + "," + getParamAsString(1) + "," + getParamAsString(2) + ")";
    }
}
