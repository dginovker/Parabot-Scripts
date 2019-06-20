package main.Actions.Logic;

import main.Actions.Action;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;

import static main.VarsMethods.log;

public class LogicHandler {
    public String determineIf(Action a) {
        switch (a.getAction().replaceAll("-", " "))
        {
            case "Item is in Inventory":
                return Inventory.getCount(a.getParam(0)) >= (a.getParamAsString(1).equals("") ? 0 : a.getParam(1)) ? "True" : "False";
            case "Entity is around":
                return Npcs.getClosest(a.getParam(0)) != null ||
                        SceneObjects.getClosest(a.getParam(0)) != null ? "True" : "False";
            case "Hitpoints is below":
                return Players.getMyPlayer().getHealth() < a.getParam(0) ? "True" : "False";
            case "In Combat":
                return Players.getMyPlayer().isInCombat() ? "True" : "False";
            default:
                log("Error: Unimplemented conditional: " + a.getAction());
        }
        return "False";
    }

    public String determineInverseIf(Action a) {
        return determineIf(a).equals("True") ? "False" : "True";
    }
}
