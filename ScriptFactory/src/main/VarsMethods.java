package main;

import main.Actions.Action;
import main.Actions.Logic.Endif;
import main.Actions.Logic.If;
import main.Actions.Logic.InverseIf;
import org.parabot.core.ui.Logger;
import org.rev317.min.api.methods.Items;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.SceneObjects;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class VarsMethods {
    public static int tickSpeed = 1200;
    public String currentAction = "";
    public final static String DEFAULT_DIR = System.getProperty("user.home") + System.getProperty("file.separator") + "Parabot" + System.getProperty("file.separator") + "Script Factory";
    public final static String CACHED_LOC = DEFAULT_DIR + System.getProperty("file.separator") + "ScriptFactory cache.txt";

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
                    case "InverseIf":
                        actions.add(new InverseIf(line));
                        break;
                    case "Endif":
                        actions.add(new Endif(line));
                        break;
                    default:
                        actions.add(new Action(line));
                }
            }
            log("File loaded successfully");
        } catch (FileNotFoundException ignored) {
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
            log("File saved successfully");
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
}
