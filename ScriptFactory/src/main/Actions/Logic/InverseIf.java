package main.Actions.Logic;

import main.Actions.Action;

import static main.VarsMethods.log;

public class InverseIf extends Action {

    public InverseIf(String action, String param1, String param2, String param3) {
        super(action, param1, param2, param3);
    }

    public InverseIf(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "InverseIf " + getAction().replace(" ", "-") +
                "(" + getParam0() + "," + getParam1() + "," + getParam2() + ")";
    }
}
