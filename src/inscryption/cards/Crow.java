package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Crow extends AnimalsCard {

    public Crow() { super("Corbeau", 2, 3, 2, 0, true, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Corbeau("+this.getName()+")";
    }

    public Crow(Crow target) { super(target); }
    @Override public Crow clone() { return new Crow(this); }
}
