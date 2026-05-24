package inscryption;

import inscryption.cartes.*;
import inscryption.players.*;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameManager {

    private final Player m_player;
    private final Opponent m_opponent;
    private int m_score;
    private int m_turn;
    private boolean m_draw;

    private Cartes m_gameboard[][] = {
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
        m_turn = 1;
        m_score = 0;
        m_draw = true;
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
            if(gameReview() != null)
            {
                return false;
            }
            m_opponent.play();
            m_draw = true;
            m_opponent.attack();
            nextTurn();
            System.out.println("Vous finissez votre tour, maintenant au tour de l'adversaire !\n");
            return false;
        }
        if(action.equals("piocher") && m_draw && !m_player.getDraw().isEmpty())
        {
            m_player.draw();
            m_draw = false;
            System.out.println("Vous piochez une carte\n");
            return false;
        }
        else if(action.equals("piocher") && !m_draw || m_player.getDraw().isEmpty())
        {
            System.out.println("Vous ne pouvez pas piocher\n");
            return true;
        }
        else if(action.length()<6)
        {
            System.out.println("Veuillez rentrer une chaîne valide\n");
            return true;
        }
        else if(action.substring(0,6).equals("placer") && action.length() >= 10)
        {
            int indHand = 0;
            int indBoard = 0;

            if(action.length()==11 && action.charAt(9) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,8)) - 1;
                    indBoard = parseInt(action.substring(10,11)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                if (m_gameboard[2][indBoard] != null && m_gameboard[2][indBoard].getAnimals() == null) {
                    System.out.println("Vous ne pouvez pas placer de carte ici : un obstacle bloque l'emplacement !\n");
                    return true;
                }

                int gouttesRequises = m_player.getHand().get(indHand).getBlood();

                if(gouttesRequises == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        placeCard(indHand, indBoard);
                        return false;
                    } else {
                        System.out.println("Vous ne pouvez pas placer ça ici (case occupée)\n");
                        return true;
                    }
                }
                else
                {
                    String enCours = "";
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice") && !action.equals("annuler"))
                    {
                        System.out.println("\n [sacrifier <position>]");
                        System.out.println(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + gouttesRequises + ")");
                        System.out.println(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= gouttesRequises)
                                {
                                    System.out.println("Vous avez déjà assez de sang ! Validez votre sacrifice.\n");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].getAnimals() != null)
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        System.out.println("Animal déjà sélectionné pour le sacrifice ! \n");
                                    }
                                    else
                                    {
                                        enCours += m_gameboard[2][idxSacrifice].getName() + " ";
                                        aSupprimer.add(idxSacrifice);
                                        blood += 1;
                                    }
                                }
                                else
                                {
                                    System.out.println("Il n'y a rien (ou un obstacle) à sacrifier ici\n");
                                }
                            }
                            else
                            {
                                System.out.println("Veuillez rentrer une chaine valide\n");
                            }
                        }
                        else if (action.equals("annuler")) {
                            return true;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            System.out.println("Veuillez rentrer une chaine valide\n");
                        }
                    }

                    if(action.equals("valider sacrifice")) {
                        if(blood < gouttesRequises) {
                            System.out.println("Pas assez de sang récolté pour cette carte !\n");
                            return true;
                        }

                        for(int col : aSupprimer) {
                            m_gameboard[2][col] = null;
                        }

                        if(m_gameboard[2][indBoard] == null) {
                            placeCard(indHand, indBoard);
                            return false;
                        } else {
                            System.out.println("Placement impossible ! La case ciblée est encore occupée.\n");
                            for(int col : aSupprimer) {
                                m_gameboard[2][col] = null;
                            }
                            return true;
                        }
                    }
                    return true;
                }
            }
            else if(action.length()==12 && action.charAt(10) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,9)) - 1;
                    indBoard = parseInt(action.substring(11,12)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                if (m_gameboard[2][indBoard] != null && m_gameboard[2][indBoard].getAnimals() == null) {
                    System.out.println("Vous ne pouvez pas placer de carte ici : un obstacle bloque l'emplacement !\n");
                    return true;
                }

                int gouttesRequises = m_player.getHand().get(indHand).getBlood();

                if(gouttesRequises == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        placeCard(indHand, indBoard);
                        return false;
                    } else {
                        System.out.println("Vous ne pouvez pas placer ça ici (case occupée)\n");
                        return true;
                    }
                }
                else
                {
                    String enCours = "";
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice") && !action.equals("annuler"))
                    {
                        System.out.println("\n [sacrifier <position>]");
                        System.out.println(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + gouttesRequises + ")");
                        System.out.println(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= gouttesRequises)
                                {
                                    System.out.println("Vous avez déjà assez de sang ! Validez votre sacrifice.\n");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].getAnimals() != null)
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        System.out.println("Animal déjà sélectionné pour le sacrifice ! \n");
                                    }
                                    else
                                    {
                                        enCours += m_gameboard[2][idxSacrifice].getName() + " ";
                                        aSupprimer.add(idxSacrifice);
                                        blood += 1;
                                    }
                                }
                                else
                                {
                                    System.out.println("Il n'y a rien (ou un obstacle) à sacrifier ici\n");
                                }
                            }
                            else
                            {
                                System.out.println("Veuillez rentrer une chaine valide\n");
                            }
                        }
                        else if (action.equals("annuler")) {
                            return true;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            System.out.println("Veuillez rentrer une chaine valide\n");
                        }
                    }

                    if(action.equals("valider sacrifice")) {
                        if(blood < gouttesRequises) {
                            System.out.println("Pas assez de sang récolté pour cette carte !\n");
                            return true;
                        }

                        for(int col : aSupprimer) {
                            m_gameboard[2][col] = null;
                        }

                        if(m_gameboard[2][indBoard] == null) {
                            placeCard(indHand, indBoard);
                            return false;
                        } else {
                            System.out.println("Placement impossible ! La case ciblée est encore occupée.\n");
                            for(int col : aSupprimer) {
                                m_gameboard[2][col] = null;
                            }
                            return true;
                        }
                    }
                    return true;
                }
            }
            else
            {
                System.out.println("Veuillez rentrer une chaîne valide\n");
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
        m_turn++;
    }

    public void placeCard(int indHand,int indBoard)
    {
        Cartes maCard = m_player.removeCard(indHand);
        m_gameboard[2][indBoard] = maCard;
    }

    public Boolean gameReview()
    {
        if(m_score <= -5)
        {
            return false;
        }
        else if(m_score >= 5)
        {
            return true;
        }
        else
        {
            return null;
        }
    }

    public int getScore(){return m_score;}

    public void setScore(int attack){m_score+=attack;}

    public int getTurn(){return m_turn;}

    public Cartes[][] getCards(){return m_gameboard;}

    public ArrayList<Cartes_animaux> getHand(){return m_player.getHand();}

    public ArrayList<Cartes_animaux> getDraw(){return m_player.getDraw();}

    public Player getPlayer(){return m_player;}

    public Opponent getOpponent(){return m_opponent;}

    public void setGame(int match, Opponent opponent) {
        opponent.setGameManager(this);
        if (match == 1) {
            m_gameboard[2][1] = new Rocher();
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
        m_gameboard[row][columns] = carte;
    }
}
