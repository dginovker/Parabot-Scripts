package main.Strategies;


import main.Actions.Action;
import main.Actions.ActionHandler;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.Actions.Logic.InverseIf;
import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;
import java.util.Stack;

import static main.VarsMethods.log;

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
            try {
                executeLine(line);
            } catch (NumberFormatException notFilledIn)
            {
                log("Error on line " + line);
                log("Make sure you fill in all numeric values properly! Numbers only!");
            }
        }

        Time.sleep(VarsMethods.tickSpeed);
    }

    private void executeLine(Action action) {
        if (action instanceof Endif)
        {
            ifStack.pop();
        }
        else if (action instanceof If)
        {
            ifStack.push(actionHandler.determineIf(action));
        }
        else if (action instanceof InverseIf)
        {
            ifStack.push(actionHandler.determineInverseIf(action));
        }
        else
        {
            switch (action.getAction().replace("-", " "))
            {
                case "Interact with":
                    actionHandler.handleInteractWith(action);
                    break;
                case "Use item on":
                    actionHandler.useItemOn(action);
                    break;
                case "Type":
                    actionHandler.type(action);
                    break;
                case "Click xy":
                    actionHandler.clickxy(action);
                    break;
                case "Sleep":
                    actionHandler.sleep(action);
                    break;
                default:
                    log("Error: Unimplemented action: " + action.getAction());
            }
        }
    }
}
