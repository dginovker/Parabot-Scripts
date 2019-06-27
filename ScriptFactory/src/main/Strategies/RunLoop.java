package main.Strategies;


import main.Actions.Action;
import main.VarsMethods;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;

/**
 * Created by SRH on 1/15/2018.
 */
public class RunLoop implements Strategy {

    private ActionExecutor actionExecutor;

    public RunLoop(ArrayList<Action> actions)
    {
        actionExecutor = new ActionExecutor(actions);
    }

    @Override
    public boolean activate() {
        return true;
    }

    @Override
    public void execute() {
        actionExecutor.execute();
    }

}
