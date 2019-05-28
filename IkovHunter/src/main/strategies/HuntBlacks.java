package main.strategies;

import main.VarsMethods;
import org.parabot.environment.scripts.framework.Strategy;

public class HuntBlacks implements Strategy {
    private VarsMethods vars;

    public HuntBlacks(VarsMethods vars) {
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
