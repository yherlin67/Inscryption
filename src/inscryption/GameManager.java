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
    private final PlateauAffichage m_display;

    private Cartes m_gameboard[][];

    public GameManager(Player player, Opponent opponent)
    {
        m_player = player;
        m_player.setGameManager(this);
        m_opponent = opponent;
        m_opponent.setGameManager(this);
        m_gameboard = new Cartes[][]{
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
        };
        m_display = new PlateauAffichage(this);
        m_turn = 1;
        m_score = 0;
        m_game = 1;
        m_draw = true;
        m_message = "";
        m_player.setGamecards();
        m_random = new Random();
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
                return false;
            }
            m_opponent.play();
            m_draw = true;
            m_opponent.attack();
            nextTurn();
            setMessage("Votre adversaire à joué, à votre tour de jouer maintenant...");
            return false;
        }
        if(action.equals("piocher") && m_draw && !m_player.isDrawEmpty())
        {
            m_player.draw();
            m_draw = false;
            setMessage("Vous piochez une carte");
            return false;
        }
        else if(m_player.isDrawEmpty())
        {
            setMessage("La pioche est vide !");
            return true;
        }
        else if(action.equals("piocher") && !m_draw)
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

            if(action.length()==11 && action.charAt(9) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,8)) - 1;
                    indBoard = parseInt(action.substring(10,11)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                int requiedDrop = m_player.getHandAt(indHand).getBlood();
                int requiedBones = m_player.getHandAt(indHand).getBone();

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
                    int os = m_player.getBones();
                    if(os >= requiedBones)
                    {
                        if (m_gameboard[2][indBoard] == null) {
                            os -= requiedBones;
                            m_player.setBones(os);
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
                    if (m_gameboard[2][indBoard] != null && "Chat".equals(m_gameboard[2][indBoard].getName())) {
                        setMessage("Ah non ! Un Chat occupe déjà cette case, placement impossible !");
                        return true;
                    }

                    String enCours = "";
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice") && !action.equals("annuler"))
                    {
                        m_display.print("\n [sacrifier <position>]");
                        m_display.print(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + requiedDrop + ")");
                        m_display.print(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= requiedDrop)
                                {
                                    m_display.print("Vous avez déjà assez de sang ! Validez votre sacrifice.");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].isAnimal())
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        m_display.print("Sacrifiez un animal une fois c'est cruel mais deux fois ??");
                                    }
                                    else
                                    {
                                        enCours += m_gameboard[2][idxSacrifice].getName() + " ";
                                        if(m_gameboard[2][idxSacrifice].getFirstPowerAnimal() != PowerEnum.NOMBREUSES_VIES || m_gameboard[2][idxSacrifice].getLastPowerAnimal() != PowerEnum.NOMBREUSES_VIES)
                                        {
                                            aSupprimer.add(idxSacrifice);
                                        }
                                        blood += 1;
                                    }
                                }
                                else
                                {
                                    m_display.print("Le vide n'est pas un animal à sacrifier.");
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
                            m_display.print("Je crois que vous ne pouvez pas faire ça dans ce jeu..");
                        }
                    }

                    if(action.equals("valider sacrifice")) {
                        if(blood < requiedDrop) {
                            m_display.print("Pas assez de sang récolté pour cette carte !");
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
                setMessage("Placement impossible ! La case ciblée est encore occupée.");
            }
            else if(action.length()==12 && action.charAt(10) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,9)) - 1;
                    indBoard = parseInt(action.substring(11,12)) - 1;
                } catch (NumberFormatException e) {
                    return true;
                }

                int requiedDrop = m_player.getHandAt(indHand).getBlood();

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
                else if(requiedDrop > 0)
                {
                    if (m_gameboard[2][indBoard] != null && "Chat".equals(m_gameboard[2][indBoard].getName())) {
                        setMessage("Ah non ! Un Chat occupe déjà cette case, placement impossible !");
                        return true;
                    }

                    String enCours = "";
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice") && !action.equals("annuler"))
                    {
                        m_display.print("\n [sacrifier <position>]");
                        m_display.print(" [valider sacrifice] (Sacrifice en cours : " + enCours + " | Récolté : " + blood + "/" + requiedDrop + ")");
                        m_display.print(" [annuler]");
                        action = sc.nextLine();

                        if (action.startsWith("sacrifier") && action.length() >= 12)
                        {
                            if(action.charAt(10) == 'B')
                            {
                                int idxSacrifice = Character.getNumericValue(action.charAt(11)) - 1;

                                if(blood >= requiedDrop)
                                {
                                    m_display.print("Vous avez déjà assez de sang ! Pourquoi sacrifier d'autres pauvres bêtes ?");
                                }
                                else if(idxSacrifice >= 0 && idxSacrifice < 4 && m_gameboard[2][idxSacrifice] != null && m_gameboard[2][idxSacrifice].isAnimal())
                                {
                                    if(aSupprimer.contains(idxSacrifice))
                                    {
                                        m_display.print("Sacrifiez un animal une fois c'est cruel mais deux fois ??");
                                    }
                                    else
                                    {
                                        enCours += m_gameboard[2][idxSacrifice].getName() + " ";
                                        if(m_gameboard[2][idxSacrifice].getFirstPowerAnimal() != PowerEnum.NOMBREUSES_VIES || m_gameboard[2][idxSacrifice].getLastPowerAnimal() != PowerEnum.NOMBREUSES_VIES)
                                        {
                                            aSupprimer.add(idxSacrifice);
                                        }
                                        blood += 1;
                                    }
                                }
                                else
                                {
                                    m_display.print("Le vide n'est pas un animal à sacrifier.");
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
                            m_display.print("Pas assez de sang récolté pour cette carte !");
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
                setMessage("Placement impossible ! La case ciblée est encore occupée.");
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

    public boolean isCard(int i, int j) { return m_gameboard[i][j] != null; }

    public boolean isCardAnimal(int i, int j) { return m_gameboard[i][j].isAnimal();}

    public int getHandSize(){return m_player.getHandSize();}

    public String getCardName(int i, int j) {return m_gameboard[i][j].getName();}

    public int getCardHealthPoints(int i, int j) {return m_gameboard[i][j].getHealthPoints();}

    public int getCardAttack(int i, int j) {return m_gameboard[i][j].getAnimalAttack();}

    public Cartes_animaux getHandAt(int index){return m_player.getHandAt(index);}

    public ArrayList<Cartes_animaux> getHand(){return m_player.getHand();}

    public ArrayList<Cartes_animaux> getDraw(){return m_player.getDraw();}

    public int getDrawSize() {return m_player.getDrawSize();}

    public Player getPlayer(){return m_player;}

    public Opponent getOpponent(){return m_opponent;}

    public void setGame(int match, Opponent opponent, int actualGame) {
        opponent.setGameManager(this);
        m_game = actualGame;
        for(int j=0; j<4; j++)
        {
            m_player.draw();
        }
        if (match == 1) {
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
        m_display.print("Avant de jouer la troisième partie, vous pouvez rajouter une carte dans la pioche parmi les deux cartes ci-dessous ! (tapez 1 ou 2)");
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
            m_display.print(ligneFormatee);
        }
        String choice;
        boolean valide = false;
        while(!valide)
        {
            choice = sc.nextLine();
            if(choice.equals("1"))
            {
                m_player.addInDraw(propositions.get(0));
                valide = true;
            }
            else if(choice.equals("2"))
            {
                m_player.addInDraw(propositions.get(1));
                valide = true;
            }
            else
            {
                m_display.print("Tapez 1 ou 2 c'est pas compliqué non ?");
            }
        }
    }

    public void displaySacrificeStone(Scanner sc)
    {

        m_display.print("Bien, maintenant vous devez choisir une carte à sacrifier ! (si la carte que vous choisissez à un pouvoir, vous pourrez ajouter ce dernier à une autre carte)");
        m_display.print("Tapez le numéro de la carte à sacrifier :");
        for(int i = 0; i < m_player.getDrawSize(); i++)
        {
            String chaine = "";
            Cartes_animaux animal = m_player.getAnimalAtInDraw(i);

            String ligneFormatee = String.format("%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Pouvoir: %-15s",
                    (i + 1),
                    animal.getName(),
                    animal.getHealthPoints(),
                    animal.getAttack(),
                    animal.getBlood(),
                    animal.getBone(),
                    animal.getPowerAt(0).toString()
            );
            m_display.print(ligneFormatee);
        }
        String choice;
        boolean valide = false;
        while(!valide)
        {
            int numChoice;
            choice = sc.nextLine();
            try
            {
                numChoice = Integer.parseInt(choice.trim()) - 1;
            } catch (NumberFormatException e) {
                numChoice = -1;
            }
            if(numChoice >= 0 && numChoice < m_player.getDrawSize())
            {
                valide = true;
                Cartes_animaux toSacrifice = m_player.getDraw().get(numChoice);
                logicSacrificeStone(toSacrifice,numChoice,sc);
            }
            else
            {
                m_display.print("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
            }
        }
    }

    public void logicSacrificeStone(Cartes_animaux toSacrifice, int numChoice, Scanner sc)
    {

        if(toSacrifice.getFirstPower() != PowerEnum.AUCUN)
        {
            PowerEnum toAttribute = toSacrifice.getFirstPower();

            m_display.print("Super, cette carte avait le pouvoir " + toSacrifice.getFirstPower().toString() + " !");
            boolean valide = false;
            String choice;
            m_display.print("Choisissez à quelle carte vous voulez maintenant attribuer ce pouvoir :");

            while(!valide)
            {

                int numChoicePow;
                choice = sc.nextLine();
                try
                {
                    numChoicePow = Integer.parseInt(choice.trim()) -1;
                } catch (NumberFormatException e) {
                    numChoicePow = -1;
                }

                if(numChoicePow >= 0 && numChoicePow < m_player.getDrawSize() && numChoicePow != numChoice)
                {
                    valide = true;
                    m_display.print("Parfait, le pouvoir à bien été ajouté !");
                    m_player.getAnimalAtInDraw(numChoicePow).addPower(toAttribute);
                    m_player.removeAtInDraw(numChoice);
                }
                else
                {
                    m_display.print("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                }
            }
        }
        else
        {
            m_display.print("Malheureusement la carte que vous venez de sacrifier n'avait aucun pouvoir à attribuer... tant pis !");
        }
    }

    public void setCard(Cartes carte, int row, int columns)
    {
        m_gameboard[row][columns] = carte;
    }

    public int getGame(){return m_game;}
}