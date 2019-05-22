package slayer;

import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import slayer.strategies.GetTask;
import slayer.strategies.supportedtasks.Kalphite;
import slayer.utils.Methods;

import java.util.ArrayList;

@ScriptManifest(author = "Before", category = Category.SLAYER, description = "Does slayer. See command output for logs.", name = "AIO Slayer", servers = { "IKOV" }, version = 1)
public class Core extends Script implements ICore, MessageListener {

    private final ArrayList<Strategy> strategies = new ArrayList<>();
    private Methods methods = new Methods();

    @Override
    public boolean onExecute() {
        strategies.add(new GetTask());
        strategies.add(new Kalphite("kalphite", this));
        provide(strategies);

        return true;
    }

    @Override
    public void messageReceived(MessageEvent messageEvent) {
        if (messageEvent.getType() == MessageEvent.TYPE_GENERIC && messageEvent.getMessage().contains("Your next assignment is to kill"))
        {
            getMethods().latestTaskMessage = messageEvent.getMessage();
        }
        //System.out.println(messageEvent.getMessage() + ", sent by " + messageEvent.getSender() + " is of type " + messageEvent.getType());
    }

    @Override
    public Methods getMethods() {
        return this.methods;
    }
}
