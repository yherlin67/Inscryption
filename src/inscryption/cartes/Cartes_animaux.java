package inscryption.cartes;

public abstract class Cartes_animaux extends Cartes{

    private final int m_attack;

    private final int m_blood;
    private final int m_bone;
    private final boolean m_flying;
    private Power m_power;

    public Cartes_animaux(String nom, int att, int pdv, int gds, int os, boolean vol)
    {
        super(nom,pdv);
        this.setAnimals(this);
        m_attack = att;
        m_blood = gds;
        m_bone = os;
        m_flying = vol;
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

    public void appliquerPouvoir()
    {
        //switch
    }
}
