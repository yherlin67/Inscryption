package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Grizzly extends AnimalsCard {

    public Grizzly() {super("Grizzly", 4, 6, 3, 0, false, new ArrayList<>(List.of(PowerEnum.NONE)));}

    @Override
    public String toString(){
        return "Grizzly("+this.getName()+")";
    }

    public Grizzly(Grizzly target) { super(target); }
    @Override public Grizzly clone() { return new Grizzly(this); }
}
