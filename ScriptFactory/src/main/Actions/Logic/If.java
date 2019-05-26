package main.Actions.Logic;

import main.Actions.Action;

/**
 * Created by SRH on 1/9/2018.
 */
public class If extends Action {
    public If(String action, String param1, String param2, String param3) {
        super(action, param1, param2, param3);
    }

    public If(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString() {
        return "If" + getAction().replace(" ", "-") +
                "(" + getParam0() + "," + getParam1() + "," + getParam2() + ")";
    }
}
