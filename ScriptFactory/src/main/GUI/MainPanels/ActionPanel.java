package main.GUI.MainPanels;

import main.GUI.EnterJButton;
import main.NewGuis.AdvancedOptionsGUI;
import main.NewGuis.ActionGuiInfo;
import main.NewGuis.ConditionGuiInfo;
import main.VarsMethods;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/10/2018.
 */
public class ActionPanel extends JPanel {
    private EnterJButton actionButton = new EnterJButton("Add Action"), startIfButton = new EnterJButton("Begin If-Action"), endIfButton = new EnterJButton("End If-Block"), removeButton = new EnterJButton ("Remove Line"), advancedButton = new EnterJButton("Advanced");
    private JTextPane actionList;

    private ActionGuiInfo newAction;
    private ConditionGuiInfo newCondition;
    private Consumer<Integer> removeAction;
    private Consumer<Boolean> endIf;
    private AdvancedOptionsGUI advancedOptions;

    public ActionPanel(JTextPane actionList, ActionGuiInfo newAction, ConditionGuiInfo newCondition, AdvancedOptionsGUI advancedOptions, Consumer<Integer> removeAction, Consumer<Boolean> endIf)
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
            String path = JOptionPane.showInputDialog("Enter the line# you wish to delete:");

            removeAction.accept(VarsMethods.parsePint(path));
        });

        advancedButton.addActionListener(o -> {
            advancedOptions.setVisible(true);
        });
    }
}
