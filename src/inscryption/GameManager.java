package inscryption;

import inscryption.cartes.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameManager {

    private Player m_player;
    private Opponent m_opponent;
    private int m_score;
    private int m_tour;

    private Cartes m_plateau[][] = {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
    };

    public GameManager(Player player, Opponent opponent)
    {
        m_player = player;
        m_player.setGameManager(this);
        m_opponent = opponent;
        m_opponent.setGameManager(this);
        m_tour = 1;
        m_score = 0;
        m_player.createDraw();
        for(int j=0; j<4; j++)
        {
            m_player.draw();
        }
    }

    public boolean manageAction()
    {
        Scanner sc = new Scanner(System.in);
        String action = sc.nextLine();
        if(action.equals("fin"))
        {
            m_player.attack();
            m_opponent.play();
            m_opponent.attack();
            nextTurn();
            System.out.println("Vous finissez votre tour, maintenant au tour de l'adversaire !\n");
            return false;
        }
        if(action.equals("piocher"))
        {
            m_player.draw();
            System.out.println("Vous piochez une carte\n");
            return false;
        }
        if(action.substring(0,6).equals("placer") && action.length() >= 10)
        {
            int indHand = 0;
            int indBoard = 0;

            try {
                indHand = parseInt(action.substring(7,8)) - 1;
                indBoard = parseInt(action.substring(10,11)) - 1;
            } catch (NumberFormatException e) {
                return true;
            }
            if(m_plateau[2][indBoard] == null)
            {
                placeCard(indHand,indBoard);
                return false;
            }
            else
            {
                System.out.println("Vous ne pouvez pas placer ça ici\n");
                return true;
            }
        }
        else
        {
            System.out.println("Veuillez rentrer une chaîne valide\n");
            return true;
        }
    }

    public void nextTurn()
    {
        m_tour ++;
    }

    public void placeCard(int indHand,int indBoard)
    {
        Cartes maCard = m_player.removeCard(indHand);
        m_plateau[2][indBoard] = maCard;
    }

    public int getScore(){return m_score;}

    public void setScore(int attack){m_score+=attack;}

    public int getTurn(){return m_tour;}

    public Cartes[][] getCartes(){return m_plateau;}

    public ArrayList<Cartes_animaux> getHand(){return m_player.getHand();}

    public ArrayList<Cartes_animaux> getDraw(){return m_player.getDraw();}

    public Player getPlayer(){return m_player;}

    public Opponent getOpponent(){return m_opponent;}

    public void increaseTurn(){m_tour++;}

    public void setGame(int match, Opponent opponent) {
        opponent.setGameManager(this);
        if (match == 1) {
            m_plateau[2][1] = new Rocher();
            opponent.setFirstMatch();
        }
        else if(match == 2)
        {
            opponent.setSecondMatch();
        }
        else
        {
            opponent.setThirdMatch();
        }
    }

    public void setCard(Cartes carte, int row, int columns)
    {
        m_plateau[row][columns] = carte;
    }
}
