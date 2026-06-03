package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Ecureuil extends AnimalsCards {

    public Ecureuil() { super("Ecureuil", 0, 1, 0, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Ecureuil("+this.getName()+")";
    }

    public Ecureuil(Ecureuil target) { super(target); }
    @Override public Ecureuil clone() { return new Ecureuil(this); }
}
