package main.java.org.parabot.emmastone.woodcutter.utils;

import org.parabot.environment.api.utils.Timer;
import org.rev317.min.api.methods.Skill;

/**
 * A wrapper class used to time a skill.
 *
 * @author Empathy
 */
public class ScriptTimer extends Timer {

    /**
     * The skill to time.
     */
    private final Skill skill;
    /**
     * The start exp.
     */
    private final int   startExp;
    /**
     * The start level.
     */
    private final int   startLevel;

    /**
     * Constructs new script timer.
     *
     * @param skill to track
     */
    public ScriptTimer(final Skill skill) {
        startLevel = skill.getLevel();
        this.skill = skill;
        startExp = skill.getExperience();
    }

    public Skill getSkill() {
        return skill;
    }

    /**
     * @return the startExp
     */
    private double getStartExp() {
        return startExp;
    }

    /**
     * Gets the exp gained over time.
     *
     * @return the exp gained.
     */
    public int getXpGained() {
        return (int) (skill.getExperience() - getStartExp());
    }

    /**
     * @return levels gained
     */
    public int levelsGained() {
        return skill.getLevel() - startLevel;
    }
}
