package main;

import org.parabot.core.ui.Logger;

public class VarsMethods {
    public String currentAction = "";

    public static void log(String str)
    {
        Logger.addMessage(str, false);
        System.out.println(str);
    }
}
