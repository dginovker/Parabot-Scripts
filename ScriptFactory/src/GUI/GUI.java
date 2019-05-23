package GUI;

import Actions.Logic.If;
import Actions.Logic.Endif;
import Actions.Action;
import GUI.MainPanels.ActionPanel;
import NewGuis.NewActionGUI;
import NewGuis.NewConditionGUI;
import Strategies.RunLoop;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by Cyn on 1/9/2018.
 */
public class GUI extends JFrame {
    private JButton saveButton = new JButton("Save"), loadButton = new JButton("Load");
    private JTextField file = new JTextField("");
    private File selectedFile = null;
    private JButton startButton = new JButton("Start");

    private JTextArea actionList = new JTextArea(18, 40);

    private NewConditionGUI newCondition;
    private NewActionGUI newAction;
    private ArrayList<Action> actions;

    public GUI(ArrayList<Action> actions)
    {
        this.actions = actions;

        //These are like little functions we pass around
        Consumer<Integer> updateTextfield = (Integer i) -> {
            updateActionList();
        };
        Consumer<Integer> removeAction = (Integer toRemove) -> {
            System.out.println("Trying to remove " + toRemove);
            if (actions.size() > toRemove && toRemove > 0)
            {
                actions.remove(toRemove);
            }
        };
        Consumer<Boolean> endIf = (Boolean remove) -> {
            actions.add(new Endif());
            updateTextfield.accept(1);
        };

        newAction = new NewActionGUI(actions, updateTextfield);
        newCondition = new NewConditionGUI(actions, updateTextfield);

        setTitle("Script Creator");
        setLayout(new BorderLayout(12, 20));

        add(new ActionPanel(actionList, newAction, newCondition, removeAction, endIf), BorderLayout.WEST);
        add(savePanel(), BorderLayout.EAST);
        add(startPanel(), BorderLayout.PAGE_END);

        this.revalidate();
        pack();

        addActionListeners();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void addActionListeners() {
        startButton.addActionListener(o -> {
            this.setVisible(false);
            System.out.println("Executing the following script:");
            for (Action a : actions)
            {
                System.out.println(a.toString());
            }
            new RunLoop(actions);
        });

        saveButton.addActionListener(o -> {
            updateFile();
            saveContents();
        });

        loadButton.addActionListener(o -> {
            updateFile();
            loadContents();
        });
    }

    private void loadContents() {
        actions.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                switch (line.split("\\{")[0])
                {
                    case "Action":
                        actions.add(new Action(line));
                        break;
                    case "If":
                        actions.add(new If(line));
                        break;
                    case "Endif":
                        actions.add(new Endif(line));
                        break;
                    default:
                        System.out.println("Unrecognized action: " + line.split("\\{")[0]);
                }
            }
            updateActionList();
            System.out.println("File loaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveContents() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(selectedFile);

            for (Action a : actions)
            {
                writer.println(a.toString());
            }
            writer.close();
            System.out.println("File saved successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateActionList()
    {
        actionList.setText("");
        int tabsInFront = 0;
        String toAppend;
        for (int i = 0; i < actions.size(); i++)
        {
            if (actions.get(i) instanceof Endif)
            {
                tabsInFront --;
            }

            toAppend = i + ")";
            for (int j = 0; j < tabsInFront; j++)
            {
                toAppend = toAppend + "    ";
            }
            actionList.append(toAppend + actions.get(i).toString() + "\n");

            if (actions.get(i) instanceof If)
            {
                tabsInFront ++;
            }
        }
    }

    private void updateFile() {
        JFileChooser saver = new JFileChooser();
        int option = saver.showOpenDialog(GUI.this);

        if (option == JFileChooser.APPROVE_OPTION) {
            file.setText(saver.getSelectedFile().getName());
            selectedFile = saver.getSelectedFile();
        }
    }

    private JPanel startPanel() {
        JPanel start = new JPanel();

        start.add(startButton);

        return start;
    }

    private JPanel savePanel() {
        JPanel save = new JPanel();
        save.setLayout(new GridLayout(2, 1, 20, 5));

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 1, 20, 5));
        buttons.add(saveButton);
        buttons.add(loadButton);


        save.add(buttons);

        JPanel chosen = new JPanel();
        chosen.setLayout(new FlowLayout(FlowLayout.LEFT));
        chosen.add(new JLabel("File chosen: "));
        file.setColumns(8);
        chosen.add(file);

        save.add(chosen);

        return save;
    }
}
