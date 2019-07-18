package scriptfactory.GUI;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EnterJButton extends JButton {
    public EnterJButton(String text)
    {
        super(text);
        final EnterJButton me = this;
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
                    me.doClick();
            }
        });

    }
}
