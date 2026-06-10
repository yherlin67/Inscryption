package inscryption.cards;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;

import java.util.ArrayList;

public abstract class Card {
    private String m_name;
    private int m_healthPoints;

    public Card(String name, int healthPoints)
    {
        m_name = name;
        m_healthPoints = healthPoints;
    }

    //Constructeur de copie appelé dans la méthode clone qui est implémentée dans chaque classe animal
    protected Card(Card target) {
        if (target != null) {
            this.m_healthPoints = target.m_healthPoints;
            this.m_name = target.m_name;
        }
    }

    public abstract Card clone();

    public String getName() {
        return m_name;
    }

    public int getHealthPoints() {
        return m_healthPoints;
    }


    public void takeDamage(int damage) {
        // Si les dégâts risquent de faire tomber les PV sous 0, on force directement à 0
        if (damage >= this.m_healthPoints) {
            this.m_healthPoints = 0;
        } else {
            this.m_healthPoints -= damage;
        }
    }

    public abstract boolean isAnimal();

    public abstract int getAnimalAttack();

    public abstract boolean getAnimalFly();

    public abstract int getPowerSizeAnimal();

    public abstract PowerEnum getFirstPowerAnimal();

    public abstract PowerEnum getLastPowerAnimal();

    public abstract void duel(ArrayList<Location> impactedLocations, int damages, int i, int attackLine, int aimLine, Card aim);
}