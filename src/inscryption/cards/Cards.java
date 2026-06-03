package inscryption.cards;

import inscryption.PowerEnum;

public abstract class Cards {
    private String m_name;
    private int m_health_points;
    private AnimalsCards m_animals;

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

    // 2. Méthode abstraite pour forcer les enfants à se cloner
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

    public abstract boolean isAnimal();

    public int getAnimalAttack() {
        return m_animals != null ? m_animals.getAttack() : 0;
    }

    public boolean getAnimalFly() {
        return m_animals != null && m_animals.isFlying();
    }

    public int getPowerSizeAnimal() {
        return m_animals != null ? m_animals.getPowerSizeAnimal() : 0;
    }

    public PowerEnum getFirstPowerAnimal() {
        return m_animals != null ? m_animals.getFirstPower() : PowerEnum.AUCUN;
    }

    public PowerEnum getLastPowerAnimal() {
        return m_animals != null ? m_animals.getLastPower() : PowerEnum.AUCUN;
    }
}