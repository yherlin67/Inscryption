package inscryption;

import inscryption.cartes.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

import java.util.Random;

public class PlateauAffichage {

    private GameManager m_datas;
    private Opponent m_opponent;
    private Player m_player;
    private Random m_random;
    private int m_actualTurn;

    public PlateauAffichage(GameManager gameManager)
    {
        m_datas = gameManager;
        m_opponent = gameManager.getOpponent();
        m_player = gameManager.getPlayer();
        m_random = new Random();
        m_actualTurn = 1;
    }

    public void displayGameboard(String message)
    {
            displayCards(message);
            System.out.println("Votre main :                                                                       Pioche");
            System.out.println("                                                                                *___________*");
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
                    Cartes_animaux animal = m_datas.getHandAt(j);

                    String ligneFormatee = String.format("%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-2d Pouvoir: %-15s",
                            (j + 1),
                            animal.getName(),
                            animal.getHealthPoints(),
                            animal.getAttack(),
                            animal.getBlood(),
                            animal.getBone(),
                            animal.getFirstPower().toString()
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
                            chaine += "                                                                                |     " + m_datas.getDraw().size() + "    |";
                        }
                        else
                        {
                            chaine +=  "                                                                                |     " + m_datas.getDraw().size() + "     |";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += "                                                                                |   cartes  |";
                    }
                    else if(j!=5)
                    {
                        chaine += "                                                                                |           |";
                    }
                    else
                    {
                        chaine += "                                                                                *___________*";
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
            String chaine = "";
            chaine = displayRow(chaine, i, message);
            if(i==0)
            {
                for(int j=0; j<4; j++)
                {
                    chaine += "        ||       ";
                }
                chaine += "\n";
                for(int j=0; j<4; j++)
                {
                    chaine += "        \\/       ";
                }
                System.out.println(chaine);
                chaine = "";
            }
            System.out.println(chaine);
        }

    }

    public String displayRow(String chaine, int rangee, String message)
    {
        int ligne = 1;
        int j=rangee;
        StringBuilder chaineBuilder = new StringBuilder(chaine);
        for(int i = 0; i<9; i++)
        {
            switch(ligne)
            {
                case 1:
                case 9:
                    for(int l=0; l<4; l++)
                    {
                        if(!m_datas.isCard(i,j))
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
                    else if(rangee==1 && ligne == 9)
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
                        if(m_datas.isCard(i,j))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else
                        {
                            chaineBuilder.append(" | ").append(m_datas.getCardName(i, j));
                            for(int x = 0; x<(12-m_datas.getCardName(i,j).length()); x++)
                            {
                                chaineBuilder.append(" ");
                            }
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
                        if(m_datas.isCard(i,j))
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
                        if(m_datas.isCard(i,j))
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
                            chaineBuilder.append(" | PV : ").append(m_datas.getCardHealthPoints(i,j)).append("      | ");
                        }
                    }
                    if(rangee==1)
                    {
                        chaineBuilder.append("      Os obtenus : ").append(m_player.getBones()).append("\n");
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
                        if(m_datas.isCard(i,j))
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(m_datas.isCardAnimal(i,j) && m_datas.getCardAttack(i,j) > 0)
                        {
                            chaineBuilder.append(" | Att : ").append(cartes[j][l].getAnimals().getAttack()).append("     | ");
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
                        if(cartes[j][l] == null)
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(cartes[j][l].getAnimals() != null && cartes[j][l].getAnimals().isFlying())
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
                        if(cartes[j][l] == null)
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(cartes[j][l].getAnimals() != null && cartes[j][l].getAnimals().getPowerSize() == 1 && cartes[j][l].getAnimals().getFirstPower() != PowerEnum.AUCUN )
                        {
                            chaineBuilder.append(" | ").append(cartes[j][l].getAnimals().getFirstPower().toString());
                            for(int x = 0; x<(12-cartes[j][l].getAnimals().getFirstPower().toString().length()); x++)
                            {
                                chaineBuilder.append(" ");
                            }
                            chaineBuilder.append("| ");
                        }
                        else
                        {
                            chaineBuilder.append(" |             | ");
                        }
                    }
                    if(rangee==1 && m_datas.getTurn() == m_actualTurn+1)
                    {
                        int playerAttack = 0;
                        int opponentAttack = 0;
                        playerAttack = m_player.getTurnAttack();
                        opponentAttack = m_opponent.getTurnAttack();
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
                        if(cartes[j][l] == null)
                        {
                            chaineBuilder.append(" *             * ");
                        }
                        else if(cartes[j][l].getAnimals() != null && cartes[j][l].getAnimals().getPowerSize() == 2)
                        {
                            chaineBuilder.append(" | ").append(cartes[j][l].getAnimals().getLastPower().toString());
                            for(int x = 0; x<(12-cartes[j][l].getAnimals().getLastPower().toString().length()); x++)
                            {
                                chaineBuilder.append(" ");
                            }
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
}