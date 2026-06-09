package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Sparrow extends AnimalsCard {

public Sparrow() { super("Moineau", 1, 2, 1, 0, true, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Moineau("+this.getName()+")";
    }

    public Sparrow(Sparrow target) { super(target); }
    @Override public Sparrow clone() { return new Sparrow(this); }
}
