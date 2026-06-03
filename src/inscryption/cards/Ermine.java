package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Ermine extends AnimalsCards {

    public Ermine() { super("Hermine", 1, 3, 1, 0, false, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Hermine("+this.getName()+")";
    }

    public Ermine(Ermine target) { super(target); }
    @Override public Ermine clone() { return new Ermine(this); }
}
