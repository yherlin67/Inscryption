package inscryption.cards;

import inscryption.logic.PowerEnum;

import java.util.ArrayList;
import java.util.List;

public class Viper extends AnimalsCards {

    public Viper() {
        super("Vipère", 1, 1, 2, 0, false, new ArrayList<>(List.of(PowerEnum.DEATH_TOUCH))); }

    @Override
    public String toString(){
        return "Vipere("+this.getName()+")";
    }

    public Viper(Viper target) { super(target); }
    @Override public Viper clone() { return new Viper(this); }
}
