import org.parabot.core.ui.Logger;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

@ScriptManifest(author = "Before", category = Category.OTHER, description = "Checks your IP, outputs to Logger", name = "IP/Proxy Checker", servers = { "Any" }, version = 1)
public class Core extends Script implements Paintable {
    private final ArrayList<Strategy> strategies = new ArrayList<>();
    private IPChecker ipChecker = new IPChecker();

    @Override
    public boolean onExecute() {
        strategies.add(ipChecker);
        provide(strategies);

        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);

        g.setFont(new Font("Cordia New", Font.PLAIN, 16));
        g.drawString("Parabot IP Checker", 560, 330);
        g.setFont(new Font("Cordia New", Font.PLAIN, 12));
        g.drawString("Open File > Logger for full history ", 560, 347);
        g.drawString("Latest IP verified: " + ipChecker.latestIP, 560, 360);
    }
}

class IPChecker implements Strategy {
    private final ArrayList<String> ipCheckURLS = new ArrayList<>();
    String latestIP = "None";

    public IPChecker()
    {
        ipCheckURLS.add("https://icanhazip.com/");
        ipCheckURLS.add("https://myexternalip.com/raw");
        ipCheckURLS.add("https://ipecho.net/plain");
        ipCheckURLS.add("https://bot.whatismyipaddress.com/");
        ipCheckURLS.add("http://checkip.amazonaws.com/");
    }

    @Override
    public boolean activate() {
        return true;
    }

    @Override
    public void execute() {
        log("Checking IP...");

        try {
            for (String i : ipCheckURLS)
            {
                URL whatismyip = new URL(i);
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        whatismyip.openStream()));

                latestIP = in.readLine(); //you get the IP as a String
                log("IP according to " + i + ": " + latestIP);
            }

        } catch (Exception e) {
            log("Exception caught: " + e.getMessage());
        }

        Time.sleep(1200);
    }

    private static void log(String str)
    {
        Logger.addMessage(str, false);
        System.out.println(str);
    }
}
