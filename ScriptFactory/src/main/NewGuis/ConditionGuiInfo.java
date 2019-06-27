package main.NewGuis;


import main.Actions.Action;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/9/2018.
 */
public class ConditionGuiInfo extends NewStatementGUI {
    public ConditionGuiInfo(ArrayList<Action> actionList, Consumer<Integer> updateTextfield) {
        String[] actionTypes = new String[]{"Item is in Inventory", "Inventory slots used", "Item is on Ground", "Entity is around", "Hitpoints is below", "In Combat"};
        Descriptions[] setDescs = {
                new Descriptions("Item to detect (eg. 4296 = bones)", "Number of them to detect (blank = 1)"),
                new Descriptions("Inventory slots greater than or equal to (eg. \"28\" detects a full inventory)"),
                new Descriptions("Item to detect (eg. 314 = feathers)"),
                new Descriptions("Entity to detect (eg. \"81,397,1767,1768\" detects cows)"),
                new Descriptions("Below health # (eg. 10)"),
                new Descriptions(),
        };

        initGui("Add new condition", actionList, updateTextfield, actionTypes, setDescs);
    }

}
