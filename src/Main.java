import inscryption.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Another challenger... It has been ages.");
        //Déclaration d'un plateau, qui symbolise l'affichage d'une partie

        int nbParties = 0;
        boolean victoire = true;

        while(nbParties < 3 && victoire)
        {
            Opponent opponent = new Opponent();
            Player player = new Player();
            GameManager gameManager = new GameManager(player, opponent);
            gameManager.setGame(nbParties+1, opponent);
            PlateauAffichage plateau = new PlateauAffichage(gameManager);
            nbParties ++;
            System.out.println("Partie " + nbParties + "\n");
            while(player.getVictoire() == null)
            {
                //System.out.flush();
                plateau.afficherPlateau();
            }
        }
    }


}
