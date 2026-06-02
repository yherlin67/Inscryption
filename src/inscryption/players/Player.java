package inscryption.players;

import inscryption.GameManager;
import inscryption.PowerEnum;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private final ArrayList<Cartes_animaux> m_hand;
    private final ArrayList<Cartes_animaux> m_gamecards;
    private final ArrayList<Cartes_animaux> m_gamecards_copy;
    private final Random m_random;
    private GameManager m_datas;
    private int m_obtainedBones;
    private int m_turnAttack;

    public Player()
    {
        m_hand = new ArrayList<>();
        m_gamecards_copy = new ArrayList<>();
        m_gamecards = new ArrayList<>();
        m_random = new Random();
        m_turnAttack = 0;
        this.createDraw();
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void createDraw()
    {
        ArrayList<Cartes_animaux> temp = new ArrayList<>();
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
        m_hand.add(carte);
    }

    public void setGamecards(){
        m_hand.clear();
        m_gamecards.clear();
        ArrayList<Cartes_animaux> tempDeck = new ArrayList<>(m_gamecards_copy);
        while (!tempDeck.isEmpty())
        {
            int aleatoireIndex = m_random.nextInt(tempDeck.size());
            m_gamecards.add(tempDeck.remove(aleatoireIndex));
        }
    }


    public int getDrawSize()
    {
        return m_gamecards.size();
    }

    public Cartes_animaux getAnimalAtInDraw(int index)
    {
        return m_gamecards.get(index);
    }

    public void removeAtInDraw(int index)
    {
        m_gamecards.remove(index);
    }

    public void addInDraw(Cartes_animaux animal)
    {
        m_gamecards.add(animal);
    }


    public int getTurnAttack(){
        return m_turnAttack;
    }


    public void attack()
    {
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(m_datas.isCard(2,i))
            {
                if(!m_datas.isCard(1,i))
                {
                    if(m_datas.isCardAnimal(2,i))
                    {
                        m_datas.setScore(m_datas.getCardAttack(2,i));
                        m_turnAttack += m_datas.getCardAttack(2,i);
                    }
                }
                else
                {
                    int degats = 0;
                    if(m_datas.isCardAnimal(2,i))
                    {
                        degats = m_datas.getCardAttack(2,i);
                    }
                    if(m_datas.isCardAnimal(1,i))
                    {
                        if(m_datas.getCardPowerFirst(1,i) == PowerEnum.PUANT || m_datas.getCardPowerLast(1,i) == PowerEnum.PUANT)
                        {
                            m_turnAttack--;
                            degats--;
                        }
                    }
                    if(m_datas.isCardAnimal(2,i) && m_datas.getCardFly(2,i))
                    {
                        m_datas.setScore(degats);
                        m_turnAttack += m_datas.getCardAttack(2,i);
                    }
                    else if(m_datas.isCardAnimal(2,i) && !m_datas.getCardFly(2,i))
                    {
                        m_datas.cardTakeDamage(1,i,degats);

                        if(m_datas.getCardPowerFirst(2,i) == PowerEnum.CONTACT_MORTEL || m_datas.getCardPowerLast(2,i) == PowerEnum.CONTACT_MORTEL)
                        {
                            m_datas.cardTakeDamage(1,i,999);
                        }

                        if(m_datas.isCard(1,i) && m_datas.isCardAnimal(1,i))
                        {
                            if(m_datas.getCardPowerFirst(1,i) == PowerEnum.PIQUES_POINTUES || m_datas.getCardPowerLast(1,i) == PowerEnum.PIQUES_POINTUES)
                            {
                                m_datas.cardTakeDamage(2,i,1);

                                if(m_datas.getCardHealthPoints(2,i) <= 0)
                                {
                                    m_datas.setCard(null, 2, i);
                                }
                            }
                        }

                        if(m_datas.isCard(1,i) && m_datas.getCardHealthPoints(1,i) <= 0)
                        {
                            m_datas.setCard(null, 1, i);
                        }
                    }
                }
                if(m_datas.isCard(2,i) && m_datas.isCardAnimal(2,i) && (m_datas.getCardPowerFirst(2,i) == PowerEnum.CROISSANCE || m_datas.getCardPowerLast(2,i) == PowerEnum.CROISSANCE))
                {
                    m_datas.setCard(new Loup(), 2, i);
                }
            }
        }

        ArrayList<Integer> indicesBloques = new ArrayList<>();

        for(int j=0; j<4; j++)
        {
            if(indicesBloques.contains(j)) {
                continue;
            }

            if(m_datas.isCardAnimal(2,j))
            {
                if(m_datas.getCardPowerFirst(2,j) == PowerEnum.COUREUR || m_datas.getCardPowerLast(2,j) == PowerEnum.COUREUR)
                {
                    if(j < 3 && !m_datas.isCard(2,j+1))
                    {
                        m_datas.moveCard(2, j, j+1);
                        indicesBloques.add(j+1);
                    }
                    else if(j > 0 && !m_datas.isCard(2,j-1))
                    {
                        m_datas.moveCard(2, j, j-1);
                    }
                }
            }
        }
    }

    public Cartes removeCard(int indHand) {return m_hand.remove(indHand);}

    public int getHandSize()
    {
        return m_hand.size();
    }

    public boolean isDrawEmpty() {return m_gamecards.isEmpty();}

    public int getBones() { return m_obtainedBones; }

    public int getBloodAt(int i) { return m_hand.get(i).getBlood(); }

    public int getBonesAt(int i) { return m_hand.get(i).getBone(); }

    public void setBones(int nb) {m_obtainedBones = nb;}

    public void increaseBones() { m_obtainedBones ++; }

    public PowerEnum getCardPowerFirst(int i){return m_gamecards.get(i).getFirstPowerAnimal();}

    public String getCardNameInHand(int i){return m_hand.get(i).getName();}

    public int getCardAttackInHand(int i){return m_hand.get(i).getAttack();}

    public int getCardHealthPointInHand(int i){return m_hand.get(i).getHealthPoints();}

    public int getCardBloodInHand(int i){return m_hand.get(i).getBlood();}

    public int getCardBonesInHand(int i){return m_hand.get(i).getBone();}

    public PowerEnum getCardFirstPowerInHand(int i){return m_hand.get(i).getFirstPower();}

    public PowerEnum getCardLastPowerInHand(int i){return m_hand.get(i).getLastPower();}

    @Override
    public String toString(){
        return "Player";
    }

}
