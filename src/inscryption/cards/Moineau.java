package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Moineau extends AnimalsCards {

public Moineau() { super("Moineau", 1, 2, 1, 0, true, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Moineau("+this.getName()+")";
    }

    public Moineau(Moineau target) { super(target); }
    @Override public Moineau clone() { return new Moineau(this); }
}
