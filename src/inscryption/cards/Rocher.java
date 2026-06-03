package inscryption.cards;

import inscryption.PowerEnum;

public class Rocher extends Cards {

    public Rocher() {super("Rocher",5);}

    @Override
    public String toString(){
        return "Rocher("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}

    public Rocher(Rocher target) { super(target); }
    @Override public Rocher clone() { return new Rocher(this); }

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
        return PowerEnum.AUCUN;
    }

    @Override
    public PowerEnum getLastPowerAnimal() {
        return PowerEnum.AUCUN;
    }
}
