package inscryption.players;

import inscryption.logic.Location;
import inscryption.logic.PowerEnum;
import inscryption.logic.ResultBox;
import inscryption.cards.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    private final ArrayList<AnimalsCard> m_hand;
    private final ArrayList<AnimalsCard> m_gamecards;
    private final ArrayList<AnimalsCard> m_gamecards_copy;
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

    public void createDraw()
    {
        ArrayList<AnimalsCard> temp = new ArrayList<>();
        temp.add(new Cat());
        temp.add(new Crow());
        temp.add(new Coyote());
        temp.add(new Grizzly());
        temp.add(new Ermine());
        temp.add(new Wolf());
        temp.add(new Cub_scout());
        temp.add(new Sparrow());
        temp.add(new Bug());
        temp.add(new Moose());
        temp.add(new Viper());
        temp.add(new Porcupine());
        while(temp.size() != 7)
        {
            int aleatoireIndex = m_random.nextInt(temp.size());
            temp.remove(temp.get(aleatoireIndex));
        }
        for(int i=0; i<8; i++)
        {
            Squirrel e = new Squirrel();
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
        AnimalsCard carte = m_gamecards.removeLast();
        m_hand.add(carte);
    }

    public void setGamecards(){
        m_hand.clear();
        m_gamecards.clear();
        ArrayList<AnimalsCard> tempDeck = new ArrayList<>(m_gamecards_copy);
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

    public AnimalsCard getAnimalAtInDraw(int index)
    {
        return m_gamecards.get(index);
    }

    public void removeAtInDraw(int index)
    {
        m_gamecards.remove(index);
    }

    public void addInDraw(AnimalsCard animal)
    {
        m_gamecards.add(animal);
    }


    public int getTurnAttack(){
        return m_turnAttack;
    }


    public ResultBox attack(Card[][] gameboard)
    {
        // Création de la liste pour stocker toutes les positions modifiées ce tour-ci
        ArrayList<Location> impactedLocations = new ArrayList<>();

        //---Gestion de l'attaque du score---
        int score = 0;
        m_turnAttack = 0;
        for(int i=0; i<4; i++)
        {
            if(gameboard[2][i] != null)
            {
                // La case en face est vide -> Dégâts directs au joueur adverse
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
                        if(gameboard[1][i].getFirstPowerAnimal() == PowerEnum.STINKY || gameboard[1][i].getLastPowerAnimal() == PowerEnum.STINKY)
                        {
                            degats = Math.max(degats - 1, 0);
                        }
                    }

                    // Carte volante -> Inflige des dégâts directs au score
                    if(gameboard[2][i].isAnimal() && gameboard[2][i].getAnimalFly())
                    {
                        score += degats;
                        m_turnAttack += degats; // On ajoute bien les dégâts calculés (qui prennent en compte le pouvoir Puant)
                    }
                    //---Gestion de l'attaque entre cartes---
                    // Carte normale -> Combat de créatures (m_turnAttack ne bouge pas puisque le score ne sera pas attaqué !)
                    // Dans ce cas, on appelle une méthode propre à Card qui gère indépendamment les duels entre des AnimalCards
                    else if(gameboard[2][i].isAnimal() && !gameboard[2][i].getAnimalFly())
                    {
                        Card attaquante = gameboard[2][i];
                        Card cible = gameboard[1][i];

                        attaquante.duel(impactedLocations, degats, i, 2, 1, cible);

                        //Nettoyage du tableau après le duel
                        if (gameboard[1][i].getHealthPoints() <= 0)
                        {
                            gameboard[1][i] = null;
                        }
                        if (gameboard[2][i] != null && gameboard[2][i].getHealthPoints() <= 0)
                        {
                            gameboard[2][i] = null;
                            this.increaseBones();
                        }
                    }
                }
            }
        }

        //---Gestion du pouvoir Coureur---
        ArrayList<Integer> indicesBloques = new ArrayList<>();

        for(int j=0; j<4; j++)
        {
            if(indicesBloques.contains(j)) {
                continue;
            }

            if(gameboard[2][j] != null && gameboard[2][j].isAnimal())
            {
                if(gameboard[2][j].getFirstPowerAnimal() == PowerEnum.RUNNER || gameboard[2][j].getLastPowerAnimal() == PowerEnum.RUNNER)
                {
                    //On vérif si on peut se déplacer à droite (on est pas au bord du plateau et c'est libre à notre droite)
                    if(j < 3 && gameboard[2][j+1] == null)
                    {
                        gameboard[2][j+1] = gameboard[2][j];
                        gameboard[2][j] = null;
                        //Il y a maintenant une carte à l'indice j+1
                        indicesBloques.add(j+1);

                        impactedLocations.add(new Location(2, j+1, gameboard[2][j]));
                        impactedLocations.add(new Location(2, j, (Card) null));
                    }
                    //Sinon on se déplace à gauche
                    else if(j > 0 && gameboard[2][j-1] == null)
                    {

                        gameboard[2][j-1] = gameboard[2][j];
                        gameboard[2][j] = null;
                        //Il y a maintenant une carte à l'indice j-1
                        indicesBloques.add(j-1);

                        impactedLocations.add(new Location(2, j-1, gameboard[2][j]));
                        impactedLocations.add(new Location(2, j, (Card) null));

                    }
                }
            }
        }

        //Gestion du pouvoir Croissance
        //Vérif si les survivants de l'attaque du player doivent se transformer en Loup
        for(int k = 0; k < 4; k++)
        {
            if(gameboard[1][k] != null && gameboard[1][k].isAnimal() && (gameboard[1][k].getFirstPowerAnimal() == PowerEnum.GROW || gameboard[1][k].getLastPowerAnimal() == PowerEnum.GROW))
            {
                Wolf newWolf = new Wolf();
                gameboard[1][k] = newWolf;
                impactedLocations.add(new Location(1, k, newWolf));
            }
        }

        return new ResultBox(score, impactedLocations);
    }

    public Card removeCard(int indHand) {return m_hand.remove(indHand);}

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
