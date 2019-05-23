package main.java.org.parabot.emmastone.woodcutter.utils;

import main.java.org.parabot.emmastone.woodcutter.ICore;

import java.awt.*;

/**
 * @author EmmaStone, Before
 */
public class PaintUtils {
    private final ICore core;

    public PaintUtils(ICore core) {
        this.core = core;
    }

    public void paint(Graphics2D g2) {
        g2.setColor(Color.green);
        g2.drawString("Simple Autofighter", 10, 35);
        g2.drawString("Runtime: " + this.core.getSettings().getScriptTimer(), 10, 65);
        g2.drawString("XP Gained (/h): "
                + this.core.getMethods().formatNumber(this.core.getSettings().getScriptTimer().getXpGained())
                + " ("
                + this.core.getMethods().formatNumber(
                this.core.getSettings().getScriptTimer().getPerHour(
                        this.core.getSettings().getScriptTimer().getXpGained()))
                + ")", 10, 80);
        g2.drawString("Current level: "
                + this.core.getSettings().getScriptTimer().getSkill().getLevel()
                + " (+"
                + this.core.getSettings().getScriptTimer().levelsGained() + ")", 10, 95);
    }
}
