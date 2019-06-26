package main.Strategies;

import main.Actions.Action;
import main.Actions.ActionHandler;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.Actions.Logic.IfNot;
import main.Actions.Logic.LogicHandler;
import main.Actions.SubscriptHandler;
import main.VarsMethods;
import org.parabot.environment.api.utils.Time;

import java.util.ArrayList;
import java.util.Stack;

import static main.VarsMethods.log;

public class ActionExecutor {
    private ArrayList<Action> actions;
    private ActionHandler actionHandler;
    private LogicHandler logicHandler;
    private Stack ifStack;

    private int lineIndex;

    public ActionExecutor(ArrayList<Action> actions) {
        this.actions = actions;

        actionHandler = new ActionHandler();
        logicHandler = new LogicHandler();
        ifStack = new Stack();
        ifStack.push("True");

        lineIndex = 0;
    }

    public void execute()
    {
        Action line = actions.get(lineIndex);
        log("Looking at line " + line);
        lineIndex = ++lineIndex == actions.size() ? 0 : lineIndex;

        if (line instanceof Endif)
        {
            ifStack.pop();
            return;
        }

        if (ifStack.peek().equals("True"))
        {
            VarsMethods.currentAction = line.getAction();

            try {
                executeLine(line);
            } catch (NumberFormatException notFilledIn) {
                log("Error on line " + line);
                log("Make sure you fill in all numeric values properly! Numbers only!");
            }

            Time.sleep(VarsMethods.tickSpeed);
        }

        Time.sleep(50);
    }

    private void executeLine(Action action) {
        log("Executing line");
        if (action instanceof If)
        {
            ifStack.push(logicHandler.determineIf(action));
        }
        else if (action instanceof IfNot)
        {
            ifStack.push(logicHandler.determineIfNot(action));
        }
        else
        {
            switch (action.getMethod().replace("-", " "))
            {
                case "Interact with entity":
                    actionHandler.handleInteractWith(action);
                    break;
                case "Take Ground item":
                    actionHandler.handleGroundItemInteract(action);
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
                case "Run subscript":
                    log("Case Run subscript");
                    SubscriptHandler.runSubscript(action.getParamAsString(0));
                    break;
                default:
                    log("Error: Unimplemented action: " + action.getAction());
            }
        }
    }
}
