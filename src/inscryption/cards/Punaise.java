package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Punaise extends Animals_cards {
    public Punaise() { super("Punaise", 1, 2, 0, 2, false, new ArrayList<>(List.of(PowerEnum.PUANT))); }

    @Override
    public String toString(){
        return "Punaise("+this.getName()+")";
    }
}
