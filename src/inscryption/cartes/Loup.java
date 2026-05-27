package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Loup extends Cartes_animaux{
    public Loup() { super("Loup", 3, 2, 2, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }
}
