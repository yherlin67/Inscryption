package inscryption.cards;

import inscryption.PowerEnum;

public abstract class Cards {
    private final String m_name;
    private int m_health_points;
    private Animals_cards m_animals;

    public Cards(String nom, int pdv)
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

    public void setAnimals(Animals_cards animaux) {
        this.m_animals = animaux;
    }

    public PowerEnum getFirstPowerAnimal()
    {
        return m_animals.getFirstPower();
    }

    public PowerEnum getLastPowerAnimal()
    {
        return m_animals.getLastPower();
    }

    public int getPowerSizeAnimal()
    {
        return m_animals.getPowerSizeAnimal();
    }

    public boolean isAnimal() {return m_animals != null;}

    public int getAnimalAttack() {return m_animals.getAttack();}

    public boolean getAnimalFly() {return m_animals.isFlying();}
}