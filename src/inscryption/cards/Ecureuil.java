package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Ecureuil extends Animals_cards {

    public Ecureuil() { super("Ecureuil", 0, 1, 0, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Ecureuil("+this.getName()+")";
    }
}
