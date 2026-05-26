package inscryption;

import inscryption.cartes.*;
import inscryption.players.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameManager {

    private final Player m_player;
    private final Opponent m_opponent;
    private int m_score;
    private int m_turn;
    private boolean m_draw;
    private int m_game;
    private String m_message;
    private Random m_random;

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
        m_game = 1;
        m_draw = true;
        m_message = "";
        m_player.createDraw();
        m_random = new Random();
        for(int j=0; j<4; j++)
        {
            m_player.draw();
        }
    }

    public boolean manageAction(Scanner sc)
    {
        //Retourne si on doit afficher une erreur ou non !
        String action = sc.nextLine();
        if(action.equals("fin"))
        {
            m_player.attack();
            if(gameReview() != null)
            {
                setMessage("Vous avez gagné ! Pour l'instant...");
                return false;
            }
            m_opponent.play();
            m_draw = true;
            m_opponent.attack();
            nextTurn();
            setMessage("Votre adversaire à joué, à votre tour de jouer maintenant...");
            return false;
        }
        if(action.equals("piocher") && m_draw && !m_player.getDraw().isEmpty())
        {
            m_player.draw();
            m_draw = false;
            setMessage("Vous piochez une carte");
            return false;
        }
        else if(action.equals("piocher") && !m_draw || m_player.getDraw().isEmpty())
        {
            setMessage("Vous avez déjà pioché voyons ! Ne soyez pas trop gourmand...");
            return true;
        }
        else if(action.length()<6)
        {
            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
            return true;
        }
        else if(action.substring(0,6).equals("placer") && action.length() >= 10)
        {
            int indHand = 0;
            int indBoard = 0;

            // CAS 1 : Index de la main à 1 chiffre (ex: placer 3 B1)
            if(action.length()==11 && action.charAt(9) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,8)) - 1;
                    indBoard = parseInt(action.substring(10,11)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                int requiedDrop = m_player.getHand().get(indHand).getBlood();
                int requiedBones = m_player.getHand().get(indHand).getBone();

                if(requiedDrop == 0 && requiedBones == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        setMessage("Vous placer la carte");
                        placeCard(indHand, indBoard);
                        return false;
                    } else {
                        setMessage("Ah non ! C'est déjà occupé ici !");
                        return true;
                    }
                }
                else if(requiedBones > 0)
                {
                    int os = m_player.getPlayerBones();
                    if(os >= requiedBones)
                    {
                        if (m_gameboard[2][indBoard] == null) {
                            os -= requiedBones;
                            m_player.setPlayerBones(os);
                            setMessage("Vous placer la carte");
                            placeCard(indHand, indBoard);
                            return false;
                        } else {
                            setMessage("Ah non ! C'est déjà occupé ici !");
                            return true;
                        }
                    }
                    else
                    {
                        setMessage("Vous n'avez pas assez d'os pour placer cette carte !");
                        return true;
                    }
                }
                else if(requiedDrop > 0)
                {
                    String enCours = "";
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice") && !action.equals("annuler"))
                    {
                        System.out.println("\n [sacrifier <position>]");
                        System.out.println(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + requiedDrop + ")");
                        System.out.println(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= requiedDrop)
                                {
                                    System.out.println("Vous avez déjà assez de sang ! Validez votre sacrifice.");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].getAnimals() != null)
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        System.out.println("Sacrifiez un animal une fois c'est cruel mais deux fois ??");
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
                                    System.out.println("Le vide n'est pas un animal à sacrifier.");
                                }
                            }
                            else
                            {
                                setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                            }
                        }
                        else if (action.equals("annuler")) {
                            setMessage("Annulation du sacrifice");
                            return true;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            System.out.println("Je crois que vous ne pouvez pas faire ça dans ce jeu..");
                        }
                    }

                    if(action.equals("valider sacrifice")) {
                        if(blood < requiedDrop) {
                            System.out.println("Pas assez de sang récolté pour cette carte !");
                            return true;
                        }

                        for(int col : aSupprimer) {
                            m_gameboard[2][col] = null;
                            m_player.increaseBones();
                        }

                        if(m_gameboard[2][indBoard] == null) {
                            placeCard(indHand, indBoard);
                            setMessage("Vous placer la carte.");
                            return false;
                        } else {
                            setMessage("Placement impossible ! La case ciblée est encore occupée.");
                            return true;
                        }
                    }
                    return true;
                }
            }
            // CAS 2 : Index de la main à 2 chiffres (ex: placer 10 B1)
            else if(action.length()==12 && action.charAt(10) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,9)) - 1;
                    indBoard = parseInt(action.substring(11,12)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                int requiedDrop = m_player.getHand().get(indHand).getBlood();

                if(requiedDrop == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        placeCard(indHand, indBoard);
                        setMessage("Vous placer la carte.");
                        return false;
                    } else {
                        setMessage("Ah non ! C'est déjà occupé ici !");
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
                        System.out.println(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + requiedDrop + ")");
                        System.out.println(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= requiedDrop)
                                {
                                    System.out.println("Vous avez déjà assez de sang ! Pourquoi sacrifier d'autres pauvres bêtes ?");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].getAnimals() != null)
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        System.out.println("Sacrifiez un animal une fois c'est cruel mais deux fois ??");
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
                                    System.out.println("Le vide n'est pas un animal à sacrifier.");
                                }
                            }
                            else
                            {
                                setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                            }
                        }
                        else if (action.equals("annuler")) {
                            setMessage("Annulation du sacrifice.");
                            return true;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                        }
                    }

                    if(action.equals("valider sacrifice")) {
                        if(blood < requiedDrop) {
                            System.out.println("Pas assez de sang récolté pour cette carte !");
                            return true;
                        }

                        for(int col : aSupprimer) {
                            m_gameboard[2][col] = null;
                            m_player.increaseBones();
                        }

                        if(m_gameboard[2][indBoard] == null) {
                            setMessage("Vous placer la carte");
                            placeCard(indHand, indBoard);
                            return false;
                        } else {
                            setMessage("Placement impossible ! La case ciblée est encore occupée.");
                            return true;
                        }
                    }
                    return true;
                }
            }
            else
            {
                setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                return true;
            }
        }
        else
        {
            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
            return true;
        }
        return false;
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

    public void setMessage(String message){m_message = message;}

    public String getMessage(){return m_message;}

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

    public void setGame(int match, Opponent opponent, int actualGame) {
        opponent.setGameManager(this);
        m_game = actualGame;
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

    public void proposeAddToDraw(Scanner sc)
    {
        System.out.println("Avant de jouer la troisième partie, vous pouvez rajouter une carte dans la pioche parmi les deux cartes ci-dessous ! (tapez 1 ou 2)");
        Cartes_animaux proposition;
        ArrayList<Cartes_animaux> propositions = new ArrayList<Cartes_animaux>();
        ArrayList<Cartes_animaux> temp = new ArrayList<Cartes_animaux>();
        temp.add(new Ecureuil());
        temp.add(new Chat());
        temp.add(new Corbeau());
        temp.add(new Coyote());
        temp.add(new Grizzly());
        temp.add(new Hermine());
        temp.add(new Loup());
        temp.add(new Louveteau());
        temp.add(new Moineau());
        temp.add(new Punaise());
        for(int i=0; i <2; i++)
        {
            int random_index = m_random.nextInt(temp.size());
            proposition = temp.get(random_index);
            propositions.add(proposition);
            temp.remove(random_index);
            String ligneFormatee = String.format("%-2d %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-10d",
                    (i+1),
                    proposition.getName(),
                    proposition.getHealthPoints(),
                    proposition.getAttack(),
                    proposition.getBlood(),
                    proposition.getBone());
            System.out.println(ligneFormatee);
        }
        String choix;
        boolean valide = false;
        while(!valide)
        {
            choix = sc.nextLine();
            if(choix.equals("1"))
            {
                m_player.getDraw().add(propositions.get(0));
                valide = true;
            }
            else if(choix.equals("2"))
            {
                m_player.getDraw().add(propositions.get(1));
                valide = true;
            }
            else
            {
                System.out.println("Tapez 1 ou 2 c'est pas compliqué non ?");
            }
        }
    }

    public void setCard(Cartes carte, int row, int columns)
    {
        m_gameboard[row][columns] = carte;
    }

    public int getGame(){return m_game;}
}
