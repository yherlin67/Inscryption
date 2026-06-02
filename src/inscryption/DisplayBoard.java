package inscryption;

import inscryption.cards.*;
import inscryption.players.*;

public class DisplayBoard {

    private int m_actualTurn;

    public DisplayBoard()
    {
        m_actualTurn = 1;
    }

    // Ajout des paramètres nécessaires pour dessiner le plateau
    public void displayGameboard(String message, Player player, Opponent opponent, Cards[][] gameboard, int game, int turn, int score)
    {
        displayCards(message, player, opponent, gameboard, game, turn, score);
        System.out.println("Votre main :                                                                                          Pioche");
        System.out.println("                                                                                                   *___________*");

        int handSize = player.getHandSize();
        int drawSize = player.getDrawSize();
        int pourlaboucle = (6 - handSize);

        if(pourlaboucle < 0)
        {
            pourlaboucle = 0;
        }

        for(int j = 0; j < handSize + pourlaboucle; j++)
        {
            String chaine = "";
            if(handSize > j)
            {
                String power = "";
                String ligne = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Volant: %-35s";
                if(player.getCardFirstPowerInHand(j) != PowerEnum.AUCUN)
                {
                    power += player.getCardFirstPowerInHand(j);
                    ligne = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Volant: %-3s  Pouvoir: %-21s";
                }
                if(player.getCardLastPowerInHand(j) != PowerEnum.AUCUN && player.getCardFirstPowerInHand(j) != player.getCardLastPowerInHand(j))
                {
                    power += " / " + player.getCardLastPowerInHand(j);
                }
                String ligneFormatee = String.format(ligne,
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
                    if(drawSize >= 10) { chaine += ligneFormatee + "        |     " + drawSize + "    |"; }
                    else { chaine += ligneFormatee + "        |     " + drawSize + "     |"; }
                } else if(j == 3) {
                    chaine += ligneFormatee + "        |  carte(s) |";
                } else if(j == 5) {
                    chaine += ligneFormatee + "        *___________*";
                } else if(j < 6) {
                    chaine += ligneFormatee + "        |           |";
                } else {
                    chaine += ligneFormatee;
                }
            }
            else
            {
                if(j == 2) {
                    if(drawSize >= 10) { chaine += "                                                                                                   |     " + drawSize + "    |"; }
                    else { chaine += "                                                                                                   |     " + drawSize + "     |"; }
                } else if(j == 3) {
                    chaine += "                                                                                                   |  carte(s) |";
                } else if(j != 5) {
                    chaine += "                                                                                                   |           |";
                } else {
                    chaine += "                                                                                                   *___________*";
                }
            }
            System.out.println(chaine);
        }
        System.out.println("\nActions possibles :");
        System.out.println(" [fin] Terminer votre tour");
        System.out.println(" [piocher] Piocher une carte");
        System.out.println(" [placer <numero carte> <position>] Placer une carte sur le plateau");
    }

    public void displayCards(String message, Player player, Opponent opponent, Cards[][] gameboard, int game, int turn, int score)
    {
        for(int i = 0; i < 3; i++)
        {
            StringBuilder chaine = new StringBuilder();
            chaine = new StringBuilder(displayRow(chaine.toString(), i, message, player, opponent, gameboard, game, turn, score));
            if(i == 0)
            {
                chaine.append("        ||       ".repeat(4));
                chaine.append("\n");
                chaine.append("        \\/       ".repeat(4));
                System.out.println(chaine);
                chaine = new StringBuilder();
            }
            System.out.println(chaine);
        }
    }

    public String displayRow(String chaine, int rangee, String message, Player player, Opponent opponent, Cards[][] gameboard, int game, int turn, int score)
    {
        int ligne = 1;
        StringBuilder chaineBuilder = new StringBuilder(chaine);
        for(int i = 0; i < 9; i++)
        {
            switch(ligne)
            {
                case 1:
                case 9:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *************** ");
                        } else {
                            chaineBuilder.append(" *_____________* ");
                        }
                    }
                    if(rangee == 1 && ligne == 1) {
                        chaineBuilder.append("      Partie n°").append(game).append("\n");
                    } else if(rangee == 1) {
                        chaineBuilder.append("      ").append(message).append("\n");
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 2:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else {
                            String name = gameboard[rangee][l].getName();
                            chaineBuilder.append(" | ").append(name);
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - name.length()))));
                            chaineBuilder.append("| ");
                        }
                    }
                    if(rangee == 1) {
                        chaineBuilder.append("      Tour n°").append(turn).append("\n");
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 3:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else {
                            chaineBuilder.append(" |-------------| ");
                        }
                    }
                    if(rangee == 1) {
                        chaineBuilder.append("      Score : ").append(score).append("\n");
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 4:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            if(rangee == 1) { chaineBuilder.append(" *     A").append(l + 1).append("      * "); }
                            else if(rangee == 2) { chaineBuilder.append(" *     B").append(l + 1).append("      * "); }
                            else { chaineBuilder.append(" *             * "); }
                        } else {
                            chaineBuilder.append(" | PV : ").append(gameboard[rangee][l].getHealthPoints()).append("      | ");
                        }
                    }
                    if(rangee == 1) {
                        chaineBuilder.append("      Os obtenus : ").append(player.getBones()).append("\n");
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 5:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else if(gameboard[rangee][l].isAnimal() && gameboard[rangee][l].getAnimalAttack() > 0) {
                            chaineBuilder.append(" | Att : ").append(gameboard[rangee][l].getAnimalAttack()).append("     | ");
                        } else {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    chaineBuilder.append("\n");
                    ligne++;
                    break;
                case 6:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else if(gameboard[rangee][l].isAnimal() && gameboard[rangee][l].getAnimalFly()) {
                            chaineBuilder.append(" | Volant      | ");
                        } else {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    if(rangee == 1) {
                        chaineBuilder.append("      Dégats infligés au tour précédent :\n");
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 7:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else if(gameboard[rangee][l].getPowerSizeAnimal() == 1 && gameboard[rangee][l].getFirstPowerAnimal() != PowerEnum.AUCUN ) {
                            String powerStr = gameboard[rangee][l].getFirstPowerAnimal().toString();
                            chaineBuilder.append(" | ").append(powerStr);
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - powerStr.length()))));
                            chaineBuilder.append("| ");
                        } else {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    if(rangee == 1 && turn == m_actualTurn + 1) {
                        int playerAttack = player.getTurnAttack();
                        int opponentAttackTurn = opponent.getTurnAttack();
                        chaineBuilder.append("      Joueur : ").append(playerAttack).append("| Adversaire : ").append(opponentAttackTurn).append("\n");
                        m_actualTurn++;
                    } else {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 8:
                    for(int l = 0; l < 4; l++)
                    {
                        if(gameboard[rangee][l] == null) {
                            chaineBuilder.append(" *             * ");
                        } else if(gameboard[rangee][l].getPowerSizeAnimal() == 2) {
                            String powerStr = gameboard[rangee][l].getLastPowerAnimal().toString();
                            chaineBuilder.append(" | ").append(powerStr);
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - powerStr.length()))));
                            chaineBuilder.append("| ");
                        } else {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    chaineBuilder.append("\n");
                    ligne++;
                    break;
            }
        }
        return chaineBuilder.toString();
    }

    public void print(String chaine)
    {
        System.out.println(chaine);
    }

    @Override
    public String toString(){
        return "PlateauAffichage";
    }
}