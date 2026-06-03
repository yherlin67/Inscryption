package inscryption.cards;

import inscryption.PowerEnum;

public class Sapin extends Cards {

    public Sapin() {super("Sapin",3);}

    @Override
    public String toString(){
        return "Sapin("+this.getName()+")";
    }

    public boolean isAnimal() {return false;}

    public Sapin(Sapin target) { super(target); }
    @Override public Sapin clone() { return new Sapin(this); }

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
