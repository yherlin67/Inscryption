import inscryption.*;
import inscryption.players.Opponent;

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
            GameManager gameManager = new GameManager();
            Opponent opponent = new Opponent();
            gameManager.setGame(nbParties+1, opponent);
            PlateauAffichage plateau = new PlateauAffichage(gameManager, opponent);
            nbParties ++;
            System.out.println("Partie " + nbParties + "\n");
            while(gameManager.getVictoire()==null)
            {
                plateau.afficherPlateau();
            }
        }
    }


}
