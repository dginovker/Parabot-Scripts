package main.java.org.parabot.emmastone.woodcutter.data;

import main.java.org.parabot.emmastone.woodcutter.utils.ScriptTimer;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;

/**
 * @author EmmaStone, Before
 */
public class Settings {

    public int attackOption;
    public int attackNpc;

    private ArrayList<Strategy> strategies;
    private ScriptTimer scriptTimer;
    private boolean autoProgress;
    private boolean powerLevel;

    public ArrayList<Strategy> getStrategies() {
        return strategies;
    }


    public void setStrategies(ArrayList<Strategy> strategies) {
        this.strategies = strategies;
    }

    public ScriptTimer getScriptTimer() {
        return scriptTimer;
    }

}
