package inscryption;

public class DisplayBoard {

    private final GameManager m_datas;
    private int m_actualTurn;

    public DisplayBoard(GameManager gameManager)
    {
        m_datas = gameManager;
        m_actualTurn = 1;
    }

    public void displayGameboard(String message)
    {
            displayCards(message);
            System.out.println("Votre main :                                                                             Pioche");
            System.out.println("                                                                                      *___________*");
            int pourlaboucle = (6-m_datas.getHandSize());
            if(pourlaboucle<0)
            {
                pourlaboucle = 0;
            }
            for(int j=0; j<m_datas.getHandSize()+pourlaboucle; j++)
            {
                String chaine = "";
                if(m_datas.getHandSize() > j)
                {
                    String power = "";
                    String ligne = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-33d";
                    if(m_datas.getCardFirstPowerInHand(j) != PowerEnum.AUCUN)
                    {
                        power += m_datas.getCardFirstPowerInHand(j);
                        ligne = "%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Pouvoir: %-21s";
                    }
                    if(m_datas.getCardLastPowerInHand(j) != PowerEnum.AUCUN && m_datas.getCardFirstPowerInHand(j) != m_datas.getCardLastPowerInHand(j))
                    {
                        power += " / " + m_datas.getCardLastPowerInHand(j);
                    }
                    String ligneFormatee = String.format(ligne,
                            (j + 1),
                            m_datas.getCardNameInHand(j),
                            m_datas.getCardHealthPointInHand(j),
                            m_datas.getCardAttackInHand(j),
                            m_datas.getCardBloodInHand(j),
                            m_datas.getCardBonesInHand(j),
                            power
                    );
                    if(j==2)
                    {
                        if(m_datas.getDrawSize() >= 10)
                        {
                            chaine += ligneFormatee + "        |     " + m_datas.getDrawSize() + "    |";
                        }
                        else
                        {
                            chaine += ligneFormatee + "        |     " + m_datas.getDrawSize() + "     |";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += ligneFormatee + "        |   cartes  |";
                    }
                    else if(j==5)
                    {
                        chaine += ligneFormatee + "        *___________*";
                    }
                    else if(j<6)
                    {
                        chaine += ligneFormatee + "        |           |";
                    }
                    else
                    {
                        chaine += ligneFormatee;
                    }
                }
                else
                {
                    if(j==2)
                    {
                        if(m_datas.getDrawSize() >= 10)
                        {
                            chaine += "                                                                                      |     " + m_datas.getDrawSize() + "    |";
                        }
                        else
                        {
                            chaine += "                                                                                      |     " + m_datas.getDrawSize() + "     |";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += "                                                                                      |   cartes  |";
                    }
                    else if(j!=5)
                    {
                        chaine += "                                                                                      |           |";
                    }
                    else
                    {
                        chaine += "                                                                                      *___________*";
                    }
                }
                System.out.println(chaine);
            }
            System.out.println("\nActions possibles :");
            System.out.println(" [fin] Terminer votre tour");
            System.out.println(" [piocher] Piocher une carte");
            System.out.println(" [placer <numero carte> <position>] Placer une carte sur le plateau");
    }

    public void displayCards(String message)
    {
        for(int i=0; i<3; i++)
        {
            StringBuilder chaine = new StringBuilder();
            chaine = new StringBuilder(displayRow(chaine.toString(), i, message));
            if(i==0)
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

    public String displayRow(String chaine, int rangee, String message)
    {
        int ligne = 1;
        StringBuilder chaineBuilder = new StringBuilder(chaine);
        for(int i = 0; i<9; i++)
        {
            switch(ligne)
            {
                case 1:
                case 9:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *************** ");
                        }
                        else {
                            chaineBuilder.append(" *_____________* ");
                        }
                    }
                    if(rangee==1 && ligne == 1)
                    {
                        chaineBuilder.append("      Partie n°").append(m_datas.getGame()).append("\n");
                    }
                    else if(rangee == 1)
                    {
                        chaineBuilder.append("      ").append(message).append("\n");
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 2:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else
                        {
                            chaineBuilder.append(" | ").append(m_datas.getCardName(rangee, l));
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - m_datas.getCardName(rangee, l).length()))));
                            chaineBuilder.append("| ");
                        }
                    }
                    if(rangee==1)
                    {
                        chaineBuilder.append("      Tour n°").append(m_datas.getTurn()).append("\n");
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 3:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else
                        {
                            chaineBuilder.append(" |-------------| ");
                        }
                    }
                    if(rangee==1)
                    {
                        chaineBuilder.append("      Score : ").append(m_datas.getScore()).append("\n");
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 4:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            if(rangee == 1)
                            {
                                chaineBuilder.append(" *     A").append(l + 1).append("      * ");
                            }
                            else if(rangee == 2)
                            {
                                chaineBuilder.append(" *     B").append(l + 1).append("      * ");
                            }
                            else
                            {
                                chaineBuilder.append(" *             * ");
                            }
                        }
                        else
                        {
                            chaineBuilder.append(" | PV : ").append(m_datas.getCardHealthPoints(rangee, l)).append("      | ");
                        }
                    }
                    if(rangee==1)
                    {
                        chaineBuilder.append("      Os obtenus : ").append(m_datas.getPlayerBones()).append("\n");
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;
                    break;
                case 5:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(m_datas.isCardAnimal(rangee, l) && m_datas.getCardAttack(rangee, l) > 0)
                        {
                            chaineBuilder.append(" | Att : ").append(m_datas.getCardAttack(rangee, l)).append("     | ");
                        }
                        else
                        {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    chaineBuilder.append("\n");
                    ligne++;
                    break;
                case 6:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(m_datas.isCard(rangee,l) && m_datas.getCardFly(rangee,l))
                        {
                            chaineBuilder.append(" | Volant      | ");
                        }
                        else
                        {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    if(rangee==1)
                    {
                        chaineBuilder.append("      Dégats infligés au tour précédent :\n");
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;

                    break;
                case 7:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(m_datas.isCard(rangee, l) && m_datas.getCardPowerSize(rangee,l) == 1 && m_datas.getCardPowerFirst(rangee,l) != PowerEnum.AUCUN )
                        {
                            chaineBuilder.append(" | ").append(m_datas.getCardPowerFirst(rangee, l).toString());
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - m_datas.getCardPowerFirst(rangee, l).toString().length()))));
                            chaineBuilder.append("| ");
                        }
                        else
                        {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    if(rangee==1 && m_datas.getTurn() == m_actualTurn+1)
                    {
                        int playerAttack;
                        int opponentAttack;
                        playerAttack = m_datas.getPlayerTurnAttack();
                        opponentAttack = m_datas.getOpponentTurnAttack();
                        chaineBuilder.append("      Joueur : ").append(playerAttack).append("| Adversaire : ").append(opponentAttack).append("\n");
                        m_actualTurn++;
                    }
                    else
                    {
                        chaineBuilder.append("\n");
                    }
                    ligne++;

                    break;
                case 8:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(rangee,l))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(m_datas.isCard(rangee,l) && m_datas.getCardPowerSize(rangee,l) == 2)
                        {
                            chaineBuilder.append(" | ").append(m_datas.getCardPowerLast(rangee,l).toString());
                            chaineBuilder.append(" ".repeat(Math.max(0, (12 - m_datas.getCardPowerLast(rangee, l).toString().length()))));
                            chaineBuilder.append("| ");
                        }
                        else
                        {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    chaineBuilder.append("\n");
                    ligne++;

                    break;
            }
        }
        chaine = chaineBuilder.toString();
        return chaine;
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