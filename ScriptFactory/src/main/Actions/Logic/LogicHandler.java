package main.Actions.Logic;

import main.Actions.Action;
import main.VarsMethods;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;

import java.util.ArrayList;
import java.util.Arrays;

import static main.VarsMethods.log;
import static main.VarsMethods.toPintArray;

public class LogicHandler {
    public String determineIf(Action a) {
        switch (a.getAction().replaceAll("-", " "))
        {
            case "Item is in Inventory":
                return Inventory.getCount(a.getParam(0)) >= (a.getParamAsString(1).equals("") ? 0 : a.getParam(1)) ? "True" : "False";
            case "Entity is around":
                ArrayList<Integer> ids = new ArrayList<>();
                for (int i = 0; i < a.getParamCount() - 1; i++)
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

    public String determineInverseIf(Action a) {
        return determineIf(a).equals("True") ? "False" : "True";
    }
}
