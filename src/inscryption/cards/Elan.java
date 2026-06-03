package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Elan extends AnimalsCards {
    public Elan() { super("Elan", 2, 4, 2, 0, false, new ArrayList<>(List.of(PowerEnum.COUREUR))); }

    @Override
    public String toString(){
        return "Elan("+this.getName()+")";
    }

    public Elan(Elan target) { super(target); }
    @Override public Elan clone() { return new Elan(this); }
}
