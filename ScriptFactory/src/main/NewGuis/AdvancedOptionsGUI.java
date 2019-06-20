package main.NewGuis;

import main.Actions.Action;
import main.VarsMethods;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static main.VarsMethods.centralizeComponent;
import static main.VarsMethods.log;

public class AdvancedOptionsGUI extends JFrame {
    private JPanel tickSpeedPanel = new JPanel();
    private TipsAndTricksGUI tipsFrame = new TipsAndTricksGUI();
    private JButton tipsAndTricksButton = new JButton("Tips and tricks");
    private JButton moveLineButton = new JButton("Move line");
    private JButton recoverPreviousScript = new JButton("Recover previous script");

    private JFrame moveLineFrame = new JFrame();

    private ArrayList<Action> actions;
    private Consumer<Integer> updateTextfield;

    public AdvancedOptionsGUI(ArrayList<Action> actions, Consumer<Integer> updateTextfield, JTextField tickSpeedField) {
        this.actions = actions;
        this.updateTextfield = updateTextfield;

        setLayout(new GridLayout(2, 3, 15, 15));
        setTitle("Parabot.org Script Factory - Advanced Options");
        initButtons();

        generateTickSpeedPanel(tickSpeedField);
        add(tickSpeedPanel);

        add(tipsAndTricksButton);

        generateMoveLineFrame();
        add(moveLineButton);

        add(recoverPreviousScript);

        setMinimumSize(new Dimension(400, 250));
        revalidate();
        pack();
    }

    private void generateTickSpeedPanel(JTextField tickSpeedField) {
        tickSpeedPanel.setLayout(new GridLayout(2, 1));
        tickSpeedPanel.add(new JLabel("Change tick speed (ms):"));
        tickSpeedField.setColumns(8);
        tickSpeedField.setText("1200");
        tickSpeedPanel.add(tickSpeedField);
    }

    private void generateMoveLineFrame() {
        JTextField lineToMove = new JTextField(6);
        JTextField lineToInsertAbove = new JTextField(6);
        JButton submitMove = new JButton("Submit");

        moveLineFrame.setLayout(new GridLayout(5, 1, 5, 15));
        moveLineFrame.setMinimumSize(new Dimension(250, 400));
        moveLineFrame.setTitle("Move a script line");

        moveLineFrame.add(new JLabel("Enter the line # you wish to move: "));
        moveLineFrame.add(lineToMove);
        moveLineFrame.add(new JLabel("Enter the line # you wish to move it above:"));
        moveLineFrame.add(lineToInsertAbove);
        moveLineFrame.add(submitMove);

        moveLineFrame.pack();

        submitMove.addActionListener(b ->
        {
            int lineToMoveAsPint = VarsMethods.parsePint(lineToMove.getText());
            int lineToPlaceAboveAsPint = VarsMethods.parsePint(lineToInsertAbove.getText());

            Action removed = actions.remove(lineToMoveAsPint);
            actions.add(lineToPlaceAboveAsPint, removed);
            updateTextfield.accept(5);
            moveLineFrame.setVisible(false);
            log("Successfully moved line " + lineToMove.getText() + ".");
        });

    }

    private void initButtons()
    {
        tipsAndTricksButton.addActionListener(o -> {
            tipsFrame.setVisible(true);
        });
        moveLineButton.addActionListener(o -> {
            moveLineFrame.setVisible(true);
        });
        recoverPreviousScript.addActionListener(o ->
        {
            VarsMethods.loadscript(actions, new File(VarsMethods.CACHED_LOC));
            updateTextfield.accept(5);
            this.setVisible(false);
        });
    }

    public void killAllGuis() {
        moveLineFrame.setVisible(false);
        tipsFrame.setVisible(false);
    }
}

class TipsAndTricksGUI extends JFrame
{
    private JLabel tipsAndTricksLabel = new JLabel("Welcome to the Tips and Tricks!");
    private JTextArea textAreaTips = new JTextArea(24, 60);
    private String[] tipsAndTipsStrings;

    public TipsAndTricksGUI()
    {
        setLayout(new GridLayout(1, 2));
        setTitle("Parabot.org Script Factory - Tips!");
        setMinimumSize(new Dimension(600, 250));

        tipsAndTipsStrings = new String[]{
                "Don't know what to do? Read this guide! https://parabot.slack.com",
                "Click File > Logger for feedback on your actions",
                "You can edit the scripts you save manually in Notepad",
                "The Type function can accept {ESC} if you want it to hit the \"Escape\" key",
        };

        textAreaTips.setEditable(false);

        for (String i : tipsAndTipsStrings)
        {
            textAreaTips.append("- " + i + "\n");
        }

        JScrollPane textAreaScroll = new JScrollPane(textAreaTips);
        textAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        add(centralizeComponent(tipsAndTricksLabel));
        add(textAreaScroll);
    }
}
