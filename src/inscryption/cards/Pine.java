package inscryption.cards;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;

import java.util.ArrayList;

public class Pine extends Card {

    public Pine() {super("Sapin",3);}

    @Override
    public String toString(){
        return "Sapin("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}

    public Pine(Pine target) { super(target); }
    @Override public Pine clone() { return new Pine(this); }

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
    //Un duel d'obstacles ne renvoie rien !
    public void duel(ArrayList<Location> impactedLocations, int degats, int i, int ligneAttaquant, int ligneCible, Card cible)
    {}
}
