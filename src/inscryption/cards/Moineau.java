package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Moineau extends Animals_cards {

public Moineau() { super("Moineau", 1, 2, 1, 0, true, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Moineau("+this.getName()+")";
    }
}
