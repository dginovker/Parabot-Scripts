import Actions.Action;
import GUI.GUI;
import Strategies.RunLoop;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;

/**
 * Created by SRH on 1/9/2018.
 * Welcome to AIO AIO - ScriptFactory. Make your own scripts!
 */


@ScriptManifest(author = "Before", name = "Script Factory 1.0", category = Category.OTHER, version = 1.1, description = "Create your own scripts.", servers = "Any")
public class Core extends Script {

    private ArrayList<Action> actions = new ArrayList<>();
    ArrayList<Strategy> strategies = new ArrayList<>();

    private Paint painter;
    private GUI gui;

    @Override
    public boolean onExecute() {
        painter = new Paint();

        gui = new GUI(actions);

        gui.setVisible(true);

        while (gui.isShowing())
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        strategies.add(new RunLoop(actions));
        provide(strategies);

        return true;
    }

    @Override
    public int getState() {
        return super.getState();
    }
}
