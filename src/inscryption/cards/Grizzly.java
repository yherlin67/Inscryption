package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Grizzly extends Animals_cards {

    public Grizzly() {super("Grizzly", 4, 6, 3, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN)));}

    @Override
    public String toString(){
        return "Grizzly("+this.getName()+")";
    }
}
