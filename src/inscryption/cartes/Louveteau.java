package inscryption.cartes;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Louveteau extends Cartes_animaux{

    public Louveteau() { super("Louveteau", 1, 1, 1, 0, false, new ArrayList<>(List.of(PowerEnum.CROISSANCE))); }
}
