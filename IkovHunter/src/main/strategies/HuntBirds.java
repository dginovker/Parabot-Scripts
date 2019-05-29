package main.strategies;

import main.VarsMethods;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Walking;
import org.rev317.min.api.wrappers.Tile;

import static org.rev317.min.api.methods.Menu.sendAction;

public class HuntBirds implements Strategy {
    private VarsMethods vars;

    private Tile[] tilePath = {new Tile(2575, 2908), new Tile(2596, 2909), new Tile(0, 0)};

    public HuntBirds(VarsMethods vars) {
        this.vars = vars;
    }

    @Override
    public boolean activate() {
        return Inventory.getCount() + vars.trapsOnGround <= 25 && vars.currentHunt.equals("Hunt birds");
    }

    @Override
    public void execute() {
        vars.currentAction = "Hunting birds";

        if (vars.birdArea.contains(Players.getMyPlayer().getLocation()))
        {
            vars.collectReadyTraps();

            if (vars.canLayAnotherTrap())
            {
                vars.layTrap(vars.birdArea, vars.birdSnare);
            }
            return;
        }

        System.out.println(Players.getMyPlayer().getLocation() + " is not in the bird area");
        getToBirdArea();
    }

    private void getToBirdArea() {
        Tile aBirdTile = vars.getRandomAverageTile(vars.birdArea);
        if (aBirdTile.distanceTo() < 15)
        {
            Walking.walkTo(aBirdTile);
            Time.sleep(5000);
            return;
        }

        if (!vars.jungleTile.equals(Players.getMyPlayer().getLocation()))
        {
            vars.currentAction = "Teleporting to jungle";
            teleportToJungleArea();
        }
        else
        {
            vars.currentAction = "Walking to the birds";
            tilePath[2] = vars.getRandomAverageTile(vars.birdArea);
            for (int i = 0; i < tilePath.length; i++) {
                System.out.println("Walking to tile " + i);
                Walking.walkTo(tilePath[i]);
                Time.sleep(2000);
                final Tile tile = tilePath[i].getLocation();
                Time.sleep(() -> Players.getMyPlayer().getLocation().equals(tile), 8000);
            }
        }
    }

    private void teleportToJungleArea() {
        sendAction(1603, 6, 0, 0, 0);
        Time.sleep(1299);
        sendAction(315, 0, 0, 1541, 0);
        Time.sleep(1299);
        Keyboard.getInstance().sendKeys("55532");
        Time.sleep(5000);
    }
}
