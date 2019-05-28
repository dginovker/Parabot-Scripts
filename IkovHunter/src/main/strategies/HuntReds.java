package main.strategies;

import main.VarsMethods;
import org.parabot.environment.scripts.framework.Strategy;

public class HuntReds implements Strategy {
    private VarsMethods vars;

    public HuntReds(VarsMethods vars) {

        this.vars = vars;
    }

    @Override
    public boolean activate() {
        return false;
    }

    @Override
    public void execute() {

    }
}
