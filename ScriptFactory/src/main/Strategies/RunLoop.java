package main.Strategies;


import main.Actions.Action;
import main.Actions.ActionHandler;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.Actions.Logic.InverseIf;
import main.Actions.Logic.LogicHandler;
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
    private LogicHandler logicHandler;
    private Stack ifStack;

    private int lineIndex;

    public RunLoop(ArrayList<Action> actions, VarsMethods vars)
    {
        this.actions = actions;
        this.vars = vars;

        actionHandler = new ActionHandler();
        logicHandler = new LogicHandler();
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

        if (line instanceof Endif)
        {
            ifStack.pop();
            return;
        }

        if (ifStack.peek().equals("True"))
        {
            vars.currentAction = line.getAction();

            try {
                executeLine(line);
            } catch (NumberFormatException notFilledIn) {
                log("Error on line " + line);
                log("Make sure you fill in all numeric values properly! Numbers only!");
                if (line.toString().contains("\t"))
                {
                    log("Potential resolution: Tab character detected on the line");
                }
            }

            Time.sleep(VarsMethods.tickSpeed);
        }

        Time.sleep(50);
    }

    private void executeLine(Action action) {
        if (action instanceof If)
        {
            ifStack.push(logicHandler.determineIf(action));
        }
        else if (action instanceof InverseIf)
        {
            ifStack.push(logicHandler.determineInverseIf(action));
        }
        else
        {
            switch (action.getAction().replace("-", " "))
            {
                case "Interact with":
                    actionHandler.handleInteractWith(action);
                    break;
                case "Inventory item interact":
                    actionHandler.inventoryItemInteract(action);
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
                case "Send raw Action":
                    actionHandler.sendRawAction(action);
                    break;
                case "Walk to":
                    actionHandler.walkTo(action);
                    break;
                default:
                    log("Error: Unimplemented action: " + action.getAction());
            }
        }
    }
}
