package org.parabot.emmastone.woodcutter.utils;

import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.wrappers.Item;

import java.text.DecimalFormat;

/**
 * @author EmmaStone
 */
public class Methods {


    public String formatNumber(final double start) {
        final DecimalFormat nf = new DecimalFormat("#.00");
        if (start >= 1000000000) {
            return nf.format(start / 1000000000) + "B";
        } else if (start >= 1000000) {
            return nf.format(start / 1000000) + "M";
        } else if (start >= 1000) {
            return nf.format(start / 1000) + "K";
        }

        return String.valueOf((int) start);
    }

}
