package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Opponent {
    private ArrayList<Cartes>[] m_actions = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    };

    private GameManager m_datas;

    public Opponent()
    {
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void setFirstMatch()
    {
        m_actions[0].addAll(Arrays.asList(new Louveteau(), new Moineau(), null, null));
        m_actions[1].addAll(Arrays.asList(null, null, new Moineau(), null));
        m_actions[2].addAll(Arrays.asList(new Punaise(), null, null, new Punaise()));
        m_actions[3].addAll(Arrays.asList(null, null, new Louveteau(), null));
        m_datas.setCard(m_actions[0].getFirst(), 0, 0);
        m_datas.setCard(m_actions[2].getFirst(), 0, 2);
        m_actions[0].removeFirst();
        m_actions[1].removeFirst();
        m_actions[2].removeFirst();
        m_actions[3].removeFirst();

    }

    public void setSecondMatch()
    {
        m_actions[0].addAll(Arrays.asList(new Loup(), null, new Corbeau(), null));
        m_actions[1].addAll(Arrays.asList(null, new Hermine(), null, new Hermine()));
        m_actions[2].addAll(Arrays.asList(new Coyote(), null, new Loup(), null));
        m_actions[3].addAll(Arrays.asList(null, new Corbeau(), null, new Coyote()));
        m_datas.setCard(m_actions[0].getFirst(), 0, 0);
        m_datas.setCard(m_actions[2].getFirst(), 0, 2);
        m_actions[0].removeFirst();
        m_actions[1].removeFirst();
        m_actions[2].removeFirst();
        m_actions[3].removeFirst();
    }

    public void setThirdMatch()
    {
        m_actions[0].addAll(Arrays.asList(new Grizzly(), null, new Loup(), new Corbeau()));
        m_actions[1].addAll(Arrays.asList(new Loup(), new Grizzly(), null, null));
        m_actions[2].addAll(Arrays.asList(null, new Corbeau(), new Grizzly(), new Loup()));
        m_actions[3].addAll(Arrays.asList(new Corbeau(), null, new Loup(), new Grizzly()));
        m_datas.setCard(m_actions[0].getFirst(), 0, 0);
        m_datas.setCard(m_actions[2].getFirst(), 0, 2);
        m_actions[0].removeFirst();
        m_actions[1].removeFirst();
        m_actions[2].removeFirst();
        m_actions[3].removeFirst();
    }

    public void play()
    {
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCards()[1][i] == null)
            {
                m_datas.setCard(m_datas.getCards()[0][i], 1, i);
                m_datas.setCard(null, 0, i);
            }
            if (m_datas.getCards()[0][i] == null && !m_actions[i].isEmpty())
            {
                m_datas.setCard(m_actions[i].getFirst(), 0, i);
                m_actions[i].removeFirst();
            }
        }
    }


    public void attack()
    {
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCards()[1][i] != null)
            {
                if(m_datas.getCards()[2][i] == null)
                {
                    m_datas.setScore(-m_datas.getCards()[1][i].getAnimals().getAttack());
                }
                else
                {
                    int degats = m_datas.getCards()[1][i].getAnimals().getAttack();
                    if(m_datas.getCards()[2][i].getAnimals() != null)
                    {
                        if(m_datas.getCards()[2][i].getAnimals().getPower() == PowerEnum.PUANT)
                        {
                            degats --;
                        }
                    }
                    if(m_datas.getCards()[1][i].getAnimals() != null && m_datas.getCards()[1][i].getAnimals().isFlying())
                    {
                        m_datas.setScore(degats);
                    }
                    else if(m_datas.getCards()[1][i].getAnimals() != null && !m_datas.getCards()[1][i].getAnimals().isFlying())
                    {
                        m_datas.getCards()[2][i].takeDamage(degats);

                        if(m_datas.getCards()[1][i].getAnimals().getPower() == PowerEnum.CONTACT_MORTEL && m_datas.getCards()[1][i].getAnimals() != null)
                        {
                            m_datas.getCards()[2][i].takeDamage(999);
                        }

                        if(m_datas.getCards()[2][i].getAnimals().getPower() == PowerEnum.PIQUES_POINTUES && m_datas.getCards()[1][i].getAnimals() != null)
                        {
                            m_datas.getCards()[1][i].takeDamage(1);
                        }

                        if(m_datas.getCards()[2][i].getAnimals().getPower() == PowerEnum.COUREUR && m_datas.getCards()[2][i].getAnimals() != null)
                        {
                            if(m_datas.getCards()[2][i].getAnimals() != null)
                            {
                                if(i<3 && m_datas.getCards()[2][i+1] != null)
                                {
                                    m_datas.getCards()[2][i+1] = m_datas.getCards()[2][i].getAnimals();
                                    m_datas.getCards()[2][i] = null;
                                }
                                else if(i>0 && m_datas.getCards()[2][i-1] != null)
                                {
                                    m_datas.getCards()[2][i-1] = m_datas.getCards()[2][i].getAnimals();
                                    m_datas.getCards()[2][i] = null;
                                }
                            }
                        }

                        if (m_datas.getCards()[2][i] != null && m_datas.getCards()[2][i].getHealthPoints() <= 0) {
                            m_datas.getCards()[2][i] = null;
                        }
                    }
                }
            }
        }
    }

}
