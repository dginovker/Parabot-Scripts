package main.GUI;

import main.Actions.Logic.If;
import main.Actions.Logic.Endif;
import main.Actions.Action;
import main.Actions.Logic.IfNot;
import main.GUI.MainPanels.ActionPanel;
import main.NewGuis.AdvancedOptionsGUI;
import main.NewGuis.ActionGuiInfo;
import main.NewGuis.ConditionGuiInfo;
import main.VarsMethods;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

import static main.VarsMethods.log;

/**
 * Created by Cyn on 1/9/2018.
 */
public class GUI extends JFrame {
    public boolean scriptStarted = false;
    private EnterJButton saveButton = new EnterJButton("Save"), loadButton = new EnterJButton("Load");
    private JTextField mostRecentLog = new JTextField("");
    private File selectedFile = null;
    private EnterJButton startButton = new EnterJButton("Start");

    private JTextPane actionList = new JTextPane();
    private JTextField tickSpeedField = new JTextField(0);

    private ConditionGuiInfo newCondition;
    private ActionGuiInfo newAction;
    private AdvancedOptionsGUI advancedOptions;
    private ArrayList<Action> actions;

    public GUI(ArrayList<Action> actions)
    {
        this.actions = actions;

        //These are like little functions we pass around
        Consumer<Integer> updateTextfield = (Integer i) -> {
            updateActionList();
        };
        Consumer<Integer> removeAction = (Integer toRemove) -> {
            log("Trying to remove " + toRemove);
            int pint = toRemove;
            actions.remove(pint);
            updateActionList();
        };
        Consumer<Boolean> endIf = (Boolean remove) -> {
            actions.add(new Endif());
            updateTextfield.accept(1);
        };

        newAction = new ActionGuiInfo(actions, updateTextfield);
        newCondition = new ConditionGuiInfo(actions, updateTextfield);
        advancedOptions = new AdvancedOptionsGUI(actions, updateTextfield, tickSpeedField);


        setTitle("Parabot.org Script Factory");
        setLayout(new BorderLayout(12, 20));

        add(new ActionPanel(actionList, newAction, newCondition, advancedOptions, removeAction, endIf), BorderLayout.WEST);
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
            log("Executing the following script:");
            for (Action a : actions)
            {
                log(a.toString());
            }
            VarsMethods.tickSpeed = VarsMethods.parsePint(tickSpeedField.getText());
            selectedFile = new File(VarsMethods.CACHED_LOC);
            saveContents();
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
        VarsMethods.loadscript(actions, selectedFile);
        updateActionList();
    }

    private void saveContents() {
        VarsMethods.savescript(actions, selectedFile);
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

            if (actions.get(i) instanceof If || actions.get(i) instanceof IfNot)
            {
                tabsInFront ++;
            }
        }
    }

    private void updateFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(VarsMethods.DEFAULT_DIR));

        int option = fileChooser.showOpenDialog(GUI.this);

        if (option == JFileChooser.APPROVE_OPTION) {
            mostRecentLog.setText("File used: " + fileChooser.getSelectedFile().getPath());
            selectedFile = fileChooser.getSelectedFile();
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

    public void killAllGuis()
    {
        newAction.setVisible(false);
        newCondition.setVisible(false);
        advancedOptions.killAllGuis();
        advancedOptions.setVisible(false);
    }

}
