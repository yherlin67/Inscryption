package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Moose extends AnimalsCards {
    public Moose() { super("Elan", 2, 4, 2, 0, false, new ArrayList<>(List.of(PowerEnum.RUNNER))); }

    @Override
    public String toString(){
        return "Elan("+this.getName()+")";
    }

    public Moose(Moose target) { super(target); }
    @Override public Moose clone() { return new Moose(this); }
}
