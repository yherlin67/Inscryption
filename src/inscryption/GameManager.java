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
    private final Random m_random;
    private final PlateauAffichage m_display;

    private final Cartes[][] m_gameboard;

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

    public void manageAction(Scanner sc)
    {
        //Retourne si on doit afficher une erreur ou non !
        String action = sc.nextLine();
        if(action.equals("fin"))
        {
            m_player.attack();
            if(gameReview() != null)
            {
                return;
            }
            m_opponent.play();
            m_draw = true;
            m_opponent.attack();
            nextTurn();
            setMessage("Votre adversaire à joué, à votre tour de jouer maintenant...");
            return;
        }
        if(action.equals("piocher") && m_draw && !m_player.isDrawEmpty())
        {
            m_player.draw();
            m_draw = false;
            setMessage("Vous piochez une carte");
        }
        else if(m_player.isDrawEmpty())
        {
            setMessage("La pioche est vide !");
        }
        else if(action.equals("piocher") && !m_draw)
        {
            setMessage("Vous avez déjà pioché voyons ! Ne soyez pas trop gourmand...");
        }
        else if(action.length()<6)
        {
            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
        }
        else if(action.startsWith("placer") && action.length() >= 10)
        {
            int indHand;
            int indBoard;

            if(action.length()==11 && action.charAt(9) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,8)) - 1;
                    indBoard = parseInt(action.substring(10,11)) - 1;
                } catch (NumberFormatException e) {
                    return;
                }

                int requiedDrop = m_player.getBloodAt(indHand);
                int requiedBones = m_player.getBonesAt(indHand);

                if(requiedDrop == 0 && requiedBones == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        setMessage("Vous placer la carte");
                        placeCard(indHand, indBoard);
                        return;
                    } else {
                        setMessage("Ah non ! C'est déjà occupé ici !");
                        return;
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
                            return;
                        } else {
                            setMessage("Ah non ! C'est déjà occupé ici !");
                            return;
                        }
                    }
                    else
                    {
                        setMessage("Vous n'avez pas assez d'os pour placer cette carte !");
                        return;
                    }
                }
                else if(requiedDrop > 0)
                {
                    if (m_gameboard[2][indBoard] != null && "Chat".equals(m_gameboard[2][indBoard].getName())) {
                        setMessage("Ah non ! Un Chat occupe déjà cette case, placement impossible !");
                        return;
                    }

                    StringBuilder enCours = new StringBuilder();
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice"))
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
                                        enCours.append(m_gameboard[2][idxSacrifice].getName()).append(" ");
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
                            return;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            m_display.print("Je crois que vous ne pouvez pas faire ça dans ce jeu..");
                        }
                    }

                    if (blood < requiedDrop) {
                        m_display.print("Pas assez de sang récolté pour cette carte !");
                        return;
                    }

                    for(int col : aSupprimer) {
                        m_gameboard[2][col] = null;
                        m_player.increaseBones();
                    }

                    if(m_gameboard[2][indBoard] == null) {
                        placeCard(indHand, indBoard);
                        setMessage("Vous placer la carte.");
                        return;
                    } else {
                        setMessage("Placement impossible ! La case ciblée est encore occupée.");
                        return;
                    }
                }
                setMessage("Placement impossible ! La case ciblée est encore occupée.");
            }
            else if(action.length()==12 && action.charAt(10) == 'B')
            {
                try {
                    indHand = parseInt(action.substring(7,9)) - 1;
                    indBoard = parseInt(action.substring(11,12)) - 1;
                } catch (NumberFormatException e) {
                    return;
                }

                int requiedDrop = m_player.getBloodAt(indHand);

                if(requiedDrop == 0)
                {
                    if(m_gameboard[2][indBoard] == null) {
                        placeCard(indHand, indBoard);
                        setMessage("Vous placer la carte.");
                        return;
                    } else {
                        setMessage("Ah non ! C'est déjà occupé ici !");
                        return;
                    }
                }
                else if(requiedDrop > 0)
                {
                    if (m_gameboard[2][indBoard] != null && "Chat".equals(m_gameboard[2][indBoard].getName())) {
                        setMessage("Ah non ! Un Chat occupe déjà cette case, placement impossible !");
                        return;
                    }

                    StringBuilder enCours = new StringBuilder();
                    ArrayList<Integer> aSupprimer = new ArrayList<>();
                    int blood = 0;

                    while(!action.equals("valider sacrifice"))
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
                                        enCours.append(m_gameboard[2][idxSacrifice].getName()).append(" ");
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
                            return;
                        }
                        else if (!action.equals("valider sacrifice"))
                        {
                            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
                        }
                    }

                    if (blood < requiedDrop) {
                        m_display.print("Pas assez de sang récolté pour cette carte !");
                        return;
                    }

                    for(int col : aSupprimer) {
                        m_gameboard[2][col] = null;
                        m_player.increaseBones();
                    }

                    if(m_gameboard[2][indBoard] == null) {
                        setMessage("Vous placer la carte");
                        placeCard(indHand, indBoard);
                        return;
                    } else {
                        setMessage("Placement impossible ! La case ciblée est encore occupée.");
                        return;
                    }
                }
                setMessage("Placement impossible ! La case ciblée est encore occupée.");
            }
            else
            {
                setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
            }
        }
        else
        {
            setMessage("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
        }
    }

    public void nextTurn()
    {
        m_turn++;
    }

    public void placeCard(int indHand,int indBoard)
    {
        m_gameboard[2][indBoard] = m_player.removeCard(indHand);
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

    public boolean isCard(int i, int j) { return m_gameboard[i][j] != null; }

    public boolean isCardAnimal(int i, int j) { return m_gameboard[i][j] != null && m_gameboard[i][j].isAnimal();}

    public int getHandSize(){return m_player.getHandSize();}

    public String getCardName(int i, int j) {
        return m_gameboard[i][j].getName();
    }

    public int getCardHealthPoints(int i, int j) {
        if(m_gameboard[i][j] != null)
        {
            return m_gameboard[i][j].getHealthPoints();
        }
        else
        {
            return 0;
        }
    }

    public int getCardAttack(int i, int j)
    {
        if(m_gameboard[i][j] != null)
        {
            return m_gameboard[i][j].getAnimalAttack();
        }
        else
        {
            return 0;
        }
    }

    public boolean getCardFly(int i, int j){return m_gameboard[i][j] != null && m_gameboard[i][j].getAnimalFly();}

    public int getCardPowerSize(int i, int j){if(m_gameboard[i][j] != null)
    {
        return m_gameboard[i][j].getPowerSizeAnimal();
    }
    else
    {
        return 0;
    }}

    public PowerEnum getCardPowerFirst(int i, int j){
        if(m_gameboard[i][j] != null)
        {
            return m_gameboard[i][j].getFirstPowerAnimal();
        }
        else
        {
            return null;
        }}

    public PowerEnum getCardPowerLast(int i, int j){
        if(m_gameboard[i][j] != null)
        {
            return m_gameboard[i][j].getLastPowerAnimal();
        }
        else
        {
            return null;
        }}

    public void cardTakeDamage(int i, int j, int degats) {m_gameboard[i][j].takeDamage(degats);}

    public int getDrawSize() {return m_player.getDrawSize();}

    public String getCardNameInHand(int index){return m_player.getCardNameInHand(index);}

    public int getCardAttackInHand(int index){return m_player.getCardAttackInHand(index);}

    public int getCardHealthPointInHand(int index){return m_player.getCardHealthPointInHand(index);}

    public int getCardBloodInHand(int index){return m_player.getCardBloodInHand(index);}

    public int getCardBonesInHand(int index){return m_player.getCardBonesInHand(index);}

    public PowerEnum getCardFirstPowerInHand(int index){return m_player.getCardFirstPowerInHand(index);}

    public PowerEnum getCardLastPowerInHand(int index){return m_player.getCardLastPowerInHand(index);}

    public void moveCardToRow1(int col) {m_gameboard[1][col] = m_gameboard[0][col];}

    public void moveCard(int row, int fromCol, int toCol) {
        m_gameboard[row][toCol] = m_gameboard[row][fromCol];
        m_gameboard[row][fromCol] = null;
    }

    public int getGame(){return m_game;}

    public int getAnimalAttack(int row, int columns)
    {
        return m_gameboard[row][columns].getAnimalAttack();
    }

    public int getPlayerBones() {return m_player.getBones();}

    public int getPlayerTurnAttack() {return m_player.getTurnAttack();}

    public int getOpponentTurnAttack() { return m_opponent.getTurnAttack();}

    public void setGame(int actualGame) {
        m_game = actualGame;
        for(int j=0; j<4; j++)
        {
            m_player.draw();
        }
        if(actualGame == 1)
        {
            m_gameboard[2][1] = new Sapin();
        }
        else if(actualGame == 2)
        {
            m_gameboard[2][0] = new Rocher();
        }
        else
        {
            m_gameboard[2][1] = new Rocher();
            m_gameboard[2][3] = new Sapin();
        }

    }

    public void proposeAddToDraw(Scanner sc)
    {
        m_display.print("Avant de jouer la troisième partie, vous pouvez rajouter une carte dans la pioche parmi les deux cartes ci-dessous ! (tapez 1 ou 2)");
        Cartes_animaux proposition;
        ArrayList<Cartes_animaux> propositions = new ArrayList<>();
        ArrayList<Cartes_animaux> temp = new ArrayList<>();
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
                m_player.addInDraw(propositions.getFirst());
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
                numChoice = parseInt(choice.trim()) - 1;
            } catch (NumberFormatException e) {
                numChoice = -1;
            }
            if(numChoice >= 0 && numChoice < m_player.getDrawSize())
            {
                valide = true;
                logicSacrificeStone(numChoice,sc);
            }
            else
            {
                m_display.print("Je crois que vous ne pouvez pas faire ça dans ce jeu.");
            }
        }
    }

    public void logicSacrificeStone(int numChoice, Scanner sc)
    {

        if(m_player.getCardPowerFirst(numChoice) != PowerEnum.AUCUN)
        {
            PowerEnum toAttribute = m_player.getCardPowerFirst(numChoice);

            m_display.print("Super, cette carte avait le pouvoir " + toAttribute.toString() + " !");
            boolean valide = false;
            String choice;
            m_display.print("Choisissez à quelle carte vous voulez maintenant attribuer ce pouvoir :");

            while(!valide)
            {

                int numChoicePow;
                choice = sc.nextLine();
                try
                {
                    numChoicePow = parseInt(choice.trim()) -1;
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
    
    @Override
    public String toString(){
        return "GameManager";
    }
}