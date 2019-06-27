package main.NewGuis;

import main.Actions.Action;

import java.util.ArrayList;
import java.util.function.Consumer;

public class UncommonActionGuiInfo extends NewStatementGUI {
    public UncommonActionGuiInfo(ArrayList<Action> actionList, Consumer<Integer> updateTextField)
    {
        String[] actionTypes = new String[]{"Comment", "Run subscript", "Bank all except IDs"};
        NewStatementGUI.Descriptions[] setDescs = {
                new Descriptions("Enter any text to be your comment."),
                new Descriptions("Enter the file name of the subscript (cAsE sEnSiTiVe)"),
                new Descriptions("Enter the IDs to keep (comma separated) (i.e. \"995,150,356\") (can be blank)"),
        };
        initGui("Add new uncommon action", actionList, updateTextField, actionTypes, setDescs);
    }
}
