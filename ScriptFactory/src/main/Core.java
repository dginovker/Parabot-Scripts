package main;

import main.Actions.Action;
import main.GUI.GUI;
import main.Strategies.RunLoop;
import org.parabot.core.Context;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.accessors.Client;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static main.VarsMethods.FSEP;
import static main.VarsMethods.log;

/**
 * Created by SRH on 1/9/2018.
 * Welcome to AIO AIO - ScriptFactory. Make your own scripts!
 */

@ScriptManifest(author = "Before", name = "Script Factory 1.5", category = Category.OTHER, version = 1.5, description = "Create your own scripts!", servers = "All")
public class Core extends Script implements Paintable {

    private ArrayList<Action> actions = new ArrayList<>();
    private ArrayList<Strategy> strategies = new ArrayList<>();

    private GUI gui;

    @Override
    public boolean onExecute() {
        File directory = new File(VarsMethods.DEFAULT_DIR + FSEP + "dependencies");
        if (!directory.exists())
            directory.mkdirs();

        gui = new GUI(actions);

        while (gui.isShowing())
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!gui.scriptStarted)
        {
            gui.killAllGuis();
            VarsMethods.savescript(actions, new File(VarsMethods.CACHED_LOC));
            return false;
        }

        strategies.add(new RunLoop(actions));
        provide(strategies);

        return true;
    }

    @Override
    public void paint(Graphics g) {
        try { g.setColor(Color.BLUE);
        g.fillRect(560, 310, 170, 70);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Cordia New", Font.PLAIN, 16));
        g.drawString("Script Factory", 580, 330);
        g.setFont(new Font("Cordia New", Font.PLAIN, 12));
        g.drawString("Currently executing: ", 580, 347);
        g.drawString(VarsMethods.currentAction, 580, 360);
        g.drawString(VarsMethods.currentSubscript.equals("") ? "" : "Subscript " + VarsMethods.currentSubscript, 580, 373);}
        catch (Exception e)
        {
            log("Found it");
        }
    }
}
