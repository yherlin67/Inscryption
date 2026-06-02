package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;


public abstract class AnimalsCards extends Cards {

    private final int m_attack;
    private final int m_blood;
    private final int m_bone;
    private final boolean m_flying;
    private final ArrayList<PowerEnum> m_powerEnum;

    public AnimalsCards(String nom, int att, int pdv, int gds, int os, boolean vol, ArrayList<PowerEnum> powerEnum)
    {
        super(nom,pdv);
        this.setAnimals(this);
        m_attack = att;
        m_blood = gds;
        m_bone = os;
        m_flying = vol;
        m_powerEnum = powerEnum;
    }

    public int getAttack() {
        return m_attack;
    }

    public int getBlood() {
        return m_blood;
    }

    public int getBone() {
        return m_bone;
    }

    public boolean isFlying() {
        return m_flying;
    }

    public PowerEnum getPowerAt(int index)
    {
        return m_powerEnum.get(index);
    }

    public void addPower(PowerEnum power)
    {
        m_powerEnum.add(power);
    }

    public PowerEnum getLastPower()
    {
        return m_powerEnum.getLast();
    }

    public PowerEnum getFirstPower()
    {
        return m_powerEnum.getFirst();
    }

    public int getPowerSizeAnimal()
    {
        return m_powerEnum.size();
    }

    public boolean isAnimal() {return true;}

}
