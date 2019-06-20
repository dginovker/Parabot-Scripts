package main.Actions.Logic;

import main.Actions.Action;

import javax.swing.*;
import java.util.ArrayList;

public class IfNot extends Action {
    @Override
    public String getAction()
    {
        return "IfNot " + super.getAction();
    }

    public IfNot(String action, ArrayList<JTextArea> inputs) {
        super(action, inputs);
    }

    public IfNot(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "IfNot " + super.toString();
    }
}
