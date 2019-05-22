package Actions;

/**
 * Created by SRH on 1/9/2018.
 */
public class If extends Action {
    public If(String action, String param1, String param2, String param3) {
        super(action, param1, param2, param3);
    }

    @Override
    public String toString() {
        return "If{" +
                "action='" + action + '\'' +
                ", param1='" + param1 + '\'' +
                ", param2='" + param2 + '\'' +
                ", param3='" + param3 + '\'' +
                '}';
    }
}
