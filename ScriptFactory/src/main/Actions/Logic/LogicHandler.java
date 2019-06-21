package main.Actions.Logic;

import main.Actions.Action;
import org.rev317.min.api.methods.*;

import java.util.ArrayList;
import java.util.Arrays;

import static main.VarsMethods.log;
import static main.VarsMethods.toPintArray;

/**
 * Todo:
 * Change this to a boolean ffs and make another function handle True : False lol
 */
public class LogicHandler {
    public String determineIf(Action a) {
        switch (a.getMethod().replaceAll("-", " "))
        {
            case "Item is in Inventory":
                return Inventory.getCount(a.getParam(0)) >= (a.getParamAsString(1).equals("") ? 0 : a.getParam(1)) ? "True" : "False";
            case "Item is on Ground":
                return GroundItems.getGroundItems(o-> o.getId() == a.getParam(0)).length > 0 ? "True" : "False";
            case "Entity is around":
                ArrayList<Integer> ids = new ArrayList<>();
                for (int i = 0; i < a.getParamCount(); i++)
                    ids.add(a.getParam(i));
                return Npcs.getClosest(toPintArray(ids)) != null ||
                        SceneObjects.getClosest(toPintArray(ids)) != null ? "True" : "False";
            case "Hitpoints is below":
                return Players.getMyPlayer().getHealth() < a.getParam(0) ? "True" : "False";
            case "In Combat":
                return Players.getMyPlayer().isInCombat() ? "True" : "False";
            default:
                log("Error: Unimplemented conditional: " + a.getAction());
        }
        return "False";
    }

    public String determineIfNot(Action a) {
        return determineIf(a).equals("True") ? "False" : "True";
    }
}
