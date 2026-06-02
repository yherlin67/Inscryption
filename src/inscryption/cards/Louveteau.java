package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Louveteau extends Animals_cards {

    public Louveteau() { super("Louveteau", 1, 1, 1, 0, false, new ArrayList<>(List.of(PowerEnum.CROISSANCE))); }

    @Override
    public String toString(){
        return "Louveteau("+this.getName()+")";
    }
}
