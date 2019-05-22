package GUI.MainPanels;

import GUI.NewGuis.NewActionGUI;
import GUI.NewGuis.NewConditionGUI;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/10/2018.
 */
public class ActionPanel extends JPanel {
    private JButton actionButton = new JButton("Add Action"), startIfButton = new JButton("Begin If-Action"), endIfButton = new JButton("End If-Block"), removeButton = new JButton ("Remove Action");
    private JTextArea actionList;
    private NewActionGUI newAction;
    private NewConditionGUI newCondition;
    private Consumer<Integer> removeAction;
    private Consumer<Boolean> endIf;

    public ActionPanel(JTextArea actionList, NewActionGUI newAction, NewConditionGUI newCondition, Consumer<Integer> removeAction, Consumer <Boolean> endIf)
    {
        this.actionList = actionList;
        this.newAction = newAction;
        this.newCondition = newCondition;
        this.removeAction = removeAction;
        this.endIf = endIf;

        setLayout(new BorderLayout(20, 0));

        this.actionList.setEditable(false);

        JScrollPane scroll = new JScrollPane(this.actionList);
        this.actionList.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLACK));
        add(scroll, BorderLayout.WEST);

        JPanel actionListButtons = new JPanel();
        actionListButtons.setLayout(new GridLayout(5, 1, 0, 20));
        actionListButtons.add(actionButton);
        actionListButtons.add(startIfButton);
        actionListButtons.add(endIfButton);
        actionListButtons.add(removeButton);

        add(actionListButtons);

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

            try{
                removeAction.accept(new Integer(Integer.parseInt(path)));
            } catch (Exception e)
            {
                //Do nothing
            }
        });
    }
}
