package main.Actions.Logic;

import main.Actions.Action;

import javax.swing.*;
import java.util.ArrayList;

public class InverseIf extends Action {

    public InverseIf(String action, ArrayList<JTextArea> inputs) {
        super(action, inputs);
    }

    public InverseIf(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "InverseIf " + super.toString();
    }
}
