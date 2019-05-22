package slayer.strategies.supportedtasks;

import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Area;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.Tile;
import slayer.ICore;

abstract class Tasks implements Strategy
{
    String taskName;
    Area monsterArea;
    int monsterId;

    private final ICore core;


    @Override
    public boolean activate() {
        return core.getMethods().latestTaskMessage.contains(taskName);
    }

    public Tasks(String taskName, ICore core)
    {
        this.taskName = taskName.toLowerCase();
        this.core = core;

        switch (taskName)
        {
            case "kalphite":
                monsterArea = new Area();
                monsterId = -1;
            default:
                monsterArea = null;
        }
    }

    public boolean isInArea()
    {
        return monsterArea.contains(Players.getMyPlayer().getLocation());
    }

    public void attackTaskNPC()
    {
        Npcs.getClosest(monsterId).interact(0);
    }
}
