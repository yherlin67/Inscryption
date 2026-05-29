import inscryption.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

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

        Player player = new Player();
        Opponent opponent = new Opponent();
        while(nbParties <= 3 && continueGame)
        {
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
