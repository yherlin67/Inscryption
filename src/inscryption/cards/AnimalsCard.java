package inscryption.cards;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;
import java.util.ArrayList;

public abstract class AnimalsCard extends Card {

    private int m_attack;
    private int m_blood;
    private int m_bone;
    private boolean m_flying;
    private ArrayList<PowerEnum> m_powerEnum;

    public AnimalsCard(String nom, int att, int pdv, int gds, int os, boolean vol, ArrayList<PowerEnum> powerEnum) {
        super(nom, pdv);
        m_attack = att;
        m_blood = gds;
        m_bone = os;
        m_flying = vol;
        m_powerEnum = powerEnum;
    }

    protected AnimalsCard(AnimalsCard target) {
        super(target);
        if (target != null) {
            this.m_attack = target.m_attack;
            this.m_powerEnum = target.m_powerEnum;
            this.m_blood = target.m_blood;
            this.m_bone = target.m_bone;
            this.m_flying = target.m_flying;
        }
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    @Override
    public int getAnimalAttack() {
        return m_attack;
    }

    @Override
    public boolean getAnimalFly() {
        return m_flying;
    }

    @Override
    public int getPowerSizeAnimal() {
        return m_powerEnum.size();
    }

    @Override
    public PowerEnum getFirstPowerAnimal() {
        return m_powerEnum.getFirst();
    }

    @Override
    public PowerEnum getLastPowerAnimal() {
        return m_powerEnum.getLast();
    }

    public int getBlood() {
        return m_blood;
    }

    public int getBone() {
        return m_bone;
    }

    public void addPower(PowerEnum power) {
        m_powerEnum.add(power);
    }

    @Override
    public void duel(ArrayList<Location> impactedLocations, int degats, int i, int ligneAttaquant, int ligneCible, Card cible)
    {
        impactedLocations.add(new Location(ligneCible, i, degats));
        cible.takeDamage(degats);

        if(this.getFirstPowerAnimal() == PowerEnum.DEATH_TOUCH || this.getLastPowerAnimal() == PowerEnum.DEATH_TOUCH)
        {
            impactedLocations.add(new Location(ligneCible, i, 999));
            cible.takeDamage(999);
        }

        if(cible.isAnimal())
        {
            if(cible.getFirstPowerAnimal() == PowerEnum.SHARP_SPIKES || cible.getLastPowerAnimal() == PowerEnum.SHARP_SPIKES)
            {
                this.takeDamage(1);
                impactedLocations.add(new Location(ligneAttaquant, i, 1));

                if(this.getHealthPoints() <= 0)
                {
                    impactedLocations.add(new Location(ligneAttaquant, i, (Card) null));
                }
            }
        }

        if(cible.getHealthPoints() <= 0)
        {
            impactedLocations.add(new Location(ligneCible, i, (Card) null));
        }
    }

}