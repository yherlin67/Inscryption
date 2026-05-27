package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Elan extends Cartes_animaux{
    public Elan() { super("Elan", 2, 4, 2, 0, false, new ArrayList<>(List.of(PowerEnum.COUREUR))); }
}
