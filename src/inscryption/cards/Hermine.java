package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Hermine extends AnimalsCards {

    public Hermine() { super("Hermine", 1, 3, 1, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Hermine("+this.getName()+")";
    }

    public Hermine(Hermine target) { super(target); }
    @Override public Hermine clone() { return new Hermine(this); }
}
