package GUI.NewGuis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Created by SRH on 1/10/2018.
 * Creates the GUI that is extended by actions and if statements
 * Allows user to generate new actions/if statements
 */
public class NewStatementGUI extends JFrame {
    private JTextArea first = new JTextArea(), second = new JTextArea(), third = new JTextArea();
    private JLabel firstDesc = new JLabel(), secondDesc = new JLabel(), thirdDesc = new JLabel();

    private JButton add = new JButton("Add");
    private String selectedAction;

    /**
     * Creates whole GUI
     * @param title: Title for JFrame
     * @param actionList: Action list that gets appended to based on user input on this UI
     * @param updateTextfield: Function to update the action list on the first (and main) GUI
     * @param actionTypes: List of possible actions the user can select
     * @param descs: Descriptions to display the user throughout the UI
     */
    void initGui(String title, ArrayList<Actions.Action> actionList, Consumer<Integer> updateTextfield, String[] actionTypes, Descriptions[] descs) {
        setTitle(title);
        setLayout(new BorderLayout());

        JPanel dropDownAndDesc = new JPanel(new GridLayout(2, 1));
        dropDownAndDesc.add(actionTypeCombo(actionTypes, descs));
        dropDownAndDesc.add(new JLabel("To find the IDs of entities/etc, click the \"Debug\" dropdown on the top right corner of the client."));
        add(dropDownAndDesc, BorderLayout.PAGE_START);

        add(fillInfo());

        add(add, BorderLayout.PAGE_END);

        setSize(650, 300);

        add.addActionListener(o -> {
            if (this.getTitle().contains("action"))
            {
                actionList.add(new Actions.Action(selectedAction, first.getText(), second.getText(), third.getText()));
            }
            else
            {
                actionList.add(new Actions.If(selectedAction, first.getText(), second.getText(), third.getText()));
            }
            updateTextfield.accept(5);
            this.setVisible(false);
        });
    }

    /**
     * @param actionTypes: Options for the dropdown
     * @param descs: Descriptions that appear depending on dropdown option selected
     * @return
     */
    JComboBox actionTypeCombo(String[] actionTypes, Descriptions[] descs) {
        JComboBox actionType = new JComboBox(actionTypes);
        actionType.addActionListener(o -> {
            selectedAction = actionType.getSelectedItem().toString();

            setDesc(descs[actionType.getSelectedIndex()].getS(), descs[actionType.getSelectedIndex()].getS1(), descs[actionType.getSelectedIndex()].getS2());
        });

        return actionType;
    }

    /**
     * @return: Panel holding the 3 descriptions and fields for user input in the UI
     */
    JPanel fillInfo() {
        JPanel fillInfo = new JPanel();
        fillInfo.setLayout(new GridLayout(3, 1, 20, 20));

        fillInfo.add(detailGrabber(first, firstDesc));
        fillInfo.add(detailGrabber(second, secondDesc));
        fillInfo.add(detailGrabber(third, thirdDesc));

        return fillInfo;
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
    void setDesc(String s, String s1, String s2)
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

        public Descriptions(String s, String s1, String s2)
        {
            this.s = s;
            this.s1 = s1;
            this.s2 = s2;
        }
        public Descriptions(String s, String s1)
        {
            this.s = s;
            this.s1 = s1;
        }

        public Descriptions(String s) {
            this.s = s;
        }

        public String getS2() {
            return s2;
        }

        public String getS1() {
            return s1;
        }

        public String getS() {
            return s;
        }
    }
}
