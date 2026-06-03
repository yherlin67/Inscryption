package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Porc_epic extends AnimalsCards {
    public Porc_epic() { super("Porc-épic", 1, 2, 1, 0, false, new ArrayList<>(List.of(PowerEnum.PIQUES_POINTUES))); }

    @Override
    public String toString(){
        return "Porc_epic("+this.getName()+")";
    }

    public Porc_epic(Porc_epic target) { super(target); }
    @Override public Porc_epic clone() { return new Porc_epic(this); }
}
