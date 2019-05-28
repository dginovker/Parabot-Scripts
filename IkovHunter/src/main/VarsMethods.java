package main;

import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Area;
import org.rev317.min.api.wrappers.Tile;

import java.util.ArrayList;
import java.util.Random;

public class VarsMethods {
    public String currentHunt = "None!";
    public boolean powerMode = false;
    public String[] huntingActivities = {"Hunt birds", "Red chins", "Black chins"};

    public int animalsCaught = 0;
    public int trapsOnGround = 0;

    public ArrayList<Tile> myTraps = new ArrayList<>();
    
    public Area edgevilleArea = new Area();
    public Area birdArea = new Area(new Tile(2600, 2887), new Tile(2609, 2899));

    public final int birdSnare = 10007;
    public final int boxTrap = 10009;

    public boolean playerInHuntingLoc()
    {
        return false;
    }

    public Tile getRandomTile(Area area) {
        return area.getTiles()[new Random().nextInt(area.getTiles().length)];
    }

    public boolean layTrap(Area toLay, int trapId)
    {
        Walking.walkTo(Players.getMyPlayer().getLocation(), getRandomTile(birdArea));
        Time.sleep(() -> Players.getMyPlayer().getAnimation() == -1, 5000);
        if (!birdArea.contains(Players.getMyPlayer().getLocation()))
            return false; //Do it again

        int countPreTrap = Inventory.getCount();
        Inventory.getItem(birdSnare).interact(Items.Option.FIRST);
        if (countPreTrap > Inventory.getCount())
        {
            trapsOnGround ++;
            return true;
        }

        return false;
    }

    public boolean canLayAnotherTrap() {
        if (trapsOnGround < 2)
        {
            return true;
        }
        return false;
    }

    public void collectReadyTraps() {
        for (Tile t : myTraps)
        {
            SceneObjects.getSceneObjectsAtTile(t.getX(), t.getY(), false);
        }
    }
}
