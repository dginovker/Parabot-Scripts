package scriptfactory.Actions.Logic;

import scriptfactory.Actions.Action;

import javax.swing.*;

import java.util.ArrayList;

/**
 * Created by SRH on 1/9/2018.
 */
public class If extends Action {
    @Override
    public String getAction()
    {
        return "If " + super.getAction();
    }

    public If(String action, ArrayList<JTextArea> inputs) {
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
