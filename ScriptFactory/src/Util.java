import java.util.Random;

/**
 * Created by Cyn on 1/9/2018.
 */
public class Util {
    private static Random rand = new Random();

    public static int avrgRand(int medium, int offset)
    {
        int sum = 0;
        for (int i = 0; i < offset; i++)
        {
            sum += rand.nextInt(medium);
        }

        return sum/offset;
    }
}
