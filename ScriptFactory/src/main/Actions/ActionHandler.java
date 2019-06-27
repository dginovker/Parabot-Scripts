package main.Actions;

import main.VarsMethods;
import org.parabot.core.Context;
import org.parabot.environment.api.utils.Filter;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.input.Mouse;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.*;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

import static main.VarsMethods.log;
import static main.VarsMethods.parsePint;

public class ActionHandler {

    private static String debugString = "";

    public void handleInteractWith(Action a)
    {
        int[] ids = new int[a.getParamCount() -1];
        for (int i = 0; i < a.getParamCount() - 1; i++) {
            ids[i] = a.getParam(i);
        }
        interactWithEntity(ids, a.getParamAsString(a.getParamCount() - 1));
    }

    public void handleInteractWithByLoc(Action a) {
        interactWithEntityByTile(new Tile(a.getParam(0), a.getParam(1)), a.getParamAsString(2));
    }

    public void inventoryItemInteract(Action a)
    {
        try {
            Item tea = Inventory.getItem(a.getParam(0));
            tea.interact(VarsMethods.getItemOption(a.getParamAsString(1)));
        } catch (NullPointerException e) {
            log("Warning: Couldn't find inventory item with id " + a.getParam(0));
            if (Inventory.getCount(a.getParam(0) + 1) > 0)
            {
                log("But, you seem to have " + Inventory.getCount(a.getParam(0) + 1) + " items with id " + (a.getParam(0) + 1));
                log("Try the new ID out. It might work for what you want.");
            }
        }
    }

    public void useItemOn(Action a)
    {
        Item toUse = Inventory.getItem(parsePint(a.getParamAsString(0)));
        Menu.interact(toUse, VarsMethods.getItemOption(a.getParamAsString(1)));

        interactWithEntity(new int[]{a.getParam(1)}, "1");
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
        int totalSleep = 0;
        for (int i = 0; i < 10; i++)
        {
            VarsMethods.currentAction = "Sleep " + totalSleep + "/" + a.getParam(0);
            totalSleep += a.getParam(0)/10;
            Time.sleep(a.getParam(0)/10);
        }
        VarsMethods.currentAction = "Sleep " + totalSleep + "/" + a.getParam(0);
    }

    public void sendRawAction(Action a)
    {
        String[] actionIds = a.getParamAsString(1).replaceAll("[^0-9;]", "").split(";");
        if (actionIds.length == 4)
        {
            Menu.sendAction(a.getParam(0), parsePint(actionIds[0]), parsePint(actionIds[1]), parsePint(actionIds[2]), parsePint(actionIds[3]), 0);
        } else {
            Menu.sendAction(a.getParam(0), parsePint(actionIds[0]), parsePint(actionIds[1]), parsePint(actionIds[2]), 0);
        }
    }

    public void walkTo(Action a) {
        Walking.walkTo(new Tile(a.getParam(0), a.getParam(1)));
        Time.sleep(a.getParam(2));
    }

    public void handleGroundItemInteract(Action a) {
        try {
            GroundItem item = GroundItems.getNearest(o -> o.getId() == a.getParam(0))[0];
            if (item == null)
            {
                log("Could not find item with id" + a.getParam(0));
            } else {
                item.take();
            }
        } catch (ArrayIndexOutOfBoundsException e)
        {
            log("Warning: Grounditem not found in the following action:");
            log(a.toString());
        }
    }

    public void bankAllExcept(Action a) {
        if (!Bank.isOpen())
        {
            log("Warning: Bank isn't open!");
        }
        Bank.depositAllExcept(a.getParamArray());
    }

    private void interactWithEntity(int[] id, String option)
    {
        SceneObject candidateObject = SceneObjects.getClosest(id);
        Npc candidateNpc = Npcs.getClosest(id);
        debugString  = "id: " + Arrays.toString(id);
        tryToInteract(candidateObject, candidateNpc, option);
    }

    private void interactWithEntityByTile(Tile tile, String option) {
        SceneObject[] sos = SceneObjects.getNearest(o -> o.getLocation().equals(tile));
        SceneObject candidateObject = null;
        if (sos.length > 0)
            candidateObject = sos[0];
        Npc[] npca = Npcs.getNearest(o -> o.getLocation().equals(tile));
        Npc candidateNpc = null;
        if (npca.length > 0)
            candidateNpc = npca[0];

        debugString = "tile: " + tile;
        tryToInteract(candidateObject, candidateNpc, option);
    }

    private void tryToInteract(SceneObject candidateObject, Npc candidateNpc, String option)
    {
        if (candidateObject != null)
        {
            candidateObject.interact(VarsMethods.getSceneOption(option));
        } else {
            if (candidateNpc != null)
            {
                candidateNpc.interact(VarsMethods.getNpcOption(option));
            } else {
                log("Couldn't find entity on action " + VarsMethods.currentAction + ", " + debugString);
            }
        }
    }
}
