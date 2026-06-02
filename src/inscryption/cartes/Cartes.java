package inscryption.cartes;

import inscryption.PowerEnum;

public abstract class Cartes {
    private final String m_name;
    private int m_health_points;
    private Cartes_animaux m_animals;

    public Cartes(String nom, int pdv)
    {
        m_name = nom;
        m_health_points = pdv;
    }

    public String getName() {
        return m_name;
    }

    public int getHealthPoints() {
        return m_health_points;
    }

    public void takeDamage(int degats) {
        this.m_health_points -= degats;
    }

    public void setAnimals(Cartes_animaux animaux) {
        this.m_animals = animaux;
    }

    public boolean isAnimal() {
        return m_animals != null;
    }

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