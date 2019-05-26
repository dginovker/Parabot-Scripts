package main.GUI;

import main.Actions.Logic.If;
import main.Actions.Logic.Endif;
import main.Actions.Action;
import main.GUI.MainPanels.ActionPanel;
import main.NewGuis.NewActionGUI;
import main.NewGuis.NewConditionGUI;
import main.VarsMethods;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by Cyn on 1/9/2018.
 */
public class GUI extends JFrame {
    public boolean scriptStarted = false;
    private JButton saveButton = new JButton("Save"), loadButton = new JButton("Load");
    private JTextField mostRecentLog = new JTextField("");
    private File selectedFile = null;
    private JButton startButton = new JButton("Start");

    private JTextPane actionList = new JTextPane();
    //private JTextArea actionList = new JTextArea(18, 40);

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
            VarsMethods.log("Trying to remove " + toRemove);
            int pint = toRemove;
            actions.remove(pint);
            updateActionList();
        };
        Consumer<Boolean> endIf = (Boolean remove) -> {
            actions.add(new Endif());
            updateTextfield.accept(1);
        };

        newAction = new NewActionGUI(actions, updateTextfield);
        newCondition = new NewConditionGUI(actions, updateTextfield);

        setTitle("Parabot.org Script Factory");
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
            VarsMethods.log("Executing the following script:");
            for (Action a : actions)
            {
                VarsMethods.log(a.toString());
            }
            scriptStarted = true;
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
                    case "If":
                        actions.add(new If(line));
                        break;
                    case "Endif":
                        actions.add(new Endif(line));
                        break;
                    default:
                        actions.add(new Action(line));
                }
            }
            updateActionList();
            VarsMethods.log("File loaded successfully");
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
            VarsMethods.log("File saved successfully");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateActionList()
    {
        actionList.setText("");
        int tabsInFront = 0;
        String prepend;
        for (int i = 0; i < actions.size(); i++)
        {
            if (actions.get(i) instanceof Endif)
            {
                tabsInFront --;
            }

            prepend = i + ": " + (i < 10 ? " " : "");
            for (int j = 0; j < tabsInFront; j++)
            {
                prepend = prepend + "    ";
            }
            try {
                actionList.getStyledDocument().insertString(actionList.getStyledDocument().getLength(), prepend, actionList.getStyle("red"));
                actionList.getStyledDocument().insertString(actionList.getStyledDocument().getLength(), actions.get(i).toString() + "\n", actionList.getStyle("black"));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            //actionList.append(prepend + actions.get(i).toString() + "\n");

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
            mostRecentLog.setText("File used: " + saver.getSelectedFile().getPath());
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
        chosen.add(new JLabel("Info: "));
        mostRecentLog.setColumns(16);
        mostRecentLog.setEditable(false);
        chosen.add(mostRecentLog);

        save.add(chosen);

        return save;
    }

}
