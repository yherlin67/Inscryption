package inscryption.players;

import inscryption.Location;
import inscryption.PowerEnum;
import inscryption.AttackResult;
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
        m_obtainedBones = 0;
        m_turnAttack = 0;
        this.createDraw();
    }

    public Player(Player other)
    {
        m_hand = new ArrayList<>();
        for(AnimalsCards card : other.m_hand)
        {
            m_hand.add(card);
        }

        m_gamecards = new ArrayList<>();
        for(AnimalsCards card : other.m_gamecards)
        {
            m_gamecards.add(card);
        }

        m_gamecards_copy = new ArrayList<>();
        for(AnimalsCards card : other.m_gamecards_copy)
        {
            m_gamecards_copy.add(card);
        }

        m_random = new Random();
        m_obtainedBones = other.m_obtainedBones;
        m_turnAttack = other.m_turnAttack;
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


    public AttackResult attack(Cards[][] gameboard)
    {
        // Création de la liste pour stocker toutes les positions modifiées ce tour-ci
        ArrayList<Location> impactedLocations = new ArrayList<>();

        int score = 0;
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(gameboard[2][i] != null)
            {
                // Cas 1 : La case en face est vide -> Dégâts directs au joueur adverse !
                if(gameboard[1][i] == null)
                {
                    if(gameboard[2][i].isAnimal())
                    {
                        score += gameboard[2][i].getAnimalAttack();
                        m_turnAttack += gameboard[2][i].getAnimalAttack();
                    }
                }
                else // Il y a une carte sur la ligne ennemie
                {
                    int degats = 0;
                    if(gameboard[2][i].isAnimal())
                    {
                        degats = gameboard[2][i].getAnimalAttack();
                    }

                    // Gestion du pouvoir Puant sur la carte en face
                    if(gameboard[1][i].isAnimal())
                    {
                        if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.PUANT || gameboard[1][i].getLastPowerAnimal() == PowerEnum.PUANT)
                        {
                            degats--;
                        }
                    }

                    // Sécurité : les dégâts ne peuvent pas être négatifs
                    if (degats < 0) degats = 0;

                    // Cas 2 : Ta carte est volante -> Elle survole la carte ennemie et tape le joueur !
                    if(gameboard[2][i].isAnimal() && gameboard[2][i].getAnimalFly())
                    {
                        score += degats;
                        m_turnAttack += degats; // On ajoute bien les dégâts calculés (qui prennent en compte Puant)
                    }
                    // Cas 3 : Carte normale -> Combat de créatures (m_turnAttack ne bouge pas !)
                    else if(gameboard[2][i].isAnimal() && !gameboard[2][i].getAnimalFly())
                    {
                        impactedLocations.add(new Location(1, i, degats));
                        gameboard[1][i].takeDamage(degats); // Attention, c'était gameboard[2][i] dans ton code, petit bug ici aussi !

                        if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.CONTACT_MORTEL || gameboard[2][i].getLastPowerAnimal() == PowerEnum.CONTACT_MORTEL)
                        {
                            impactedLocations.add(new Location(1, i, 999));
                            gameboard[1][i].takeDamage(999);
                        }

                        if(gameboard[1][i] != null && gameboard[1][i].isAnimal())
                        {
                            if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.PIQUES_POINTUES || gameboard[1][i].getLastPowerAnimal() == PowerEnum.PIQUES_POINTUES)
                            {
                                gameboard[2][i].takeDamage(1);
                                impactedLocations.add(new Location(2, i, 1));

                                if(gameboard[2][i].getHealthPoints() <= 0)
                                {
                                    gameboard[2][i] = null;
                                    impactedLocations.add(new Location(2, i, (Cards) null));
                                }
                            }
                        }

                        if(gameboard[1][i] != null && gameboard[1][i].getHealthPoints() <= 0)
                        {
                            gameboard[1][i] = null;
                            impactedLocations.add(new Location(1, i, (Cards) null));
                        }
                    }
                }

                if(gameboard[2][i] != null && gameboard[2][i].isAnimal() && (gameboard[2][i].getFirstPowerAnimal() == PowerEnum.CROISSANCE || gameboard[2][i].getLastPowerAnimal() == PowerEnum.CROISSANCE))
                {
                    gameboard[2][i] = new Loup();
                    impactedLocations.add(new Location(2, i, new Loup()));
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

                        impactedLocations.add(new Location(2, j+1, gameboard[2][j]));
                        impactedLocations.add(new Location(2, j, (Cards) null));
                    }
                    else if(j > 0 && gameboard[2][j-1] == null)
                    {

                        gameboard[2][j-1] = gameboard[2][j];
                        gameboard[2][j] = null;

                        impactedLocations.add(new Location(2, j-1, gameboard[2][j]));
                        impactedLocations.add(new Location(2, j, (Cards) null));

                    }
                }
            }
        }

        return new AttackResult(score, impactedLocations);
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

    public int getCardAttackInHand(int i){return m_hand.get(i).getAnimalAttack();}

    public int getCardHealthPointInHand(int i){return m_hand.get(i).getHealthPoints();}

    public int getCardBloodInHand(int i){return m_hand.get(i).getBlood();}

    public int getCardBonesInHand(int i){return m_hand.get(i).getBone();}

    public String getCardFlyInHand(int i)
    {
        if(m_hand.get(i).getAnimalFly())
        {
            return "Oui";
        }
        else
        {
            return "Non";
        }
    }

    public PowerEnum getCardFirstPowerInHand(int i){return m_hand.get(i).getFirstPowerAnimal();}

    public PowerEnum getCardLastPowerInHand(int i){return m_hand.get(i).getLastPowerAnimal();}

    @Override
    public String toString(){
        return "Player";
    }

}
