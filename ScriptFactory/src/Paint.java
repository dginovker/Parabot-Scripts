import xobot.script.methods.tabs.Skills;
import xobot.script.util.Timer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by SRH on 1/9/2018.
 */
public class Paint {

    private final Color color1 = new Color(0, 0, 0);
    private final Font font1 = new Font("Arial", 0, 17);

    private Image img1;
    private final RenderingHints antialiasing = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    private int startxp;
    private int crafted;
    private Timer t;
    private String status;

    public Paint()
    {
        try {
            img1 = ImageIO.read(new URL("http://i.imgur.com/2hT2U83.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePaint(Graphics arg0)
    {
        int xp = Skills.getCurrentExp(Skills.RUNECRAFTING) - startxp;
        int xph = (int) ((xp) * 3600000D / (t.getElapsed()));
        int ph = (int) ((crafted) * 3600000D / (t.getElapsed()));

        Graphics2D g = (Graphics2D)arg0;
        g.setRenderingHints(antialiasing);

        g.drawImage(img1, 0, 162, null);
        g.setFont(font1);
        g.setColor(color1);
        g.drawString(t.toElapsedString(), 158, 248);
        g.drawString(status, 325, 248);
        g.drawString(String.valueOf(xp), 158, 296);
        g.drawString(String.valueOf(xph), 325, 296);
        g.drawString(String.valueOf(ph), 158, 272);
        g.drawString(String.valueOf(crafted), 325, 272);
    }
}
