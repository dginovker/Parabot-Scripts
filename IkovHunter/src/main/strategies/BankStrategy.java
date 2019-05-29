package main.strategies;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;

import static org.rev317.min.api.methods.Walking.walkTo;

public class BankStrategy implements Strategy {
    private VarsMethods vars;

    public BankStrategy(VarsMethods vars) {
        this.vars = vars;
    }

    @Override
    public boolean activate() {
        return !vars.powerMode && Inventory.getCount() + vars.trapsOnGround > 25;
    }

    @Override
    public void execute() {
        vars.currentAction = "Banking";

        vars.collectReadyTraps();
        walkTo(Players.getMyPlayer().getLocation(), Players.getMyPlayer().getLocation()); //In case we're at level up screen
        Keyboard.getInstance().sendKeys("::home");
        Time.sleep(() -> vars.edgevilleArea.contains(Players.getMyPlayer().getLocation()), 5000);
        Time.sleep(600);
        Bank.getBank().interact(SceneObjects.Option.FIRST);
        Time.sleep(Bank::isOpen, 10000);
        Bank.depositAllExcept(vars.birdSnare, vars.boxTrap);
        Bank.close();
    }

}
