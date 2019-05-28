package main.strategies;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Player;

public class BankStrategy implements Strategy {
    private VarsMethods vars;

    public BankStrategy(VarsMethods vars) {
        this.vars = vars;
    }

    @Override
    public boolean activate() {
        return Inventory.getCount() + vars.trapsOnGround > 27 && vars.playerInHuntingLoc();
    }

    @Override
    public void execute() {
        pickUpAllTraps();
        Keyboard.getInstance().sendKeys("::bank");
        Time.sleep(() -> vars.edgevilleArea.contains(Players.getMyPlayer().getLocation()), 5000);
    }

    private void pickUpAllTraps() {
    }
}
