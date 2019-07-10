package scriptfactory.AdvancedGui;

import javax.swing.*;
import java.awt.*;

import static scriptfactory.NewGuis.NewStatementGUI.addEscapeHotkey;
import static scriptfactory.VarsMethods.centralizeComponent;

class TipsAndTricksGUI extends JFrame
{
    private JLabel tipsAndTricksLabel = new JLabel("Welcome to the Tips and Tricks!");
    private JTextArea textAreaTips = new JTextArea(12, 30);
    private String[] tipsAndTipsStrings;

    public TipsAndTricksGUI()
    {
        setLayout(new GridLayout(1, 2));
        setTitle("Parabot.org Script Factory - Tips!");
        setMaximumSize(new Dimension(300, 120));

        tipsAndTipsStrings = new String[]{
                "Don't know what to do? Read this guide! https://parabot.slack.com",
                "Click File > Logger for debugging help",
                "You can edit the scripts you save manually in Notepad",
                "The Type function can accept {ESC} if you want it to hit the \"Escape\" key",
                "scriptfactory.GUI navigation has hotkeys;",
                "\tTab brings you to the next input field",
                "\tShift Tab brings you to the previous field",
                "\tYou can hit Enter when highlighting over a button to click it",
                "\tYou can hit Escape to close sub-interfaces quickly",
                "Share your scripts! It helps everyone learn faster :)",
                "Interact-with-entity will only look for entities that are not in combat",
        };

        textAreaTips.setEditable(false);

        for (String i : tipsAndTipsStrings)
        {
            textAreaTips.append("- " + i + "\n");
        }

        JScrollPane textAreaScroll = new JScrollPane(textAreaTips);
        textAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textAreaScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(centralizeComponent(tipsAndTricksLabel));
        add(textAreaScroll);
        addEscapeHotkey(this);
        pack();
    }
}
