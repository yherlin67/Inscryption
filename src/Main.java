import inscryption.*;
import inscryption.cartes.Cartes;
import inscryption.cartes.Louveteau;
import inscryption.cartes.Moineau;
import inscryption.cartes.Punaise;
import inscryption.players.Opponent;
import inscryption.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Another challenger... It has been ages.");
        //Déclaration d'un plateau, qui symbolise l'affichage d'une partie

        int nbParties = 1;
        Boolean matchVictory = null;
        boolean continueGame = true;
        Scanner sc = new Scanner(System.in);

        ArrayList<Cartes> actions0;
        ArrayList<Cartes> actions1;
        ArrayList<Cartes> actions2;
        ArrayList<Cartes> actions3;
        Player player = new Player();
        Opponent opponent = new Opponent();
        while(nbParties <= 3 && continueGame)
        {
            if(nbParties <= 0)
            {
                System.out.println("Erreur, vous n'êtes pas censé pouvoir faire ca !");
                break;
            }
            if(nbParties == 1)
            {
                actions0 = new ArrayList(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList(Arrays.asList(null, null, new Louveteau(), null));
            }
            else if(nbParties == 2)
            {
                actions0 = new ArrayList(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList(Arrays.asList(null, null, new Louveteau(), null));
            }
            else
            {
                actions0 = new ArrayList(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList(Arrays.asList(null, null, new Louveteau(), null));
            }
            opponent.setMatch(actions0,actions1,actions2,actions3);
            GameManager gameManager = new GameManager(player, opponent);
            if(nbParties == 3)
            {
                gameManager.proposeAddToDraw(sc);
                gameManager.displaySacrificeStone(sc);
            }
            gameManager.setGame(nbParties, opponent, nbParties);
            PlateauAffichage plateau = new PlateauAffichage(gameManager);
            while(matchVictory == null)
            {
                plateau.displayGameboard(gameManager.getMessage());
                gameManager.manageAction(sc);
                matchVictory = gameManager.gameReview();
            }
            continueGame = matchVictory;
            matchVictory = null;
            nbParties ++;
//            if(nbParties == 3)
//            {
//                System.out.flush();
//            }
        }
        if (continueGame)
        {
            System.out.flush();
            System.out.println("Vous avez survécu ! Bravo !");
        }
        else
        {
            System.out.flush();
            System.out.println("Retentez votre chance... La prochaine fois sera peut être la bonne.");
        }
    }


}
