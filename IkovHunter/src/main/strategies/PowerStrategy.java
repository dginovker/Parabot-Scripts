package main.strategies;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.wrappers.Item;

public class PowerStrategy implements Strategy {
    private final VarsMethods vars;

    @Override
    public boolean activate() {
        return vars.powerMode && Inventory.getCount() + vars.trapsOnGround > 25;
    }

    @Override
    public void execute() {
        for (Item i : Inventory.getItems())
        {
            if (shouldDrop(i))
            {
                i.drop();
                Time.sleep(300);
            }
        }
    }

    private boolean shouldDrop(Item i) {
        switch (i.getId())
        {
            case 9979:
            case 527:
                return true;
        }

        return false;
    }

    public PowerStrategy(VarsMethods vars) {
        this.vars = vars;
    }

}
