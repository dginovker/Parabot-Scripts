package GUI.NewGuis;

import Actions.Action;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/9/2018.
 */
public class NewConditionGUI extends NewStatementGUI {
    public NewConditionGUI(ArrayList<Action> actionList, Consumer<Integer> updateTextfield) {
        String[] actionTypes = new String[]{"If item is in Inventory", "If Entity is around"};
        Descriptions[] setDescs = {
                new Descriptions("Item to detect (eg. 4296 = bones)"),
                new Descriptions("Entity to detect (eg. 4296 = banker)"),
        };

        initGui("Add new condition", actionList, updateTextfield, actionTypes, setDescs);
    }

}
