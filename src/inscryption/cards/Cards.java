package inscryption.cards;

import inscryption.PowerEnum;

public abstract class Cards {
    private String m_name;
    private int m_health_points;

    public Cards(String nom, int pdv)
    {
        m_name = nom;
        m_health_points = pdv;
    }

    protected Cards(Cards target) {
        if (target != null) {
            this.m_health_points = target.m_health_points;
        }
    }

    public abstract Cards clone();

    public String getName() {
        return m_name;
    }

    public int getHealthPoints() {
        return m_health_points;
    }

    public void takeDamage(int degats) {
        this.m_health_points -= degats;
    }

    // --- COMPORTEMENT PAR DÉFAUT (Pour un rocher, un sapin, etc.) ---

    public boolean isAnimal() {
        return false; // Par défaut, une carte n'est pas un animal
    }

    public int getAnimalAttack() {
        return 0; // Par défaut, pas d'attaque
    }

    public boolean getAnimalFly() {
        return false; // Par défaut, ne vole pas
    }

    public int getPowerSizeAnimal() {
        return 0; // Par défaut, 0 pouvoir
    }

    public PowerEnum getFirstPowerAnimal() {
        return PowerEnum.AUCUN;
    }

    public PowerEnum getLastPowerAnimal() {
        return PowerEnum.AUCUN;
    }
}