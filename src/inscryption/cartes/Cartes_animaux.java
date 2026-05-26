package inscryption.cartes;

import inscryption.PowerEnum;
import inscryption.PowerLogic;

public abstract class Cartes_animaux extends Cartes{

    private final int m_attack;

    private final int m_blood;
    private final int m_bone;
    private final boolean m_flying;
    private PowerEnum m_powerEnum;
    private PowerLogic m_powerLogic;

    public Cartes_animaux(String nom, int att, int pdv, int gds, int os, boolean vol, PowerEnum powerEnum)
    {
        super(nom,pdv);
        this.setAnimals(this);
        m_attack = att;
        m_blood = gds;
        m_bone = os;
        m_flying = vol;
        m_powerEnum = powerEnum;
        m_powerLogic = new PowerLogic();
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

    public void appliquerPouvoir() {
        switch(m_powerEnum) {
            case NOMBREUSES_VIES:
                m_powerEnum.manyLives();
                break;
            case CROISSANCE:
                m_powerEnum.grow();
                break;
            case PUANT:
                m_powerEnum.stinky();
                break;
            case COUREUR:
                m_powerEnum.runner();
            case CONTACT_MORTEL:
                //m_powerLogic.
            case PIQUES_POINTUES:
                //m_
            case AUCUN:
                break;
        }
    }
}
