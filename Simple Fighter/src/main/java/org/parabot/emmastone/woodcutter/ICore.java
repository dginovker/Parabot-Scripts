package main.java.org.parabot.emmastone.woodcutter;

import main.java.org.parabot.emmastone.woodcutter.data.Settings;
import org.parabot.emmastone.woodcutter.utils.Methods;

/**
 * @author EmmaStone
 */
public interface ICore {

    Settings getSettings();

    Methods getMethods();
}
