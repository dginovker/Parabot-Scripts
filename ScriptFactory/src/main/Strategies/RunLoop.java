package main.Strategies;


import main.Actions.Action;
import main.Actions.ActionHandler;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by SRH on 1/15/2018.
 */
public class RunLoop implements Strategy {
    private ArrayList<Action> actions;
    private VarsMethods vars;
    private ActionHandler actionHandler;
    private Stack ifStack;

    private int lineIndex;

    public RunLoop(ArrayList<Action> actions, VarsMethods vars)
    {
        this.actions = actions;
        this.vars = vars;

        actionHandler = new ActionHandler();
        ifStack = new Stack();
        ifStack.push("True");

        lineIndex = 0;
    }

    @Override
    public boolean activate() {
        return true;
    }

    @Override
    public void execute() {
        Action line = actions.get(lineIndex);
        lineIndex = ++lineIndex == actions.size() ? 0 : lineIndex;

        vars.currentAction = line.getAction();

        if (ifStack.peek().equals("True"))
        {
            executeLine(line);
        }

        Time.sleep(1200);
    }

    private void executeLine(Action line) {
        if (line instanceof Endif)
        {
            ifStack.pop();
        }
        else if (line instanceof If)
        {
            ifStack.push(actionHandler.determineIf(line));
        }
        else
        {
            switch (line.getAction())
            {
                case "Interact with":
                    actionHandler.handleInteractWith(line);
                    break;
                case "Use item on":
                    actionHandler.useItemOn(line);
                    break;
                case "Type":
                    actionHandler.type(line);
                    break;
                case "Click (x,y)":
                    actionHandler.clickxy(line);
                    break;
                case "Sleep":
                    actionHandler.sleep(line);
                    break;
                default:
                    VarsMethods.log("Error: Unimplemented action: " + line.getAction());
            }
        }
    }
}
