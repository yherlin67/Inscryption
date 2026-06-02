package inscryption.players;

import inscryption.PowerEnum;
import inscryption.cards.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private final ArrayList<AnimalsCards> m_hand;
    private final ArrayList<AnimalsCards> m_gamecards;
    private final ArrayList<AnimalsCards> m_gamecards_copy;
    private final Random m_random;
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

    public void createDraw()
    {
        ArrayList<AnimalsCards> temp = new ArrayList<>();
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
        AnimalsCards carte = m_gamecards.removeLast();
        m_hand.add(carte);
    }

    public void setGamecards(){
        m_hand.clear();
        m_gamecards.clear();
        ArrayList<AnimalsCards> tempDeck = new ArrayList<>(m_gamecards_copy);
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

    public AnimalsCards getAnimalAtInDraw(int index)
    {
        return m_gamecards.get(index);
    }

    public void removeAtInDraw(int index)
    {
        m_gamecards.remove(index);
    }

    public void addInDraw(AnimalsCards animal)
    {
        m_gamecards.add(animal);
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
            if(gameboard[2][i] != null)
            {
                if(gameboard[1][i] == null)
                {
                    if(gameboard[2][i].isAnimal())
                    {
                        score += gameboard[2][i].getAnimalAttack();
                        m_turnAttack += gameboard[2][i].getAnimalAttack();
                    }
                }
                else
                {
                    int degats = 0;
                    if(gameboard[2][i].isAnimal())
                    {
                        degats = gameboard[2][i].getAnimalAttack();
                    }
                    if(gameboard[1][i].isAnimal())
                    {
                        if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.PUANT || gameboard[1][i].getLastPowerAnimal() == PowerEnum.PUANT)
                        {
                            m_turnAttack--;
                            degats--;
                        }
                    }
                    if(gameboard[2][i].isAnimal() && gameboard[2][i].getAnimalFly())
                    {
                        score += degats;
                        m_turnAttack += gameboard[2][i].getAnimalAttack();
                    }
                    else if(gameboard[2][i].isAnimal() && !gameboard[2][i].getAnimalFly())
                    {
                        gameboard[1][i].takeDamage(degats);
                        //gameManager.cardTakeDamage(1,i,degats);

                        if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.CONTACT_MORTEL || gameboard[2][i].getLastPowerAnimal() == PowerEnum.CONTACT_MORTEL)
                        {
                            gameboard[1][i].takeDamage(999);
                            //gameManager.cardTakeDamage(1,i,999);
                        }

                        if(gameboard[1][i] != null && gameboard[1][i].isAnimal())
                        {
                            if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.PIQUES_POINTUES || gameboard[1][i].getLastPowerAnimal() == PowerEnum.PIQUES_POINTUES)
                            {
                                gameboard[2][i].takeDamage(999);
                                //gameManager.cardTakeDamage(2,i,1);

                                if(gameboard[2][i].getHealthPoints() <= 0)
                                {
                                    gameboard[2][i] = null;
                                    //gameManager.setCard(null, 2, i);
                                }
                            }
                        }

                        if(gameboard[1][i] != null && gameboard[1][i].getHealthPoints() <= 0)
                        {
                            gameboard[1][i] = null;
                            //gameManager.setCard(null, 1, i);
                        }
                    }
                }
                if(gameboard[2][i] != null && gameboard[2][i].isAnimal() && (gameboard[2][i].getFirstPowerAnimal() == PowerEnum.CROISSANCE || gameboard[2][i].getLastPowerAnimal() == PowerEnum.CROISSANCE))
                {
                    gameboard[2][i] = new Loup();
                }
            }
        }

        ArrayList<Integer> indicesBloques = new ArrayList<>();

        for(int j=0; j<4; j++)
        {
            if(indicesBloques.contains(j)) {
                continue;
            }

            if(gameboard[2][j] != null && gameboard[2][j].isAnimal())
            {
                if(gameboard[2][j].getFirstPowerAnimal() == PowerEnum.COUREUR || gameboard[2][j].getLastPowerAnimal() == PowerEnum.COUREUR)
                {
                    if(j < 3 && gameboard[2][j+1] == null)
                    {
                        gameboard[2][j+1] = gameboard[2][j];
                        gameboard[2][j] = null;
                        indicesBloques.add(j+1);
                    }
                    else if(j > 0 && gameboard[2][j-1] == null)
                    {
                        gameboard[2][j-1] = gameboard[2][j];
                        gameboard[2][j] = null;
                    }
                }
            }
        }

        return score;
    }

    public Cards removeCard(int indHand) {return m_hand.remove(indHand);}

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
