package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Corbeau extends AnimalsCards {

    public Corbeau() { super("Corbeau", 2, 3, 2, 0, true, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Corbeau("+this.getName()+")";
    }

    public Corbeau(Corbeau target) { super(target); }
    @Override public Corbeau clone() { return new Corbeau(this); }
}
