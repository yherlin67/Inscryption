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
        Boolean matchVictoiry = null;
        boolean continueGame = true;

        while(nbParties <= 3 && continueGame)
        {
            Opponent opponent = new Opponent();
            Player player = new Player();
            GameManager gameManager = new GameManager(player, opponent);
            gameManager.setGame(nbParties, opponent);
            PlateauAffichage plateau = new PlateauAffichage(gameManager);
            System.out.println("Partie " + nbParties + "\n");
            while(matchVictoiry == null)
            {
                //System.out.flush();
                plateau.afficherPlateau();
                matchVictoiry = gameManager.gameReview();
            }
            if(!matchVictoiry)
            {
                continueGame = false;
            }
            matchVictoiry = null;
            nbParties ++;
        }
    }


}
