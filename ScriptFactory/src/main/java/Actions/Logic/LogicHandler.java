package main.java.Actions.Logic;

import main.java.Actions.Action;
import org.rev317.min.api.methods.*;

import java.util.ArrayList;

import static main.java.VarsMethods.log;
import static main.java.VarsMethods.toPintArray;

public class LogicHandler {
    public boolean determineIf(Action a) {
        switch (a.getMethod().replaceAll("-", " "))
        {
            case "Item is in Inventory":
                return Inventory.getCount(a.getParam(0)) >= (a.getParamAsString(1).equals("") ? 0 : a.getParam(1));
            case "Inventory slots used":
                return Inventory.getCount() >= a.getParam(0);
            case "Item is on Ground":
                return GroundItems.getGroundItems(o-> o.getId() == a.getParam(0)).length > 0;
            case "Entity is around":
                ArrayList<Integer> ids = new ArrayList<>();
                for (int i = 0; i < a.getParamCount(); i++)
                    ids.add(a.getParam(i));
                return Npcs.getClosest(toPintArray(ids)) != null ||
                        SceneObjects.getClosest(toPintArray(ids)) != null;
            case "Hitpoints is below":
                return Players.getMyPlayer().getHealth() < a.getParam(0);
            case "In Combat":
                return Players.getMyPlayer().isInCombat();
            default:
                log("Error: Unimplemented conditional: " + a.getAction());
        }
        return false;
    }

    public String determineIfAsBoolString(Action a)
    {
        return determineIf(a) ? "True" : "False";
    }

    public String determineIfNotAsBoolString(Action a) {
        return determineIfAsBoolString(a).equals("True") ? "False" : "True";
    }
}
