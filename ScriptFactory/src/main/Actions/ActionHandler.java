package main.Actions;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.input.Mouse;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;

public class ActionHandler {

    private void interactWithEntity(String id, String option)
    {
        int entityId = Integer.parseInt(id);
        int optionInt = Integer.parseInt(option);

        SceneObject candidateObject = SceneObjects.getClosest(entityId);
        Npc candidateNpc = Npcs.getClosest(entityId);

        if (candidateObject != null)
        {
            candidateObject.interact(optionInt);
        } else {
            candidateNpc.interact(optionInt);
        }
    }

    public void handleInteractWith(Action a)
    {
        interactWithEntity(a.getParam0(), a.getParam1());
    }

    public void useItemOn(Action a)
    {
        int itemId = Integer.parseInt(a.getParam0());

        Item toUse = Inventory.getItem(itemId);
        Menu.interact(toUse, a.getParam2());

        interactWithEntity(a.getParam1(), "1");
    }

    public void type(Action a)
    {
        Keyboard.getInstance().sendKeys(a.getParam0(), a.getParam1().equals("1"));
    }

    public void clickxy(Action a)
    {
        Mouse.getInstance().click(Integer.parseInt(a.getParam0()), Integer.parseInt(a.getParam1()), a.getParam2().equals("0"));
    }

    public void sleep(Action a) {
        Time.sleep(Integer.parseInt(a.getParam0()));
    }

    public String determineIf(Action a) {
        switch (a.getAction())
        {
            case "If item is in Inventory":
                return Inventory.getCount(Integer.parseInt(a.getParam0())) > 0 ? "True" : "False";
            case "If Entity is around":
                return Npcs.getClosest(Integer.parseInt(a.getParam0())) != null ||
                        SceneObjects.getClosest(Integer.parseInt(a.getParam0())) != null ? "True" : "False";
            default:
                VarsMethods.log("Error: Unimplemented conditional: " + a.getAction());
        }
        return "False";
    }
}
