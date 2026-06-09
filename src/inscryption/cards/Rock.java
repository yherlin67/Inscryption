package inscryption.cards;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;

import java.util.ArrayList;

public class Rock extends Card {

    public Rock() {super("Rocher",5);}

    @Override
    public String toString(){
        return "Rocher("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}

    public Rock(Rock target) { super(target); }
    @Override public Rock clone() { return new Rock(this); }

    @Override
    public int getAnimalAttack() {
        return 0;
    }

    @Override
    public boolean getAnimalFly() {
        return false;
    }

    @Override
    public int getPowerSizeAnimal() {
        return 0;
    }

    @Override
    public PowerEnum getFirstPowerAnimal() {
        return PowerEnum.NONE;
    }

    @Override
    public PowerEnum getLastPowerAnimal() {
        return PowerEnum.NONE;
    }

    @Override
    public void duel(ArrayList<Location> impactedLocations, int degats, int i, int ligneAttaquant, int ligneCible, Card cible)
    {}
}
