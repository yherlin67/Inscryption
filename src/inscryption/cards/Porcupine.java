package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Porcupine extends AnimalsCard {
    public Porcupine() { super("Porc-épic", 1, 2, 1, 0, false, new ArrayList<>(List.of(PowerEnum.SHARP_SPIKES))); }

    @Override
    public String toString(){
        return "Porc_epic("+this.getName()+")";
    }

    public Porcupine(Porcupine target) { super(target); }
    @Override public Porcupine clone() { return new Porcupine(this); }
}
