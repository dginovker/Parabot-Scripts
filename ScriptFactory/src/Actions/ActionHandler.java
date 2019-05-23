package Actions;

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
        interactWithEntity(a.getParam1(), a.getParam2());
    }

    public void useItemOn(Action a)
    {
        int itemId = Integer.parseInt(a.getParam1());

        Item toUse = Inventory.getItem(itemId);
        Menu.interact(toUse, a.getParam3());

        interactWithEntity(a.getParam2(), "1");
    }

    public void type(Action a)
    {
        Keyboard.getInstance().sendKeys(a.getParam1(), a.getParam2().equals("1"));
    }

    public void clickxy(Action a)
    {
        Mouse.getInstance().click(Integer.parseInt(a.getParam1()), Integer.parseInt(a.getParam2()), a.getParam3().equals("0"));
    }
}
