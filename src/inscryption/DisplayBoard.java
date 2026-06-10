package inscryption;

import inscryption.cards.*;
import inscryption.logic.PowerEnum;
import inscryption.players.*;

public class DisplayBoard {

    private int m_actualTurn;

    public DisplayBoard()
    {
        m_actualTurn = 1;
    }

    // Ajout des paramètres nécessaires pour dessiner le plateau
    public void displayGameboard(String message, Player player, Opponent opponent, Card[][] gameboard, int game, int turn, int score)
    {
        displayCards(message, player, opponent, gameboard, game, turn, score);
        System.out.println("Votre main :                                                                                          Pioche");
        System.out.println("                                                                                                   *___________*");

        int handSize = player.getHandSize();
        int drawSize = player.getDrawSize();
        //Si la main fait moins de 6 cartes, il faudra afficher des lines supplémentaires pour faire un bel affichage
        int forLoop = Math.max(6 - handSize, 0);

        for(int j = 0; j < handSize + forLoop; j++)
        {
            String str = "";
            if(handSize > j)
            {
                String power = "";
                String line = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Volant: %-35s";
                if(player.getCardFirstPowerInHand(j) != PowerEnum.NONE)
                {
                    power += player.getCardFirstPowerInHand(j);
                    //-1d : entier affiché à gauche sur un caractère ... (s pour chaîne)
                    line = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Volant: %-3s  Pouvoir: %-21s";
                }
                if(player.getCardLastPowerInHand(j) != PowerEnum.NONE && player.getCardFirstPowerInHand(j) != player.getCardLastPowerInHand(j))
                {
                    power += " / " + player.getCardLastPowerInHand(j);
                }
                String formatedLine = String.format(line,
                        (j + 1),
                        player.getCardNameInHand(j),
                        player.getCardHealthPointInHand(j),
                        player.getCardAttackInHand(j),
                        player.getCardBloodInHand(j),
                        player.getCardBonesInHand(j),
                        player.getCardFlyInHand(j),
                        power
                );

                if(j == 2) {
                    if(drawSize >= 10) { str += formatedLine + "        |     " + drawSize + "    |"; }
                    else { str += formatedLine + "        |     " + drawSize + "     |"; }
                } else if(j == 3) {
                    str += formatedLine + "        |  carte(s) |";
                } else if(j == 5) {
                    str += formatedLine + "        *___________*";
                } else if(j < 6) {
                    str += formatedLine + "        |           |";
                } else {
                    str += formatedLine;
                }
            }
            else
            {
                if(j == 2) {
                    if(drawSize >= 10) { str += "                                                                                                   |     " + drawSize + "    |"; }
                    else { str += "                                                                                                   |     " + drawSize + "     |"; }
                } else if(j == 3) {
                    str += "                                                                                                   |  carte(s) |";
                } else if(j != 5) {
                    str += "                                                                                                   |           |";
                } else {
                    str += "                                                                                                   *___________*";
                }
            }
            System.out.println(str);
        }
        System.out.println("\nActions possibles :");
        System.out.println(" [fin] Terminer votre tour");
        System.out.println(" [piocher] Piocher une carte");
        System.out.println(" [placer <numero carte> <position>] Placer une carte sur le plateau");
    }

    public void displayCards(String message, Player player, Opponent opponent, Card[][] gameboard, int game, int turn, int score)
    {
        //On affiche toutes les cartes du plateau et les flèches vers le bas (line de transition) dans le cas où i = 0.
        for(int i = 0; i < 3; i++)
        {
            StringBuilder str = new StringBuilder();
            str = new StringBuilder(displayRow(str.toString(), i, message, player, opponent, gameboard, game, turn, score));
            if(i == 0)
            {
                str.append("        ||       ".repeat(4));
                str.append("\n");
                str.append("        \\/       ".repeat(4));
                System.out.println(str);
                str = new StringBuilder();
            }
            System.out.println(str);
        }
    }

