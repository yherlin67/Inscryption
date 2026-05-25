import inscryption.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Another challenger... It has been ages.");
        //Déclaration d'un plateau, qui symbolise l'affichage d'une partie

        int nbParties = 1;
        Boolean matchVictory = null;
        boolean continueGame = true;

        while(nbParties <= 3 && continueGame)
        {
            Opponent opponent = new Opponent();
            Player player = new Player();
            GameManager gameManager = new GameManager(player, opponent);
            gameManager.setGame(nbParties, opponent, nbParties);
            PlateauAffichage plateau = new PlateauAffichage(gameManager);
            while(matchVictory == null)
            {
                plateau.displayGameboard(gameManager.getMessage());
                gameManager.manageAction();
                matchVictory = gameManager.gameReview();
            }
            if(!matchVictory)
            {
                continueGame = false;
            }
            matchVictory = null;
            nbParties ++;
            if(nbParties == 3)
            {
                System.out.flush();
                plateau.displayNewCard();
            }
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
