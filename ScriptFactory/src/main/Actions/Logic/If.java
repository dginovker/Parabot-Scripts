package main.Actions.Logic;

import main.Actions.Action;

import javax.swing.*;

import static main.VarsMethods.log;

/**
 * Created by SRH on 1/9/2018.
 */
public class If extends Action {
    public If(String action, JTextArea inputs[]) {
        super(action, inputs);
    }

    public If(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "If " + super.toString();
    }
}
