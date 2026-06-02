package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Corbeau extends Animals_cards {

    public Corbeau() { super("Corbeau", 2, 3, 2, 0, true, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Corbeau("+this.getName()+")";
    }
}
