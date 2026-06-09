package inscryption.cards;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;

import java.util.ArrayList;

public abstract class Card {
    private String m_name;
    private int m_health_points;

    public Card(String nom, int pdv)
    {
        m_name = nom;
        m_health_points = pdv;
    }

    protected Card(Card target) {
        if (target != null) {
            this.m_health_points = target.m_health_points;
            this.m_name = target.m_name;
        }
    }

    public abstract Card clone();

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

    public abstract int getAnimalAttack();

    public abstract boolean getAnimalFly();

    public abstract int getPowerSizeAnimal();

    public abstract PowerEnum getFirstPowerAnimal();

    public abstract PowerEnum getLastPowerAnimal();

    public abstract void duel(ArrayList<Location> impactedLocations, int degats, int i, int ligneAttaquant, int ligneCible, Card cible);
}