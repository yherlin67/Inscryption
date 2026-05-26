package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private ArrayList<Cartes_animaux> m_main;
    private ArrayList<Cartes_animaux> m_gamecards;
    private Random m_random;
    private GameManager m_datas;
    private int m_obtainedBones;

    public Player()
    {
        m_main = new ArrayList<Cartes_animaux>();
        m_gamecards = new ArrayList<Cartes_animaux>();
        m_random = new Random();
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void createDraw()
    {
        ArrayList<Cartes_animaux> temp = new ArrayList<Cartes_animaux>();
        temp.add(new Chat());
        temp.add(new Corbeau());
        temp.add(new Coyote());
        temp.add(new Grizzly());
        temp.add(new Hermine());
        temp.add(new Loup());
        temp.add(new Louveteau());
        temp.add(new Moineau());
        temp.add(new Punaise());
        temp.add(new Elan());
        temp.add(new Vipere());
        temp.add(new Porc_epic());
        while(temp.size() != 7)
        {
            int aleatoireIndex = m_random.nextInt(temp.size());
            temp.remove(temp.get(aleatoireIndex));
        }
        for(int i=0; i<8; i++)
        {
            Ecureuil e = new Ecureuil();
            temp.add(e);
        }
        for(int k=0; k<15; k++)
        {
            int aleatoireIndex = m_random.nextInt(temp.size());
            m_gamecards.add(temp.get(aleatoireIndex));
            temp.remove(aleatoireIndex);
        }
    }

    public void draw()
    {
        Cartes_animaux carte = m_gamecards.removeLast();
        m_main.add(carte);
    }

    public void attack()
    {
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCards()[2][i] != null)
            {
                if(m_datas.getCards()[1][i] == null)
                {
                    if (m_datas.getCards()[2][i].getAnimals() != null)
                    {
                        m_datas.setScore(m_datas.getCards()[2][i].getAnimals().getAttack());
                    }
                }
                else
                {
                    int degats = m_datas.getCards()[2][i].getAnimals().getAttack();

                    if(m_datas.getCards()[1][i].getAnimals() != null)
                    {
                        if(m_datas.getCards()[1][i].getAnimals().getPower() == PowerEnum.PUANT)
                        {
                            degats --;
                        }
                    }
                    if(m_datas.getCards()[2][i].getAnimals() != null && m_datas.getCards()[2][i].getAnimals().isFlying())
                    {
                        m_datas.setScore(degats);
                    }
                    else if(m_datas.getCards()[2][i].getAnimals() != null && !m_datas.getCards()[2][i].getAnimals().isFlying())
                    {
                        m_datas.getCards()[1][i].takeDamage(degats);

                        if(m_datas.getCards()[2][i].getAnimals().getPower() == PowerEnum.CONTACT_MORTEL && m_datas.getCards()[2][i].getAnimals() != null)
                        {
                            m_datas.getCards()[1][i].takeDamage(999);
                        }

                        if(m_datas.getCards()[1][i].getAnimals().getPower() == PowerEnum.PIQUES_POINTUES && m_datas.getCards()[2][i].getAnimals() != null)
                        {
                            m_datas.getCards()[2][i].takeDamage(1);
                        }

                        if(m_datas.getCards()[2][i].getAnimals().getPower() == PowerEnum.COUREUR && m_datas.getCards()[2][i].getAnimals() != null)
                        {
                            if(m_datas.getCards()[2][i].getAnimals() != null)
                            {
                                if(i<3 && m_datas.getCards()[2][i+1] != null && m_datas.getCards()[2][i+1].getAnimals() == null)
                                {
                                    m_datas.getCards()[2][i+1] = m_datas.getCards()[2][i].getAnimals();
                                    m_datas.getCards()[2][i] = null;
                                }
                                else if(i>0 && m_datas.getCards()[2][i-1] != null && m_datas.getCards()[2][i-1].getAnimals() == null)
                                {
                                    m_datas.getCards()[2][i-1] = m_datas.getCards()[2][i].getAnimals();
                                    m_datas.getCards()[2][i] = null;
                                }
                            }
                        }

                        if (m_datas.getCards()[1][i] != null && m_datas.getCards()[1][i].getHealthPoints() <= 0) {
                            m_datas.getCards()[1][i] = null;
                        }
                    }
                }
            }
        }
    }

    public Cartes removeCard(int indHand)
    {
        return m_main.remove(indHand);
    }

    public ArrayList<Cartes_animaux> getHand(){return m_main;}

    public ArrayList<Cartes_animaux> getDraw(){return m_gamecards;}

    public int getPlayerBones() { return m_obtainedBones; }

    public void setPlayerBones(int nb) {m_obtainedBones = nb;}

    public void increaseBones() { m_obtainedBones ++; }

}
