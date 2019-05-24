package Actions.Logic;

import Actions.Action;

/**
 * Created by SRH on 1/15/2018.
 */
public class Endif extends Action {
    public Endif(String action, String param1, String param2, String param3) {
        super(action, param1, param2, param3);
    }

    public Endif() {
        super();
    }

    public Endif(String fromString)
    {
        super(fromString);
    }

    @Override
    public String toString()
    {
        return "end if";
    }
}
