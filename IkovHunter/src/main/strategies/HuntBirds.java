package main.strategies;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Walking;
import org.rev317.min.api.wrappers.Player;

public class HuntBirds implements Strategy {
    private VarsMethods vars;

    public HuntBirds(VarsMethods vars) {
        this.vars = vars;
    }

    @Override
    public boolean activate() {
        return Inventory.getCount() + vars.trapsOnGround <= 27 && vars.currentHunt.equals("Hunt birds");
    }

    @Override
    public void execute() {
        vars.collectReadyTraps();

        if (vars.canLayAnotherTrap())
        {
            vars.layTrap(vars.birdArea, vars.birdSnare);
        }
    }
}
