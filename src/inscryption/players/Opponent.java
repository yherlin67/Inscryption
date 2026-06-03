package inscryption.players;

import inscryption.AttackResult;
import inscryption.GameManager;
import inscryption.Location;
import inscryption.PowerEnum;
import inscryption.cards.*;

import java.util.ArrayList;

public class Opponent {
    private final ArrayList<AnimalsCards>[] m_actions;
    private int m_turnAttack;

    public Opponent()
    {
        m_actions = new ArrayList[]{
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        };

        m_turnAttack = 0;
    }

    public Opponent(Opponent other)
    {
        m_actions = new ArrayList[other.m_actions.length];
        for(int i = 0; i < other.m_actions.length; i++)
        {
            m_actions[i] = new ArrayList<>();
            for(AnimalsCards card : (ArrayList<AnimalsCards>) other.m_actions[i])
            {
                m_actions[i].add(card != null ? card : null);
            }
        }
        m_turnAttack = other.m_turnAttack;
    }

    public void setMatch(ArrayList<AnimalsCards> actions0, ArrayList<AnimalsCards> actions1, ArrayList<AnimalsCards> actions2)
    {
        m_actions[0] = actions0;
        m_actions[1] = actions1;
        m_actions[2] = actions2;
    }

    public AttackResult play(Cards[][] gameboard)
    {
        // Notre liste d'impacts pour le GameManager
        ArrayList<Location> impactedLocations = new ArrayList<>();

        for(int i=0; i<3; i++)
        {
            if(gameboard[1][i] == null && gameboard[0][i] != null)
            {
                impactedLocations.add(new Location(1, i, gameboard[0][i])); // La carte arrive en ligne 1
                impactedLocations.add(new Location(0, i, (Cards) null));    // La ligne 0 devient vide

                gameboard[1][i] = gameboard[0][i];
                gameboard[0][i] = null;
            }

            if (gameboard[0][i] == null && !m_actions[i].isEmpty())
            {
                Cards nouvelleCarte = m_actions[i].getFirst();
             //   m_actions[i].removeFirst();

                impactedLocations.add(new Location(0, i, nouvelleCarte));

                gameboard[0][i] = nouvelleCarte;
            }
        }
        return new AttackResult(0, impactedLocations);
    }

    public int getTurnAttack(){
        return m_turnAttack;
    }

    public AttackResult attack(Cards[][] gameboard)
    {
        ArrayList<Location> impactedLocations = new ArrayList<>();
        int score = 0;
        m_turnAttack = 0;

        for(int i=0; i<4; i++)
        {
            if(gameboard[1][i] != null)
            {
                if(gameboard[2][i] == null)
                {
                    if(gameboard[1][i].isAnimal())
                    {
                        score -= gameboard[1][i].getAnimalAttack();
                        m_turnAttack += gameboard[1][i].getAnimalAttack();
                    }
                }
                else
                {
                    int degats = 0;
                    if(gameboard[1][i].isAnimal())
                    {
                        degats = gameboard[1][i].getAnimalAttack();
                    }
                    if(gameboard[2][i] != null && gameboard[2][i].isAnimal())
                    {
                        if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.PUANT || gameboard[2][i].getLastPowerAnimal() == PowerEnum.PUANT)
                        {
                            m_turnAttack--;
                            degats--;
                        }
                    }
                    if(gameboard[1][i].isAnimal() && gameboard[1][i].getAnimalFly())
                    {
                        score -= degats;
                        m_turnAttack += gameboard[1][i].getAnimalAttack();
                    }
                    else if(gameboard[1][i].isAnimal() && !gameboard[1][i].getAnimalFly())
                    {
                        impactedLocations.add(new Location(2, i, degats));
                        gameboard[2][i].takeDamage(degats);

                        if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.CONTACT_MORTEL || gameboard[1][i].getLastPowerAnimal() == PowerEnum.CONTACT_MORTEL)
                        {
                            impactedLocations.add(new Location(2, i, 999));
                            gameboard[2][i].takeDamage(999);
                        }

                        if(gameboard[2][i] != null && gameboard[2][i].isAnimal())
                        {
                            if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.PIQUES_POINTUES || gameboard[2][i].getLastPowerAnimal() == PowerEnum.PIQUES_POINTUES)
                            {
                                impactedLocations.add(new Location(1, i, 1));
                                gameboard[1][i].takeDamage(1);

                                if(gameboard[1][i].getHealthPoints() <= 0)
                                {
                                    impactedLocations.add(new Location(1, i, (Cards) null));
                                    gameboard[1][i] = null;
                                }
                            }
                        }

                        if(gameboard[2][i] != null && gameboard[2][i].getHealthPoints() <= 0)
                        {
                            impactedLocations.add(new Location(2, i, (Cards) null));
                            gameboard[2][i] = null;
                        }
                    }
                }

                if(gameboard[1][i] != null && gameboard[1][i].isAnimal() && (gameboard[1][i].getFirstPowerAnimal() == PowerEnum.CROISSANCE || gameboard[1][i].getLastPowerAnimal() == PowerEnum.CROISSANCE))
                {
                    Loup nouveauLoup = new Loup();
                    impactedLocations.add(new Location(1, i, nouveauLoup));
                    gameboard[1][i] = nouveauLoup;
                }
            }
        }

        ArrayList<Integer> indicesBloques = new ArrayList<>();

        for(int j=0; j<4; j++)
        {
            if(indicesBloques.contains(j)) {
                continue;
            }

            if(gameboard[1][j] != null && gameboard[1][j].isAnimal())
            {
                if(gameboard[1][j].getFirstPowerAnimal() == PowerEnum.COUREUR || gameboard[1][j].getLastPowerAnimal() == PowerEnum.COUREUR)
                {
                    if(j < 3 && gameboard[1][j+1] == null)
                    {
                        impactedLocations.add(new Location(1, j, (Cards) null));
                        impactedLocations.add(new Location(1, j+1, gameboard[1][j]));

                        gameboard[1][j+1] = gameboard[1][j];
                        gameboard[1][j] = null;
                        indicesBloques.add(j+1);
                    }
                    else if(j > 0 && gameboard[1][j-1] == null)
                    {
                        impactedLocations.add(new Location(1, j, (Cards) null));
                        impactedLocations.add(new Location(1, j-1, gameboard[1][j]));

                        gameboard[1][j-1] = gameboard[1][j];
                        gameboard[1][j] = null;
                    }
                }
            }
        }

        return new AttackResult(score, impactedLocations);
    }

    @Override
    public String toString(){
        return "Opponent";
    }
}