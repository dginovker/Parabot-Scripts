package Actions;

/**
 * Created by SRH on 1/15/2018.
 */
public class endIf extends Action {
    public endIf(String action, String param1, String param2, String param3) {
        super(action, param1, param2, param3);
    }

    public endIf() {
        super();
    }

    @Override
    public String toString()
    {
        return "end if";
    }
}
