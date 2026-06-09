package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Squirrel extends AnimalsCard {

    public Squirrel() { super("Ecureuil", 0, 1, 0, 0, false, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Ecureuil("+this.getName()+")";
    }

    public Squirrel(Squirrel target) { super(target); }
    @Override public Squirrel clone() { return new Squirrel(this); }
}
