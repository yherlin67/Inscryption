package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Porc_epic extends Animals_cards {
    public Porc_epic() { super("Porc-épic", 1, 2, 1, 0, false, new ArrayList<>(List.of(PowerEnum.PIQUES_POINTUES))); }

    @Override
    public String toString(){
        return "Porc_epic("+this.getName()+")";
    }
}
