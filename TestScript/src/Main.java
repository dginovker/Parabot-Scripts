import main.Strategies.Bank;
import main.Strategies.Fish;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;

import java.util.ArrayList;

@ScriptManifest(author = "Before", category = Category.OTHER, description = "Tutorial skeleton", name = "Tutorial", servers = { "IKOV" }, version = 1)
public class Main extends Script {

    private final ArrayList<Strategy> strategies = new ArrayList<>();

    private long startTime = System.currentTimeMillis();

    @Override
    public boolean onExecute() {
        strategies.add(new Fish());
        strategies.add(new Bank());
        provide(strategies);

        return true;
    }

    /*@Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Cordia New", Font.PLAIN, 26));

        g.drawString("Draynor and Piscatoris Fisher", 10, 30);

        g.setFont(new Font("Cordia New", Font.PLAIN, 20));
        g.drawString("version " + ((ScriptManifest) new RefClass(this).getAnnotations()[0]).version(), 10, 50);
        g.drawString("Status : " + status, 8, 325);
        g.drawString("Runtime: " + runTime(startTime), 10, 70);
        g.drawString("Fish Caught: " + 0, 10, 90);
    }

    static String runTime(long i) {
        DecimalFormat nf = new DecimalFormat("00");
        long millis = System.currentTimeMillis() - i;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;
        return nf.format(hours) + ":" + nf.format(minutes) + ":"
                + nf.format(seconds);
    }*/

}

