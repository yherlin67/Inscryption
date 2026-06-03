package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Bug extends AnimalsCards {
    public Bug() { super("Punaise", 1, 2, 0, 2, false, new ArrayList<>(List.of(PowerEnum.STINKY))); }

    @Override
    public String toString(){
        return "Punaise("+this.getName()+")";
    }

    public Bug(Bug target) { super(target); }
    @Override public Bug clone() { return new Bug(this); }
}
