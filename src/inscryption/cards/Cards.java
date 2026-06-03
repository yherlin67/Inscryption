package inscryption.cards;

import inscryption.PowerEnum;

public abstract class Cards {
    private String m_name;
    private int m_health_points;
    // On a supprimé 'private AnimalsCards m_animals;' !

    public Cards(String nom, int pdv)
    {
        m_name = nom;
        m_health_points = pdv;
    }

    protected Cards(Cards target) {
        if (target != null) {
            this.m_health_points = target.m_health_points;
            this.m_name = target.m_name;
        }
    }

    // Méthode abstraite pour forcer les enfants à se cloner
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

    // --- On utilise le casting (instanceof) sur "this" au lieu d'une variable vide ---

    public int getAnimalAttack() {
        if (this instanceof AnimalsCards) {
            return ((AnimalsCards) this).getAttack();
        }
        return 0;
    }

    public boolean getAnimalFly() {
        if (this instanceof AnimalsCards) {
            return ((AnimalsCards) this).isFlying();
        }
        return false;
    }

    public int getPowerSizeAnimal() {
        if (this instanceof AnimalsCards) {
            return ((AnimalsCards) this).getPowerSizeAnimal();
        }
        return 0;
    }

    public PowerEnum getFirstPowerAnimal() {
        if (this instanceof AnimalsCards) {
            return ((AnimalsCards) this).getFirstPower();
        }
        return PowerEnum.AUCUN;
    }

    public PowerEnum getLastPowerAnimal() {
        if (this instanceof AnimalsCards) {
            return ((AnimalsCards) this).getLastPower();
        }
        return PowerEnum.AUCUN;
    }
}