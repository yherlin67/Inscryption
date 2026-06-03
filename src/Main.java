import inscryption.*;
import inscryption.cards.*;
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

        ArrayList<AnimalsCards> actions0;
        ArrayList<AnimalsCards> actions1;
        ArrayList<AnimalsCards> actions2;
        ArrayList<AnimalsCards> actions3;
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
                actions0 = new ArrayList<>(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList<>(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList<>(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList<>(Arrays.asList(null, null, new Louveteau(), null));
            }
            else if(nbParties == 2)
            {
                actions0 = new ArrayList<>(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList<>(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList<>(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList<>(Arrays.asList(null, null, new Louveteau(), null));
            }
            else
            {
                actions0 = new ArrayList<>(Arrays.asList(new Louveteau(), new Moineau(), null, null));
                actions1 = new ArrayList<>(Arrays.asList(null, null, new Moineau(), null));
                actions2 = new ArrayList<>(Arrays.asList(new Punaise(), null, null, new Punaise()));
                actions3 = new ArrayList<>(Arrays.asList(null, null, new Louveteau(), null));
            }
            GameManager gameManager = new GameManager(player, opponent);
            for(int i=0; i<4; i++)
            {
                gameManager.setCard(actions0.get(i), 0, i);
            }
            opponent.setMatch(actions1,actions2,actions3);
            if(nbParties == 3)
            {
                gameManager.proposeAddToDraw(sc);
                gameManager.displaySacrificeStone(sc);
            }
            gameManager.setGame(nbParties);
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
