package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Vipere extends Animals_cards {

    public Vipere() {
        super("Vipère", 1, 1, 2, 0, false, new ArrayList<>(List.of(PowerEnum.CONTACT_MORTEL))); }

    @Override
    public String toString(){
        return "Vipere("+this.getName()+")";
    }
}