    public String displayRow(String str, int row, String message, Player player, Opponent opponent, Card[][] gameboard, int game, int turn, int score)
    {
        //row représente la rangée de l'emplacement de la carte sur le tableau gameboard, la line sur le gameboard enfaite
        StringBuilder strBuilder = new StringBuilder(str);

        // Dans la boucle ci-dessous, i représente directement l'index de la line sur le plateau (de 0 à 8, on parle de la version affichée du plateau ici)
        for(int i = 0; i < 9; i++)
        {
            switch(i) // On switch sur i directement (0 à 8)
            {
                //Même action pour case 0 et 8
                case 0: // Bordure haute
                case 8: // Bordure basse
                    //On balaie les cartes sur la line
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *************** ");
                        } else {
                            strBuilder.append(" *_____________* ");
                        }
                    }
                    // Si on est sur la row 1 sur la line 0
                    if(row == 1 && i == 0) {
                        strBuilder.append("      Partie n°").append(game).append("\n");
                    } else if(row == 1) {
                        strBuilder.append("      ").append(message).append("\n");
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 1: // line 2 (Nom)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else {
                            String name = gameboard[row][l].getName();
                            strBuilder.append(" | ").append(name);
                            strBuilder.append(" ".repeat(Math.max(0, (12 - name.length()))));
                            strBuilder.append("| ");
                        }
                    }
                    if(row == 1) {
                        strBuilder.append("      Tour n°").append(turn).append("\n");
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 2: // line 3 (Séparateur)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else {
                            strBuilder.append(" |-------------| ");
                        }
                    }
                    if(row == 1) {
                        strBuilder.append("      Score : ").append(score).append("\n");
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 3: // line 4 (PV)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            if(row == 1) { strBuilder.append(" *     A").append(l + 1).append("      * "); }
                            else if(row == 2) { strBuilder.append(" *     B").append(l + 1).append("      * "); }
                            else { strBuilder.append(" *             * "); }
                        } else {
                            strBuilder.append(" | PV : ").append(gameboard[row][l].getHealthPoints()).append("      | ");
                        }
                    }
                    if(row == 1) {
                        strBuilder.append("      Os obtenus : ").append(player.getBones()).append("\n");
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 4: // line 5 (Attaque)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else if(gameboard[row][l].isAnimal() && gameboard[row][l].getAnimalAttack() > 0) {
                            strBuilder.append(" | Att : ").append(gameboard[row][l].getAnimalAttack()).append("     | ");
                        } else {
                            strBuilder.append(" |             | ");
                        }
                    }
                    strBuilder.append("\n");
                    break;

                case 5: // line 6 (Volant)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else if(gameboard[row][l].isAnimal() && gameboard[row][l].getAnimalFly()) {
                            strBuilder.append(" | Volant      | ");
                        } else {
                            strBuilder.append(" |             | ");
                        }
                    }
                    if(row == 1) {
                        strBuilder.append("      Dégats infligés au tour précédent :\n");
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 6: // line 7 (Pouvoir 1 + Bilan du dernier tour)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else if(gameboard[row][l].getPowerSizeAnimal() == 1 && gameboard[row][l].getFirstPowerAnimal() != PowerEnum.NONE) {
                            String powerStr = gameboard[row][l].getFirstPowerAnimal().toString();
                            strBuilder.append(" | ").append(powerStr);
                            strBuilder.append(" ".repeat(Math.max(0, (12 - powerStr.length()))));
                            strBuilder.append("| ");
                        } else {
                            strBuilder.append(" |             | ");
                        }
                    }
                    //Affichage des dégâts infligés au dernier tour
                    if(row == 1 && turn == m_actualTurn + 1) {
                        int playerAttack = player.getTurnAttack();
                        int opponentAttackTurn = opponent.getTurnAttack();
                        strBuilder.append("      Joueur : ").append(playerAttack).append("| Adversaire : ").append(opponentAttackTurn).append("\n");
                        m_actualTurn++;
                    } else {
                        strBuilder.append("\n");
                    }
                    break;

                case 7: // line 8 (Pouvoir 2)
                    //On balaie les cartes sur la line sur la row
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[row][l] == null) {
                            strBuilder.append(" *             * ");
                        } else if(gameboard[row][l].getPowerSizeAnimal() == 2) {
                            String powerStr = gameboard[row][l].getLastPowerAnimal().toString();
                            strBuilder.append(" | ").append(powerStr);
                            strBuilder.append(" ".repeat(Math.max(0, (12 - powerStr.length()))));
                            strBuilder.append("| ");
                        } else {
                            strBuilder.append(" |             | ");
                        }
                    }
                    strBuilder.append("\n");
                    break;
            }
        }
        return strBuilder.toString();
    }

    public void print(String str)
    {
        System.out.println(str);
    }

    @Override
    public String toString(){
        return "PlateauAffichage";
    }
}