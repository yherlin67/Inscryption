package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Louveteau extends AnimalsCards {

    public Louveteau() { super("Louveteau", 1, 1, 1, 0, false, new ArrayList<>(List.of(PowerEnum.CROISSANCE))); }

    @Override
    public String toString(){
        return "Louveteau("+this.getName()+")";
    }

    public Louveteau(Louveteau target) { super(target); }
    @Override public Louveteau clone() { return new Louveteau(this); }
}
