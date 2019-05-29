package main;

import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Area;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import static org.rev317.min.api.methods.Menu.sendAction;

public class VarsMethods {
    public String currentAction = "Nothing!";

    public String currentHunt = "None!";
    public boolean powerMode = false;
    public String[] huntingActivities = {"Hunt birds", "Red chins", "Black chins"};

    public int animalsCaught = 0;
    public int trapsOnGround = 0;

    public ArrayList<Tile> myTraps = new ArrayList<>();
    
    public Area edgevilleArea = new Area(new Tile(3086, 3504), new Tile(3099, 3487));
    public Area birdArea = new Area(new Tile(2603, 2887), new Tile(2609, 2894), new Tile(2603, 2894), new Tile(2609, 2887));

    public Tile jungleTile = new Tile(2557, 2912);

    public final int birdSnare = 10007;
    public final int boxTrap = 10009;
    public final int usedBirdSnare = 19180;
    public final int usedBoxTrap = 0;

    public boolean playerInHuntingLoc()
    {
        return false;
    }

    public Tile getRandomAverageTile(Area area) {
        /*System.out.println("Area tiles: ");
        for (Tile t : area.getTiles())
        {
            System.out.println(t);
        }*/
        return area.getTiles()[new Random().nextInt(area.getTiles().length)];
    }

    public boolean layTrap(Area toLay, int trapId)
    {
        Tile myTile = getRandomAverageTile(toLay);
        Walking.walkTo(Players.getMyPlayer().getLocation(), myTile);
        Time.sleep(1200);
        Time.sleep(() -> Players.getMyPlayer().getLocation().equals(myTile), 5000);
        Time.sleep(600);
        if (!toLay.contains(Players.getMyPlayer().getLocation()))
        {
            System.out.println("Not in area.");
            return false; //Do it again
        }

        int countPreTrap = Inventory.getCount();
        sendAction(74, trapId - 1, Inventory.getItem(trapId).getSlot(), 3214);
        System.out.println("Did I put down a snare?");
        Time.sleep(() -> countPreTrap > Inventory.getCount(), 3000);
        if (countPreTrap > Inventory.getCount())
        {
            System.out.println("Yup");
            myTraps.add(myTile);
            trapsOnGround ++;
            return true;
        }

        System.out.println("Nope");
        return false;
    }

    public boolean canLayAnotherTrap() {
        return trapsOnGround < Skill.HUNTER.getRealLevel()/20 + 1;
    }

    public void collectReadyTraps() {
        try {
            for (Tile t : myTraps)
            {
                SceneObject[] traps = SceneObjects.getSceneObjects(o -> o.getLocation().equals(t) && (o.getId() == usedBirdSnare || o.getId() == usedBoxTrap));
                if (traps.length == 0)
                {
                    continue;
                }

                System.out.println("interacting with the trap");
                currentAction = "Picking up trap";
                traps[0].interact(SceneObjects.Option.FIRST);

                int invCount = Inventory.getCount();
                Time.sleep(1000);
                Time.sleep(() -> Inventory.getCount() > invCount, 8000);
                if (Inventory.getCount() > invCount)
                {
                    myTraps.remove(t);
                    trapsOnGround --;
                }
            }
        } catch (ConcurrentModificationException ignore) {}
    }
}
