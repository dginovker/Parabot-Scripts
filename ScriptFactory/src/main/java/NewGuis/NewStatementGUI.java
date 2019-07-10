package main.java.NewGuis;

import main.java.Actions.Action;
import main.java.Actions.Logic.If;
import main.java.Actions.Logic.IfNot;
import main.java.GUI.EnterJButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;

import static main.java.VarsMethods.MAX_PARAMS;

/**
 * Created by SRH on 1/10/2018.
 * Creates the main.java.GUI that is extended by actions and if statements
 * Allows user to generate new actions/if statements
 */
public class NewStatementGUI extends JFrame {
    private ArrayList<JTextArea> inputs = new ArrayList<>();
    private ArrayList<JLabel> descLabels = new ArrayList<>();

    private EnterJButton add = new EnterJButton("Add");
    private EnterJButton addInverse = new EnterJButton("Add inverse");
    private JPanel addPanel = new JPanel();
    private String selectedAction;

    /**
     * Creates whole GUI
     * @param title: Title for JFrame
     * @param actionList: List of steps (actions) in script
     * @param updateTextfield: Function to refresh the action list UI
     * @param actionTypes: List of possible actions the user can select
     * @param descStrings: Descriptions to display for the actions
     */
    void initGui(String title, ArrayList<Action> actionList, Consumer<Integer> updateTextfield, String[] actionTypes, Descriptions[] descStrings) {
        setTitle(title);
        setLayout(new BorderLayout());

        for (int i = 0; i < MAX_PARAMS; i++) {
            inputs.add(new JTextArea());
            descLabels.add(new JLabel());
        }

        JPanel dropDownAndDesc = new JPanel(new GridLayout(2, 1));
        dropDownAndDesc.add(actionTypeCombo(actionTypes, descStrings));
        dropDownAndDesc.add(new JLabel("To IDs, click the \"Debug\" dropdown on the top right corner of the client."));
        add(dropDownAndDesc, BorderLayout.PAGE_START);

        add(fillInfo());

        add(generateAddButton(title), BorderLayout.PAGE_END);

        setSize(650, 300);

        addEscapeHotkey(this);

        add.addActionListener(o -> {
            if (this.getTitle().contains("action"))
                actionList.add(new Action(selectedAction, inputs));
            else
                actionList.add(new If(selectedAction, inputs));
            updateTextfield.accept(5);
            this.setVisible(false);
            clearInputs();
        });
        addInverse.addActionListener(o -> {
            actionList.add(new IfNot(selectedAction, inputs));
            updateTextfield.accept(5);
            this.setVisible(false);
            clearInputs();
        });
    }

    /**
     * Updates the UI to include descriptions for newly selected action
     *
     * @param actionTypes: Actions user can select
     * @param descs: Field descriptions for that action
     */
    private JComboBox actionTypeCombo(String[] actionTypes, Descriptions[] descs) {
        JComboBox actionType = new JComboBox(actionTypes);
        selectedAction = actionTypes[0]; //prevents null
        setupInputFields(descs[0]);

        actionType.addActionListener(o -> {
            selectedAction = actionType.getSelectedItem().toString();

            setupInputFields(descs[actionType.getSelectedIndex()]);
        });

        return actionType;
    }

    /**
     * Adds the "Add" button
     * Figures out if "Add Inverse" is needed
     * Adds their hotkeys
     */
    private JPanel generateAddButton(String title) {
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

        for (int i = 0; i < 3; i++) {
            fillInfo.add(detailGrabber(inputs.get(i), descLabels.get(i)));
            setHKNavigation(inputs.get(i));
        }

        fillInfo.updateUI();
        fillInfo.repaint();
        return fillInfo;
    }

    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).setText("");
            inputs.get(i).repaint();
            //i.updateUI() ?
        }
    }

    /**
     * Makes the textareas respond to hotkeys
     * Currently supports Tab, Shift Tab
     * @param textArea current TextArea to operate on
     */
    private void setHKNavigation(JTextArea textArea) {
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {
                    if (keyEvent.getModifiers() > 0)
                        textArea.transferFocusBackward();
                    else
                        textArea.transferFocus();
                    keyEvent.consume();
                }
            }
        });
    }

    public static void addEscapeHotkey(JFrame temp) {
        ActionListener escListener = e -> temp.setVisible(false);
        temp.getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);

    }

    /**
     * @param input: Where the user types in the value
     * @param desc: Description of the input field
     * @return: Panel holding input field and description
     */
    private JPanel detailGrabber(JTextArea input, JLabel desc) {
        JPanel forum = new JPanel();
        forum.setLayout(new FlowLayout(FlowLayout.LEFT));
        forum.add(input);
        forum.add(desc);
        input.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        input.setColumns(8);

        return forum;
    }

    /**
     *
     */
    private void setupInputFields(Descriptions desc)
    {
        for (int i = 0; i < MAX_PARAMS; i++) {
            if (desc.getLabelText(i) == null)
                inputs.get(i).setVisible(false);
            else
                inputs.get(i).setVisible(true);

            descLabels.get(i).setText(desc.getLabelText(i));
        }
    }

    private void clearInputs() {
        for (int i = 0; i < MAX_PARAMS; i++)
            inputs.get(i).setText("");
    }

    /**
     * Simple class holding three strings that will be the descriptions on the UI
     */
    class Descriptions {
        private String[] labelText;

        Descriptions(String... descriptions)
        {
            this.labelText = descriptions;
        }

        String getLabelText(int index) {
            if (index < labelText.length)
                return labelText[index];
            return null;
        }
    }
}
