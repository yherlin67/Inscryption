
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

            Plateau plateau = new Plateau();
            nbParties ++;
            System.out.println("Partie " + nbParties + "\n");
            plateau.afficherPlateau();
        }
    }


}
