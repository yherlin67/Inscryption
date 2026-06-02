package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Loup extends Animals_cards {
    public Loup() { super("Loup", 3, 2, 2, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Loup("+this.getName()+")";
    }
}
