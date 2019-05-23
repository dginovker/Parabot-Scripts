package main.java.org.parabot.emmastone.woodcutter.ui;

import main.java.org.parabot.emmastone.woodcutter.ICore;
import org.rev317.min.accessors.Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author EmmaStone, Before
 */
public class UI extends JFrame {

    public UI(final ICore core) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(350, 450);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        final JLabel idDesc = new JLabel("Enter the id of the NPC you wish to attack");
        final JTextField ids = new JTextField();
        idDesc.setBounds(6, 6, 240, 10);
        ids.setBounds(6, 26, 240, 37);
        contentPane.add(idDesc);
        contentPane.add(ids);

        final JLabel interactDesc = new JLabel("Enter which option is attack (default is 1)");
        final JTextField interact = new JTextField("1");
        interactDesc.setBounds(6, 97, 240, 107);
        interact.setBounds(6, 117, 240, 157);
        contentPane.add(interactDesc);
        contentPane.add(interact);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            core.getSettings().attackNpc = Integer.parseInt(ids.getText());
            core.getSettings().attackOption = Integer.parseInt(interact.getText());
            setVisible(false);
        });
        submitButton.setBounds(58, 200, 307, 250);
        contentPane.add(submitButton);

        setVisible(true);
    }
}
