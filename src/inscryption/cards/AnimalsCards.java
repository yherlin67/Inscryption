package inscryption.cards;

import inscryption.PowerEnum;

import java.util.ArrayList;


public abstract class AnimalsCards extends Cards {

    private int m_attack;
    private int m_blood;
    private int m_bone;
    private boolean m_flying;
    private ArrayList<PowerEnum> m_powerEnum;

    public AnimalsCards(String nom, int att, int pdv, int gds, int os, boolean vol, ArrayList<PowerEnum> powerEnum)
    {
        super(nom,pdv);
        m_attack = att;
        m_blood = gds;
        m_bone = os;
        m_flying = vol;
        m_powerEnum = powerEnum;
    }

    // Constructeur de copie pour les attributs d'animaux
    protected AnimalsCards(AnimalsCards target) {
        super(target); // Appelle la copie de Cards (les PV)
        if (target != null) {
            this.m_attack = target.m_attack;
            this.m_powerEnum = target.m_powerEnum;
            this.m_blood = target.m_blood;
            this.m_bone = target.m_bone;
            this.m_flying = target.m_flying;
        }
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
