package main.java.org.parabot.emmastone.woodcutter;

import main.java.org.parabot.emmastone.woodcutter.data.Settings;
import main.java.org.parabot.emmastone.woodcutter.strategies.Fight;
import main.java.org.parabot.emmastone.woodcutter.ui.UI;
import main.java.org.parabot.emmastone.woodcutter.utils.PaintUtils;
import org.parabot.emmastone.woodcutter.utils.Methods;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author EmmaStone
 */
@ScriptManifest(
        author = "Before",
        name = "Simple Autofighter",
        category = Category.COMBAT,
        version = 1.01,
        description = "Simple Fighting Script",
        servers = { "IKOV" })
public class Core extends Script implements ICore, Paintable {
    private final Settings settings;
    private final Methods    methods;
    private final PaintUtils paintUtils;
    private final UI ui;

    public Core() {
        this.settings = new Settings();
        this.settings.setStrategies(new ArrayList<Strategy>());
        this.methods = new Methods();
        this.paintUtils = new PaintUtils(this);
        this.ui = new UI(this);
    }

    @Override
    public boolean onExecute() {
        while (this.ui.isVisible()) {
            sleep(200);
        }

        this.settings.getStrategies().addAll(
                Arrays.asList(
                        new Fight(this)));

        provide(this.settings.getStrategies());
        return true;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Methods getMethods() {
        return methods;
    }

    @Override
    public void paint(Graphics graphics) {
        if (this.ui.isVisible()
                || this.settings.getScriptTimer() == null) {
            return;
        }

        this.paintUtils.paint((Graphics2D) graphics);
    }
}
