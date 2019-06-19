package main.NewGuis;

import main.Actions.Action;
import main.Actions.Logic.If;
import main.Actions.Logic.InverseIf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/10/2018.
 * Creates the main.GUI that is extended by actions and if statements
 * Allows user to generate new actions/if statements
 */
class NewStatementGUI extends JFrame {
    private JTextArea first = new JTextArea(), second = new JTextArea(), third = new JTextArea();
    private JLabel firstDesc = new JLabel(), secondDesc = new JLabel(), thirdDesc = new JLabel();

    private JButton add = new JButton("Add");
    private JButton addInverse = new JButton("Add inverse");
    private JPanel addPanel = new JPanel();
    private String selectedAction;

    /**
     * Creates whole GUI
     * @param title: Title for JFrame
     * @param actionList: Action list that gets appended to based on user input on this UI
     * @param updateTextfield: Function to update the action list on the first (and main) src.main.GUI
     * @param actionTypes: List of possible actions the user can select
     * @param descs: Descriptions to display the user throughout the UI
     */
    void initGui(String title, ArrayList<Action> actionList, Consumer<Integer> updateTextfield, String[] actionTypes, Descriptions[] descs) {
        setTitle(title);
        setLayout(new BorderLayout());

        JPanel dropDownAndDesc = new JPanel(new GridLayout(2, 1));
        dropDownAndDesc.add(actionTypeCombo(actionTypes, descs));
        dropDownAndDesc.add(new JLabel("To find the IDs of entities/etc, click the \"Debug\" dropdown on the top right corner of the client."));
        add(dropDownAndDesc, BorderLayout.PAGE_START);

        add(fillInfo());

        add(generateAddPanel(title), BorderLayout.PAGE_END);

        setSize(650, 300);

        add.addActionListener(o -> {
            if (this.getTitle().contains("action"))
            {
                actionList.add(new Action(selectedAction, first.getText(), second.getText(), third.getText()));
            }
            else
            {
                actionList.add(new If(selectedAction, first.getText(), second.getText(), third.getText()));
            }
            updateTextfield.accept(5);
            this.setVisible(false);
        });
        addInverse.addActionListener(o -> {
            actionList.add(new InverseIf(selectedAction, first.getText(), second.getText(), third.getText()));
            updateTextfield.accept(5);
            this.setVisible(false);
        });
    }

    /**
     * @param actionTypes: Options for the dropdown
     * @param descs: Descriptions that appear depending on dropdown option selected
     */
    private JComboBox actionTypeCombo(String[] actionTypes, Descriptions[] descs) {
        JComboBox actionType = new JComboBox(actionTypes);
        selectedAction = actionTypes[0]; //prevents null
        setDesc(descs[0].getS(), descs[0].getS1(), descs[0].getS2());

        actionType.addActionListener(o -> {
            selectedAction = actionType.getSelectedItem().toString();

            setDesc(descs[actionType.getSelectedIndex()].getS(), descs[actionType.getSelectedIndex()].getS1(), descs[actionType.getSelectedIndex()].getS2());
        });

        return actionType;
    }

    /**
     * Figures out if "Add Inverse" is needed
     */
    private JPanel generateAddPanel(String title) {
        addPanel.add(add);
        if (title.equals("Add new condition"))
        {
            addPanel.add(addInverse);
        }
        return addPanel;
    }

    /**
     * @return: Panel holding the 3 descriptions and fields for user input in the UI
     */
    private JPanel fillInfo() {
        JPanel fillInfo = new JPanel();
        fillInfo.setLayout(new GridLayout(3, 1, 20, 20));

        fillInfo.add(detailGrabber(first, firstDesc));
        fillInfo.add(detailGrabber(second, secondDesc));
        fillInfo.add(detailGrabber(third, thirdDesc));

        setTabNavigation(first);

        return fillInfo;
    }

    /**
     * Makes the textareas switch when pressing the Tab key
     * @param textArea current TextArea to operate on
     */
    private void setTabNavigation(JTextArea textArea) {
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_TAB)
                {
                    if (keyEvent.getModifiers() > 0)
                        first.transferFocusBackward();
                    else
                        first.transferFocus();

                    keyEvent.consume();
                }
            }
        });
    }

    /**
     * @param area: Where the user types in the value
     * @param lab: Description of the input field
     * @return: Panel holding input field and description
     */
    private JPanel detailGrabber(JTextArea area, JLabel lab) {
        JPanel forum = new JPanel();
        forum.setLayout(new FlowLayout(FlowLayout.LEFT));
        forum.add(area);
        forum.add(lab);
        area.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        area.setColumns(8);

        return forum;
    }

    /**
     * @param s, first description beside textfield
     * @param s1 ..second
     * @param s2 ..third
     */
    private void setDesc(String s, String s1, String s2)
    {
        firstDesc.setText(s);
        secondDesc.setText(s1);
        thirdDesc.setText(s2);
    }

    /**
     * Simple class holding three strings that will be the descriptions on the UI
     */
    class Descriptions {
        private String s = null;
        private String s1 = null;
        private String s2 = null;

        Descriptions(String s, String s1, String s2)
        {
            this.s = s;
            this.s1 = s1;
            this.s2 = s2;
        }
        Descriptions(String s, String s1)
        {
            this.s = s;
            this.s1 = s1;
        }

        Descriptions(String s) {
            this.s = s;
        }

        String getS2() {
            return s2;
        }

        String getS1() {
            return s1;
        }

        String getS() {
            return s;
        }
    }
}
