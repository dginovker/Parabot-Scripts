package main;

import main.Actions.Action;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.Actions.Logic.IfNot;
import org.parabot.core.ui.Logger;
import org.parabot.environment.scripts.Script;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.methods.Items;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.SceneObjects;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class VarsMethods {
    public static int tickSpeed = 1200;
    public static String currentAction = "";
    public static String currentSubscript = "";

    public final static String DEFAULT_DIR = System.getProperty("user.home") + System.getProperty("file.separator") + "Parabot" + System.getProperty("file.separator") + "Script Factory";
    public final static String CACHED_LOC = DEFAULT_DIR + System.getProperty("file.separator") + "ScriptFactory cache.txt";
    public final static String FSEP = System.getProperty("file.separator");
    public final static int MAX_PARAMS = 3;

    public static void log(String str)
    {
        Logger.addMessage(str, false);
        System.out.println(str);
    }

    public static void loadscript(ArrayList<Action> actions, File selectedFile) {
        actions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                switch (line.split(" ")[0])
                {
                    case "If":
                        actions.add(new If(line));
                        break;
                    case "IfNot":
                        actions.add(new IfNot(line));
                        break;
                    case "Endif":
                        actions.add(new Endif(line));
                        break;
                    default:
                        actions.add(new Action(line));
                }
            }
            log("File loaded successfully");
        } catch (FileNotFoundException e) {
            log("Warning: Could not find file " + selectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savescript(ArrayList<Action> actions, File selectedFile) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(selectedFile);

            for (Action a : actions)
            {
                writer.println(a.toString());
            }
            writer.close();
        } catch (FileNotFoundException ignored) {
        }
    }

    public static JPanel centralizeComponent(Component component)
    {
        JPanel centralizer = new JPanel();
        centralizer.setLayout(new FlowLayout(FlowLayout.CENTER));
        centralizer.add(component);

        return centralizer;
    }

    public static int parsePint(String toParse)
    {
        return Integer.parseInt(toParse.replaceAll("[^0-9]", ""));
    }

    public static int[] toPintArray(ArrayList<Integer> ids) {
        int[] array = new int[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            array[i] = ids.get(i);
        }
        return array;
    }

    public static SceneObjects.Option getSceneOption(String option) {
        switch (option)
        {
            case "1":
                return SceneObjects.Option.FIRST;
            case "2":
                return SceneObjects.Option.SECOND;
            case "3":
                return SceneObjects.Option.THIRD;
            case "4":
                return SceneObjects.Option.FOURTH;
            case "5":
                return SceneObjects.Option.FIFTH;
            default:
                VarsMethods.log("Invalid Object option: " + option);
                return SceneObjects.Option.valueOf(option);
        }
    }

    public static Npcs.Option getNpcOption(String option) {
        switch (option)
        {
            case "1":
                return Npcs.Option.FIRST;
            case "2":
                return Npcs.Option.SECOND;
            case "3":
                return Npcs.Option.THIRD;
            case "4":
                return Npcs.Option.FOURTH;
            case "5":
                return Npcs.Option.FIFTH;
            default:
                VarsMethods.log("Invalid Object option: " + option);
                return Npcs.Option.valueOf(option);
        }
    }

    public static Items.Option getItemOption(String option) {
        switch (option)
        {
            case "1":
                return Items.Option.FIRST;
            case "2":
                return Items.Option.SECOND;
            case "3":
                return Items.Option.THIRD;
            case "4":
                return Items.Option.FOURTH;
            case "5":
                return Items.Option.FIFTH;
            default:
                VarsMethods.log("Invalid Object option: " + option);
                return Items.Option.valueOf(option);
        }
    }

    public static GroundItems.Option getGroundItemOption(String option) {
        switch (option)
        {
            case "1":
                return GroundItems.Option.FIRST;
            case "2":
                return GroundItems.Option.SECOND;
            case "3":
                return GroundItems.Option.THIRD;
            case "4":
                return GroundItems.Option.FOURTH;
            case "5":
                return GroundItems.Option.FIFTH;
            default:
                VarsMethods.log("Invalid Object option: " + option);
                return GroundItems.Option.valueOf(option);
        }
    }
}
