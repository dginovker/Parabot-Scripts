package slayer.strategies.supportedtasks;


import org.parabot.environment.api.utils.Time;
import slayer.ICore;

public class Kalphite extends Tasks {

    public Kalphite(String taskName, ICore core) {
        super(taskName, core);
    }

    @Override
    public void execute() {
        if (isInArea())
        {
            attackTaskNPC();
        }
        else
        {
            //Get there LOL
        }

        Time.sleep(1500);
    }
}
