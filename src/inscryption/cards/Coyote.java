package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Coyote extends AnimalsCards {

    public Coyote() { super("Coyote", 2, 1, 0, 4, false, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Coyote("+this.getName()+")";
    }

    public Coyote(Coyote target) { super(target); }
    @Override public Coyote clone() { return new Coyote(this); }

}
