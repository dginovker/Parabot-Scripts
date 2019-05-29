package main.Actions;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.input.Mouse;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Item;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;

import java.awt.event.KeyEvent;

import static main.VarsMethods.log;
import static main.VarsMethods.parsePint;

public class ActionHandler {

    private void interactWithEntity(String id, String option)
    {
        int entityId = parsePint(id);
        int optionInt = parsePint(option);

        SceneObject candidateObject = SceneObjects.getClosest(entityId);
        Npc candidateNpc = Npcs.getClosest(entityId);
        Npcs.getClosest(3328);

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
                log("Can't find the entity to interact with!");
            }
        }
    }

    public void handleInteractWith(Action a)
    {
        interactWithEntity(a.getParam0(), a.getParam1());
    }

    public void inventoryItemInteract(Action a)
    {
        Inventory.getItem(parsePint(a.getParam0())).interact(VarsMethods.getItemOption(a.getParam1()));
    }

    public void useItemOn(Action a)
    {
        Item toUse = Inventory.getItem(parsePint(a.getParam0()));
        Menu.interact(toUse, VarsMethods.getItemOption(a.getParam1()));

        interactWithEntity(a.getParam1(), "1");
    }

    public void type(Action a)
    {
        if (a.getParam0().toLowerCase().equals("{esc}"))
        {
            Keyboard.getInstance().clickKey(KeyEvent.VK_ESCAPE);
        }
        else
        {
            Keyboard.getInstance().sendKeys(a.getParam0(), a.getParam1().equals("1"));
        }
    }

    public void clickxy(Action a)
    {
        Mouse.getInstance().click(parsePint(a.getParam0()), parsePint(a.getParam1()), a.getParam2().equals("0"));
    }

    public void sleep(Action a) {
        Time.sleep(parsePint(a.getParam0()));
    }

    public void sendRawAction(Action a)
    {
        String[] actionIds = a.getParam1().replaceAll("[^0-9;]", "").split(";");
        Menu.sendAction(parsePint(a.getParam0()), parsePint(actionIds[0]), parsePint(actionIds[1]), parsePint(actionIds[2]), parsePint(actionIds[3]), 0);
    }
}
