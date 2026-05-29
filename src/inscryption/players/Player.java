package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private ArrayList<Cartes_animaux> m_main;
    private ArrayList<Cartes_animaux> m_gamecards;
    private ArrayList<Cartes_animaux> m_gamecards_copy;
    private Random m_random;
    private GameManager m_datas;
    private int m_obtainedBones;
    private int m_turnAttack;

    public Player()
    {
        m_main = new ArrayList<Cartes_animaux>();
        m_gamecards_copy = new ArrayList<Cartes_animaux>();
        m_gamecards = m_gamecards_copy;
        m_random = new Random();
        m_turnAttack = 0;
        this.createDraw();
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
            m_gamecards_copy.add(temp.get(aleatoireIndex));
            temp.remove(aleatoireIndex);
        }
    }

    public void draw()
    {
        Cartes_animaux carte = m_gamecards.removeLast();
        m_main.add(carte);
    }

    public void setGamecards(){m_gamecards = m_gamecards_copy;}

    public void drawAt(int index)
    {
        m_gamecards.remove(index);
    }

    public int getDrawSize()
    {
        return m_gamecards.size();
    }

    public void setAnimalAtInDraw(int index, Cartes_animaux animal)
    {
        m_gamecards.set(index, animal);
    }

    public Cartes_animaux getAnimalAtInDraw(int index)
    {
        return m_gamecards.get(index);
    }

    public void removeAtInDraw(int index)
    {
        m_gamecards.remove(index);
    }


    public int getTurnAttack(){
        return m_turnAttack;
    }


    public void attack()
    {
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCards()[2][i] != null)
            {
                if(m_datas.getCards()[1][i] == null)
                {
                    if (m_datas.getCards()[2][i].getAnimals() != null)
                    {
                        m_datas.setScore(m_datas.getCards()[2][i].getAnimals().getAttack());
                        m_turnAttack += m_datas.getCards()[2][i].getAnimals().getAttack();
                    }
                }
                else
                {
                    int degats =0;
                    if(m_datas.getCards()[2][i].getAnimals() != null)
                    {
                        degats = m_datas.getCards()[2][i].getAnimals().getAttack();
                    }
                    if(m_datas.getCards()[1][i].getAnimals() != null)
                    {
                        if(m_datas.getCards()[1][i].getAnimals().getFirstPower() == PowerEnum.PUANT || m_datas.getCards()[1][i].getAnimals().getLastPower() == PowerEnum.PUANT)
                        {
                            m_turnAttack--;
                            degats --;
                        }
                    }
                    if(m_datas.getCards()[2][i].getAnimals() != null && m_datas.getCards()[2][i].getAnimals().isFlying())
                    {
                        m_datas.setScore(degats);
                        m_turnAttack += m_datas.getCards()[2][i].getAnimals().getAttack();
                    }
                    else if(m_datas.getCards()[2][i].getAnimals() != null && !m_datas.getCards()[2][i].getAnimals().isFlying())
                    {
                        m_datas.getCards()[1][i].takeDamage(degats);

                        if(m_datas.getCards()[2][i].getAnimals().getFirstPower() == PowerEnum.CONTACT_MORTEL || m_datas.getCards()[2][i].getAnimals().getLastPower() == PowerEnum.CONTACT_MORTEL)
                        {
                            m_datas.getCards()[1][i].takeDamage(999);
                        }

                        if(m_datas.getCards()[1][i] != null && m_datas.getCards()[1][i].getAnimals() != null) {
                            if(m_datas.getCards()[1][i].getAnimals().getFirstPower() == PowerEnum.PIQUES_POINTUES || m_datas.getCards()[1][i].getAnimals().getLastPower() == PowerEnum.PIQUES_POINTUES)
                            {
                                m_datas.getCards()[2][i].takeDamage(1);

                                if (m_datas.getCards()[2][i].getHealthPoints() <= 0) {
                                    m_datas.getCards()[2][i] = null;
                                }
                            }
                        }

                        if (m_datas.getCards()[1][i] != null && m_datas.getCards()[1][i].getHealthPoints() <= 0) {
                            m_datas.getCards()[1][i] = null;
                        }
                    }
                }
                if(m_datas.getCards()[2][i] != null && m_datas.getCards()[2][i].getAnimals() != null && (m_datas.getCards()[2][i].getAnimals().getFirstPower() == PowerEnum.CROISSANCE || m_datas.getCards()[2][i].getAnimals().getLastPower() == PowerEnum.CROISSANCE))
                {
                    m_datas.setCard(new Loup(), 2, i);
                }
            }
        }
        ArrayList<Integer> indicesBloques = new ArrayList<>();

        for(int i=0; i<4; i++)
        {
            if(indicesBloques.contains(i)) {
                continue;
            }

            Cartes carteActuelle = m_datas.getCards()[2][i];

            if(carteActuelle != null && carteActuelle.getAnimals() != null)
            {
                if(carteActuelle.getAnimals().getFirstPower() == PowerEnum.COUREUR || carteActuelle.getAnimals().getLastPower() == PowerEnum.COUREUR)
                {
                    if(i < 3 && m_datas.getCards()[2][i+1] == null)
                    {
                        m_datas.setCard(carteActuelle, 2, i+1);
                        m_datas.setCard(null, 2, i);
                        indicesBloques.add(i+1);
                    }
                    else if(i > 0 && m_datas.getCards()[2][i-1] == null)
                    {
                        m_datas.setCard(carteActuelle, 2, i-1);
                        m_datas.setCard(null, 2, i);
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
