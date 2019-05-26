package main.Actions.Logic;

import main.Actions.Action;

import static main.VarsMethods.log;

/**
 * Created by SRH on 1/9/2018.
 */
public class If extends Action {
    public If(String action, String param0, String param1, String param2) {
        super(action, param0, param1, param2);
    }

    public If(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "If " + getAction().replace(" ", "-") +
                "(" + getParam0() + "," + getParam1() + "," + getParam2() + ")";
    }
}
