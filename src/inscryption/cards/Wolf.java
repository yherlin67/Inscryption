package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Wolf extends AnimalsCards {
    public Wolf() { super("Loup", 3, 2, 2, 0, false, new ArrayList<>(List.of(PowerEnum.NONE))); }

    @Override
    public String toString(){
        return "Loup("+this.getName()+")";
    }

    public Wolf(Wolf target) {
        super(target);
    }

    // L'implémentation finale du clone !
    @Override
    public Wolf clone() {
        return new Wolf(this); // "this" représente le Loup actuel à cloner
    }
}
