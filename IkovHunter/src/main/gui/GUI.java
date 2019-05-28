package main.gui;

import main.VarsMethods;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    public boolean scriptStarted = false;

    private VarsMethods vars;

    private JComboBox selectHuntingActivity = new JComboBox(vars.huntingActivities);
    private JCheckBox powerMode = new JCheckBox("Drop animals? (Power mode)");
    private JButton start = new JButton("Start");

    public GUI(VarsMethods vars)
    {
        this.vars = vars;
        setTitle("Before's Script Writer Application - Hunter v1");
        setLayout(new GridLayout(4, 1));

        add(selectHuntingActivity);
        add(powerMode);
        add(start);

        selectHuntingActivity.setSelectedIndex(0);

        start.addActionListener(o ->
        {
            vars.powerMode = powerMode.isSelected();
            vars.currentHunt = vars.huntingActivities[selectHuntingActivity.getSelectedIndex()];
        });

        this.setVisible(true);
    }
}
