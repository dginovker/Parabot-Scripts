package main.Actions;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.input.Mouse;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

import java.awt.event.KeyEvent;

import static main.VarsMethods.log;
import static main.VarsMethods.parsePint;

public class ActionHandler {

    private boolean interactWithEntity(int id, String option)
    {
        log("interact with entity: id: " + id + "opt: " + option);
        SceneObject candidateObject = SceneObjects.getClosest(id);
        Npc candidateNpc = Npcs.getClosest(id);

        if (candidateObject != null)
        {
            candidateObject.interact(VarsMethods.getSceneOption(option));
        } else {
            if (candidateNpc != null)
            {
                candidateNpc.interact(VarsMethods.getNpcOption(option));
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    public void handleInteractWith(Action a)
    {
        for (int i = 0; i < a.getParamCount() - 1; i++) {
            if (interactWithEntity(a.getParam(i), a.getParamAsString(a.getParamCount()-1)))
                return;
        }
        log("Couldn't find an entity in: " + a);
    }

    public void inventoryItemInteract(Action a)
    {
        Inventory.getItem(a.getParam(0)).interact(VarsMethods.getItemOption(a.getParamAsString(1)));
    }

    public void useItemOn(Action a)
    {
        Item toUse = Inventory.getItem(parsePint(a.getParamAsString(0)));
        Menu.interact(toUse, VarsMethods.getItemOption(a.getParamAsString(1)));

        interactWithEntity(a.getParam(1), "1");
    }

    public void type(Action a)
    {
        if (a.getParamAsString(0).toLowerCase().equals("{esc}"))
        {
            Keyboard.getInstance().clickKey(KeyEvent.VK_ESCAPE);
        }
        else
        {
            Keyboard.getInstance().sendKeys(a.getParamAsString(0), a.getParamAsString(1).equals("1"));
        }
    }

    public void clickxy(Action a)
    {
        Mouse.getInstance().click(a.getParam(0), a.getParam(1), a.getParamAsString(2).equals("0"));
    }

    public void sleep(Action a) {
        Time.sleep(a.getParam(0));
    }

    public void sendRawAction(Action a)
    {
        String[] actionIds = a.getParamAsString(1).replaceAll("[^0-9;]", "").split(";");
        Menu.sendAction(a.getParam(0), parsePint(actionIds[0]), parsePint(actionIds[1]), parsePint(actionIds[2]), parsePint(actionIds[3]), 0);
    }

    public void walkTo(Action a) {
        Walking.walkTo(new Tile(Integer.valueOf(a.getParamAsString(0)), Integer.valueOf(a.getParamAsString(1))));
    }
}
