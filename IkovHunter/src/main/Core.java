package main;

import main.gui.GUI;
import main.strategies.*;
import org.parabot.core.Context;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;

import java.awt.*;
import java.util.ArrayList;

@ScriptManifest(author = "Before", name = "Before's Hunter Script", category = Category.HUNTER, version = 1.0, description = "Hunts birds, red and black chinchompas", servers = "Ikov")
public class Core extends Script implements Paintable, MessageListener {

    private VarsMethods vars = new VarsMethods();
    private ArrayList<Strategy> strategies = new ArrayList<>();

    private GUI gui;

    @Override
    public boolean onExecute()
    {

        gui = new GUI(vars);

        while (gui.isShowing())
        {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!gui.scriptStarted)
            Context.getInstance().getRunningScript().setState(STATE_STOPPED);

        if (vars.powerMode) {
            strategies.add(new PowerStrategy(vars));
        } else {
            strategies.add(new BankStrategy(vars));
        }

        switch (vars.currentHunt)
        {
            case "Hunt birds":
                strategies.add(new HuntBirds(vars));
                break;
            case "Red chins":
                strategies.add(new HuntReds(vars));
                break;
            case "Black chins":
                strategies.add(new HuntBlacks(vars));
                break;
            default:
                System.out.println("You should not see this, ever");
        }
        provide(strategies);

        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(560, 310, 170, 90);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Cordia New", Font.PLAIN, 16));
        g.drawString("Before's Hunter", 580, 330);
        g.setFont(new Font("Cordia New", Font.PLAIN, 12));
        g.drawString(vars.currentAction, 580, 347);
        g.drawString("Power mode? " +  vars.powerMode, 580, 360);
        g.drawString("Birds caught: " +  vars.animalsCaught, 580, 373);
        g.drawString("Traps on ground: " + vars.trapsOnGround, 580, 386);
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        if (messageEvent.getType() == MessageEvent.TYPE_GENERIC && messageEvent.getMessage().contains("One of your traps has collapsed."))
        {
            vars.trapsOnGround --;
        }
    }
}
