package GUI.NewGuis;

import Actions.Action;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/9/2018.
 */
public class NewActionGUI extends NewStatementGUI {
    public NewActionGUI(ArrayList<Action> actionList, Consumer<Integer> updateTextfield) {
        String[] actionTypes = new String[]{"Interact with", "Use item on", "Type", "Click (x,y)"};
        Descriptions[] setDescs = {
                new Descriptions("Entity to interact with (eg. 4296 = banker)", "Option to select (eg. \"Bank\")(cAsE sEnSiTivE)"),
                new Descriptions("Item in your inventory to use (eg. 999 = \"Bones\")", "Entity to use it on (eg. 999 = altar)<999 isn't actually altar>", "Item option to select (can be left blank)(eg. 1 = \"Bury\")"),
                new Descriptions("Text to type in (eg. 28)", "Hit enter? (0 for no, 1 for yes)"),
                new Descriptions("X coordinate to click (eg. 0)", "Y coordinate to click (eg. 600)", "Right click? (0 for no, 1 for yes)")
        };

        initGui("Add new action", actionList, updateTextfield, actionTypes, setDescs);
    }
}
