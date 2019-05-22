package Strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.wrappers.Npc;

public class Fish implements Strategy {
    @Override
    public boolean activate() {
        Npc fishingPool = Npcs.getClosest(316, 1333);
        return fishingPool != null && Inventory.getCount() != 28;

    }

    @Override
    public void execute() {

        Npc fishingPool = Npcs.getClosest(316, 1333);
        fishingPool.interact(0);

        Time.sleep(() -> Inventory.getCount() == 28, 25000);


        Time.sleep(1200);
    }
}
