package main.gui;

import main.VarsMethods;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public boolean scriptStarted = false;

    private JComboBox<String> selectHuntingActivity;
    private JCheckBox powerMode = new JCheckBox("Drop animals? (Power mode)");
    private JButton start = new JButton("Start");

    public GUI(VarsMethods vars)
    {
        selectHuntingActivity = new JComboBox<>(vars.huntingActivities);
        setTitle("Before's Script Writer Application - Hunter v1");
        setLayout(new GridLayout(4, 1));
        setSize(260, 420);

        powerMode.setSelected(false);

        add(selectHuntingActivity);
        add(powerMode);
        add(start);

        selectHuntingActivity.setSelectedIndex(0);

        start.addActionListener(o ->
        {
            scriptStarted = true;
            vars.powerMode = powerMode.isSelected();
            vars.currentHunt = vars.huntingActivities[selectHuntingActivity.getSelectedIndex()];
            setVisible(false);
        });

        this.setVisible(true);
    }
}
