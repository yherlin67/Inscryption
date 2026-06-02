package inscryption.players;

import inscryption.GameManager;
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

    public void play(Cards[][] gameboard)
    {
        for(int i=0; i<4; i++)
        {
            if(gameboard[1][i] == null)
            {
                gameboard[1][i] = gameboard[0][i];
                gameboard[0][i] = null;
            }
            if (gameboard[0][i] == null && !m_actions[i].isEmpty())
            {
                gameboard[0][i] = m_actions[i].getFirst();
                m_actions[i].removeFirst();
            }
        }
    }

    public int getTurnAttack(){
        return m_turnAttack;
    }

    public int attack(Cards[][] gameboard)
    {
        //Renvoie le score à ajouter à celui existant dans le gameManager
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
                        gameboard[2][i].takeDamage(degats);

                        if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.CONTACT_MORTEL || gameboard[1][i].getLastPowerAnimal() == PowerEnum.CONTACT_MORTEL)
                        {
                            gameboard[2][i].takeDamage(999);
                        }

                        if(gameboard[2][i] != null && gameboard[2][i].isAnimal())
                        {
                            if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.PIQUES_POINTUES || gameboard[2][i].getLastPowerAnimal() == PowerEnum.PIQUES_POINTUES)
                            {
                                gameboard[1][i].takeDamage(1);

                                if(gameboard[1][i].getHealthPoints() <= 0)
                                {
                                    gameboard[1][i] = null;
                                }
                            }
                        }

                        if(gameboard[2][i] != null && gameboard[2][i].getHealthPoints() <= 0)
                        {
                            gameboard[2][i] = null;
                        }
                    }
                }

                if(gameboard[1][i] != null && gameboard[1][i].isAnimal() && (gameboard[1][i].getFirstPowerAnimal() == PowerEnum.CROISSANCE || gameboard[1][i].getLastPowerAnimal() == PowerEnum.CROISSANCE))
                {
                    gameboard[1][i] = new Loup();
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
                        gameboard[1][j+1] = gameboard[1][j];
                        gameboard[1][j] = null;
                        indicesBloques.add(j+1);
                    }
                    else if(j > 0 && gameboard[1][j-1] == null)
                    {
                        gameboard[1][j-1] = gameboard[1][j];
                        gameboard[1][j] = null;
                    }
                }
            }
        }

        return score;
    }

    @Override
    public String toString(){
        return "Opponent";
    }
}