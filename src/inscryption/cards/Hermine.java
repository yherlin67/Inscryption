package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Hermine extends Animals_cards {

    public Hermine() { super("Hermine", 1, 3, 1, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Hermine("+this.getName()+")";
    }
}
