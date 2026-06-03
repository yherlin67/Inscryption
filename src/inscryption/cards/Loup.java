package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Loup extends AnimalsCards {
    public Loup() { super("Loup", 3, 2, 2, 0, false, new ArrayList<>(List.of(PowerEnum.AUCUN))); }

    @Override
    public String toString(){
        return "Loup("+this.getName()+")";
    }

    public Loup(Loup target) {
        super(target);
    }

    // L'implémentation finale du clone !
    @Override
    public Loup clone() {
        return new Loup(this); // "this" représente le Loup actuel à cloner
    }
}
