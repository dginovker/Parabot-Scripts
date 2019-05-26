package main.GUI.MainPanels;

import main.NewGuis.AdvancedOptionsGUI;
import main.NewGuis.NewActionGUI;
import main.NewGuis.NewConditionGUI;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/10/2018.
 */
public class ActionPanel extends JPanel {
    private JButton actionButton = new JButton("Add Action"), startIfButton = new JButton("Begin If-Action"), endIfButton = new JButton("End If-Block"), removeButton = new JButton ("Remove Action"), advancedButton = new JButton("Advanced");
    private JTextPane actionList;

    private NewActionGUI newAction;
    private NewConditionGUI newCondition;
    private Consumer<Integer> removeAction;
    private Consumer<Boolean> endIf;
    private AdvancedOptionsGUI advancedOptions;

    public ActionPanel(JTextPane actionList, NewActionGUI newAction, NewConditionGUI newCondition, AdvancedOptionsGUI advancedOptions, Consumer<Integer> removeAction, Consumer<Boolean> endIf)
    {
        this.actionList = actionList;
        this.newAction = newAction;
        this.newCondition = newCondition;
        this.removeAction = removeAction;
        this.endIf = endIf;
        this.advancedOptions = advancedOptions;

        setLayout(new BorderLayout(20, 0));

        this.actionList.setEditable(false);
        this.actionList.setText("                                                                                     ");
        Style redStyle = this.actionList.addStyle("red", null);
        StyleConstants.setForeground(redStyle, Color.red);
        Style blackStyle = this.actionList.addStyle("black", null);
        StyleConstants.setForeground(blackStyle, Color.black);

        JScrollPane scroll = new JScrollPane(this.actionList);
        this.actionList.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLACK));

        JPanel actionListButtons = new JPanel();
        actionListButtons.setLayout(new GridLayout(5, 1, 0, 20));
        actionListButtons.add(actionButton);
        actionListButtons.add(startIfButton);
        actionListButtons.add(endIfButton);
        actionListButtons.add(removeButton);
        actionListButtons.add(advancedButton);

        add(actionListButtons, BorderLayout.EAST);
        add(scroll, BorderLayout.WEST);

        initButtons();
    }

    private void initButtons() {
        actionButton.addActionListener(o -> {
            newAction.setVisible(true);
        });

        startIfButton.addActionListener(o -> {
            newCondition.setVisible(true);
        });

        endIfButton.addActionListener(o -> {
            endIf.accept(true);
        });

        removeButton.addActionListener(o -> {
            String path = JOptionPane.showInputDialog("Enter the Statement# you wish to delete:");

            removeAction.accept(Integer.parseInt(path));
        });

        advancedButton.addActionListener(o -> {
            advancedOptions.setVisible(true);
        });
    }
}
