package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Cub_scout extends AnimalsCard {

    public Cub_scout() { super("Louveteau", 1, 1, 1, 0, false, new ArrayList<>(List.of(PowerEnum.GROW))); }

    @Override
    public String toString(){
        return "Louveteau("+this.getName()+")";
    }

    public Cub_scout(Cub_scout target) { super(target); }
    @Override public Cub_scout clone() { return new Cub_scout(this); }
}
