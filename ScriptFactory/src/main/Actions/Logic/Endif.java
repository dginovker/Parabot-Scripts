package main.Actions.Logic;

import main.Actions.Action;

/**
 * Created by SRH on 1/15/2018.
 */
public class Endif extends Action {
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
        return "Endif";
    }
}
