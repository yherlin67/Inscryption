package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Punaise extends Cartes_animaux{
    public Punaise() { super("Punaise", 1, 2, 0, 2, false, new ArrayList<>(List.of(PowerEnum.PUANT))); }
}
