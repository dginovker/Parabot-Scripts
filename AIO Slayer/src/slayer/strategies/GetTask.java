package slayer.strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Walking;
import org.rev317.min.api.wrappers.Tile;

public class GetTask implements Strategy {
    @Override
    public boolean activate() {
        return !hasNoTask();
    }

    private boolean hasNoTask() {
        return true;
    }

    @Override
    public void execute() {
        Keyboard.getInstance().sendKeys("::home");
        Time.sleep(1500);
        Walking.walkTo(Players.getMyPlayer().getLocation(), new Tile(3094, 3482));
        Time.sleep(6000);
        Npcs.getClosest(8464).interact(1);
    }
}
