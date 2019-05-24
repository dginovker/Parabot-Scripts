package main.Strategies;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.SceneObject;

public class Bank implements Strategy {
    @Override
    public boolean activate() {
        SceneObject bank = SceneObjects.getClosest(2213);
        Npc banker = org.rev317.min.api.methods.Bank.getBanker();
        return Inventory.getCount() == 28 && (bank != null || banker != null);
    }

    @Override
    public void execute() {
        SceneObject bank = SceneObjects.getClosest(2213);
        if (bank == null)
        {
            org.rev317.min.api.methods.Bank.getBanker().interact(0);
        }
        else
        {
            bank.interact(0);
        }
        Time.sleep(1200);
        org.rev317.min.api.methods.Bank.depositAllExcept(304, 306, 302, 312);
        Time.sleep(1200);
        org.rev317.min.api.methods.Bank.close();

        Time.sleep(1200);
    }
}

