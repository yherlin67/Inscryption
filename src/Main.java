import inscryption.cards.*;
import inscryption.logic.GameManager;
import inscryption.players.Opponent;
import inscryption.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        int nbParties = 1;
        Boolean matchVictory = null;
        boolean continueGame = true;
        Scanner sc = new Scanner(System.in);

        ArrayList<AnimalsCard> actions0;
        ArrayList<AnimalsCard> actions1;
        ArrayList<AnimalsCard> actions2;
        ArrayList<AnimalsCard> actions3;
        Player player = new Player();
        Opponent opponent = new Opponent();
        while(nbParties <= 3 && continueGame)
        {
            if(nbParties <= 0)
            {
                System.out.println("Erreur, vous n'êtes pas censé pouvoir faire ca !");
                break;
            }
            //Initialisation des cartes que va jouer l'adversaire (de plus en plus dur en fonction des parties)
            if(nbParties == 1)
            {
                actions0 = new ArrayList<>(Arrays.asList(new Coyote(), null, null, null));
                actions1 = new ArrayList<>(Arrays.asList(null, null, new Cub_scout(), null));
                actions2 = new ArrayList<>(Arrays.asList(null, new Bug(), null, null));
                actions3 = new ArrayList<>(Arrays.asList(new Sparrow(), null, new Sparrow(), null));
            }
            else if(nbParties == 2)
            {
                actions0 = new ArrayList<>(Arrays.asList(null, null, new Crow(), null));
                actions1 = new ArrayList<>(Arrays.asList(new Moose(), null, null, new Cub_scout()));
                actions2 = new ArrayList<>(Arrays.asList(null, null, new Porcupine(), null));
                actions3 = new ArrayList<>(Arrays.asList(new Wolf(), null, null, null));
            }
            else
            {
                actions0 = new ArrayList<>(Arrays.asList(new Porcupine(), null, null, null));
                actions1 = new ArrayList<>(Arrays.asList(null, null, new Grizzly(), null));
                actions2 = new ArrayList<>(Arrays.asList(null, new Coyote(), null, new Wolf()));
                actions3 = new ArrayList<>(Arrays.asList(new Grizzly(), null, null, new Viper()));
            }
            GameManager gameManager = new GameManager(player, opponent);
            for(int i=0; i<4; i++)
            {
                //On place les cartes de actions0 direct sur le plateau
                gameManager.setCard(actions0.get(i), 0, i);
            }
            //Les cartes des autres listes actions, à venir, sont stockées dans opponent
            opponent.setMatch(actions1,actions2,actions3);
            if(nbParties == 3)
            {
                //Proposition d'ajout à la pioche et pierre de sacrifice à la fin de la deuxième partie
                gameManager.proposeAddToDraw(sc);
                gameManager.displaySacrificeStone(sc);
            }
            gameManager.setGame(nbParties);
            //matchVictory = true, le joueur a gagné, partie suivante
            //matchVictory = false, le joueur a perdu, tout s'arrête
            //matchVictory = null, on continue !
            while(matchVictory == null)
            {
                gameManager.showBoard();
                gameManager.manageAction(sc);
                matchVictory = gameManager.gameReview();
            }
            continueGame = matchVictory;
            matchVictory = null;
            nbParties ++;
        }
        if (continueGame)
        {
            System.out.println("Vous avez survécu ! Bravo !");
        }
        else
        {
            System.out.println("Retentez votre chance... La prochaine fois sera peut être la bonne.");
        }
    }


}
