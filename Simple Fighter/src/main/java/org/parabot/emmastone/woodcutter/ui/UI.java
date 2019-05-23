package main.java.org.parabot.emmastone.woodcutter.ui;

import main.java.org.parabot.emmastone.woodcutter.ICore;

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
        setSize(250, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        final JTextField ids = new JTextField();
        ids.setBounds(6, 6, 238, 27);
        contentPane.add(ids);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                core.getSettings().attackNpc = Integer.parseInt(ids.getText());
                setVisible(false);
            }
        });
        submitButton.setBounds(58, 95, 117, 29);
        contentPane.add(submitButton);

        setVisible(true);
    }
}
