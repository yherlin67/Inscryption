package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Coyote extends Cartes_animaux {

    public Coyote() { super("Coyote", 2, 1, 0, 4, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Coyote("+this.getName()+")";
    }

}
