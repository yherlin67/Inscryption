package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Porc_epic extends Cartes_animaux{
    public Porc_epic() { super("Porc-épic", 1, 2, 1, 0, false, new ArrayList<>(List.of(PowerEnum.PIQUES_POINTUES))); }
}
