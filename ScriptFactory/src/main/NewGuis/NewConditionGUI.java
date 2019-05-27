package main.NewGuis;


import main.Actions.Action;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/9/2018.
 */
public class NewConditionGUI extends NewStatementGUI {
    public NewConditionGUI(ArrayList<Action> actionList, Consumer<Integer> updateTextfield) {
        String[] actionTypes = new String[]{"Item is in Inventory", "Entity is around", "Hitpoints is below"};
        Descriptions[] setDescs = {
                new Descriptions("Item to detect (eg. 4296 = bones)", "Number of them to detect (blank = 1)"),
                new Descriptions("Entity to detect (eg. 4296 = banker)"),
                new Descriptions("Below health # (eg. 10)")
        };

        initGui("Add new condition", actionList, updateTextfield, actionTypes, setDescs);
    }

}
