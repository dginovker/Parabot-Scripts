package main.java.org.parabot.emmastone.woodcutter.strategies;

import main.java.org.parabot.emmastone.woodcutter.ICore;
import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Npc;

/**
 * @author EmmaStone, Before
 */
public class Fight implements org.parabot.environment.scripts.framework.Strategy {
    private final ICore core;

    public Fight(ICore core) {
        this.core = core;
    }

    @Override
    public boolean activate() {
        return Npcs.getClosest(core.getSettings().attackNpc) != null;
    }

    @Override
    public void execute() {
        if (!Players.getMyPlayer().isInCombat())
        {
            Npcs.getClosest(o -> !o.isInCombat() && o.getDef().getId() == core.getSettings().attackNpc).interact(0);
        }

        Time.sleep(1200);
    }
}
