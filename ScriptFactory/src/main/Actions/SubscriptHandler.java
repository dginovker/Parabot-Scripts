package main.Actions;

import main.Strategies.ActionExecutor;
import main.VarsMethods;

import java.io.File;
import java.util.ArrayList;

import static main.VarsMethods.log;

public class SubscriptHandler {
    public static void runSubscript(String path)
    {
        ArrayList<Action> actions = new ArrayList<>();
        VarsMethods.loadscript(actions, new File(VarsMethods.DEFAULT_DIR + System.getProperty("file.separator") + path));
        ActionExecutor executor = new ActionExecutor(actions);

        VarsMethods.currentSubscript = path;

        for (int i = 0; i < actions.size(); i++) {
            executor.execute();
        }

        VarsMethods.currentSubscript = "";
    }
}
