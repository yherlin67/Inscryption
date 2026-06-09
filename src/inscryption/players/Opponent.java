package inscryption.players;

import inscryption.logic.ResultBox;
import inscryption.logic.Location;
import inscryption.logic.PowerEnum;
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

    public void setMatch(ArrayList<AnimalsCards> actions0, ArrayList<AnimalsCards> actions1, ArrayList<AnimalsCards> actions2)
    {
        m_actions[0] = actions0;
        m_actions[1] = actions1;
        m_actions[2] = actions2;
    }

    public ResultBox play(Cards[][] gameboard)
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
        return new ResultBox(0, impactedLocations);
    }

    public int getTurnAttack(){
        return m_turnAttack;
    }

    public ResultBox attack(Cards[][] gameboard, Player player)
    {
        ArrayList<Location> impactedLocations = new ArrayList<>();
        int score = 0;
        m_turnAttack = 0;

        for(int i=0; i<4; i++)
        {
            if(gameboard[1][i] != null)
            {
                // Case en face vide -> Dégâts directs au joueur !
                if(gameboard[2][i] == null)
                {
                    if(gameboard[1][i].isAnimal())
                    {
                        score -= gameboard[1][i].getAnimalAttack();
                        m_turnAttack += gameboard[1][i].getAnimalAttack();
                    }
                }
                else // Il y a une carte du player en face
                {
                    int degats = 0;
                    if(gameboard[1][i].isAnimal())
                    {
                        degats = gameboard[1][i].getAnimalAttack();
                    }

                    // Gestion du pouvoir Puant de la carte du player
                    if(gameboard[2][i] != null && gameboard[2][i].isAnimal())
                    {
                        if(gameboard[2][i].getFirstPowerAnimal() == PowerEnum.STINKY || gameboard[2][i].getLastPowerAnimal() == PowerEnum.STINKY)
                        {
                            degats--;
                        }
                    }

                    if (degats < 0) degats = 0;

                    // Carte ennemie volante -> Inflige des dégâts directs au score
                    if(gameboard[1][i].isAnimal() && gameboard[1][i].getAnimalFly())
                    {
                        score -= degats;
                        m_turnAttack += degats;
                    }
                    // Combat de cartes -> Pas de m_turnAttack
                    else if(gameboard[1][i].isAnimal() && !gameboard[1][i].getAnimalFly())
                    {
                        Cards attaquante = gameboard[1][i]; // Ligne 1
                        Cards cible = gameboard[2][i];       // Ligne 2

                        attaquante.duel(impactedLocations, degats, i, 1, 2, cible);

                        // Nettoyage du tableau après duel
                        if (gameboard[2][i].getHealthPoints() <= 0) {
                            gameboard[2][i] = null;
                            player.increaseBones(); // Le joueur gagne un os car sa carte est morte
                        }
                        if (gameboard[1][i] != null && gameboard[1][i].getHealthPoints() <= 0) {
                            gameboard[1][i] = null; // L'adversaire meurt (possible à cause des piques du joueur)
                        }
                    }
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
                if(gameboard[1][j].getFirstPowerAnimal() == PowerEnum.RUNNER || gameboard[1][j].getLastPowerAnimal() == PowerEnum.RUNNER)
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

        //Vérif si les survivants de l'attaque doivent se transformer en Loup
        for(int k = 0; k < 4; k++)
        {
            if(gameboard[2][k] != null && gameboard[2][k].isAnimal() && (gameboard[2][k].getFirstPowerAnimal() == PowerEnum.GROW || gameboard[2][k].getLastPowerAnimal() == PowerEnum.GROW))
            {
                Wolf newWolf = new Wolf();
                gameboard[2][k] = newWolf;
                impactedLocations.add(new Location(2, k, newWolf));
            }
        }

        return new ResultBox(score, impactedLocations);
    }

    @Override
    public String toString(){
        return "Opponent";
    }
}