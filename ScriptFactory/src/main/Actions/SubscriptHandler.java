package main.Actions;

import main.Strategies.ActionExecutor;
import main.VarsMethods;

import java.io.File;
import java.util.ArrayList;

import static main.VarsMethods.*;

public class SubscriptHandler {
    public static void runSubscript(String path)
    {
        ArrayList<Action> actions = new ArrayList<>();
        File subscriptFile = new File(DEFAULT_DIR + FSEP + path);
        if (subscriptFile.exists())
            loadscript(actions, subscriptFile);
        else
            loadscript(actions, new File(DEFAULT_DIR + FSEP + "dependencies" + FSEP + path));
        ActionExecutor executor = new ActionExecutor(actions);

        VarsMethods.currentSubscript = path;

        for (int i = 0; i < actions.size(); i++) {
            executor.execute();
        }

        VarsMethods.currentSubscript = "";
    }
}
