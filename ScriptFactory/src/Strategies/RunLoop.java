package Strategies;


import Actions.Action;
import Actions.ActionHandler;
import Actions.Logic.Endif;
import Actions.Logic.If;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by SRH on 1/15/2018.
 */
public class RunLoop implements Strategy {
    private ArrayList<Action> actions;
    private ActionHandler actionHandler;
    private Stack ifStack;

    public RunLoop(ArrayList<Action> actions)
    {
        this.actions = actions;
        actionHandler = new ActionHandler();
        ifStack = new Stack();
        ifStack.push("True");
    }

    @Override
    public boolean activate() {
        return true;
    }

    @Override
    public void execute() {

        Time.sleep(1200);

        for (Action line : actions)
        {
            if (ifStack.peek().equals("True"))
            {
                if (line instanceof If)
                {
                    ifStack.push(actionHandler.determineIf(line));
                }
                else if (line instanceof Endif)
                {
                    ifStack.pop();
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
                        default:
                            System.out.println("Error: Unimplemented action: " + line.getAction());
                    }
                }
            }
        }
    }
}
