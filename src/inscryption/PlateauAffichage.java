package inscryption;

import inscryption.cartes.*;
import inscryption.players.Opponent;
import inscryption.players.Player;

import java.util.ArrayList;
import java.util.Random;

import java.util.Random;

public class PlateauAffichage {

    private GameManager m_datas;
    private Opponent m_opponent;
    private Player m_player;
    private Random m_random;

    public PlateauAffichage(GameManager gameManager)
    {
        m_datas = gameManager;
        m_opponent = gameManager.getOpponent();
        m_player = gameManager.getPlayer();
        m_random = new Random();
    }

    public void displayGameboard(String message)
    {
            displayCards(message);
            System.out.println("Votre main :                                                    Pioche");
            System.out.println("                                                             *___________*");
            int pourlaboucle = (6-m_datas.getHand().size());
            if(pourlaboucle<0)
            {
                pourlaboucle = 0;
            }
            for(int j=0; j<m_datas.getHand().size()+pourlaboucle; j++)
            {
                String chaine = "";
                if(m_datas.getHand().size() > j)
                {
                    Cartes_animaux animal = m_datas.getHand().get(j);

                    String ligneFormatee = String.format("%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-10d",
                            (j + 1),
                            animal.getName(),
                            animal.getHealthPoints(),
                            animal.getAttack(),
                            animal.getBlood(),
                            animal.getBone()
                    );
                    if(j==2)
                    {
                        if(m_datas.getDraw().size() >= 10)
                        {
                            chaine += ligneFormatee + "      |     " + m_datas.getDraw().size() + "    |";
                        }
                        else
                        {
                            chaine += ligneFormatee + "      |     " + m_datas.getDraw().size() + "     |";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += ligneFormatee + "      |   cartes  |";
                    }
                    else if(j==5)
                    {
                        chaine += ligneFormatee + "      *___________*";
                    }
                    else if(j<6)
                    {
                        chaine += ligneFormatee + "      |           |";
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
                        if(m_datas.getDraw().size() >= 10)
                        {
                            chaine += "                                                             |     " + m_datas.getDraw().size() + "    |";
                        }
                        else
                        {
                            chaine +=  "                                                             |     " + m_datas.getDraw().size() + "     |";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += "                                                             |   cartes  |";
                    }
                    else if(j!=5)
                    {
                        chaine += "                                                             |           |";
                    }
                    else
                    {
                        chaine += "                                                             *___________*";
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
                    chaine += "      ||       ";
                }
                chaine += "\n";
                for(int j=0; j<4; j++)
                {
                    chaine += "      \\/       ";
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
        Cartes[][] cartes = m_datas.getCards();
        for(int i=0; i<7; i++)
        {
            switch(ligne)
            {
                case 1:
                case 7:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " ************* ";
                        }
                        else {
                            chaine += " *___________* ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                    break;
                case 2:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else
                        {
                            chaine += " | " + cartes[j][l].getName();
                            for(int x = 0; x<(10-cartes[j][l].getName().length()); x++)
                            {
                                chaine += " ";
                            }
                            chaine += "| ";
                        }
                    }
                    if(rangee==1)
                    {
                        chaine += "      Partie n°" + m_datas.getGame() + "\n";
                    }
                    else
                    {
                        chaine += "\n";
                    }
                    ligne++;
                    break;
                case 3:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else
                        {
                            chaine += " |-----------| ";
                        }
                    }
                    if(rangee==1)
                    {
                        chaine += "      Tour n°" + m_datas.getTurn() + "\n";
                    }
                    else
                    {
                        chaine += "\n";
                    }
                    ligne++;
                    break;
                case 4:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            if(rangee == 1)
                            {
                                chaine += " *    A" + (l+1) + "     * ";
                            }
                            else if(rangee == 2)
                            {
                                chaine += " *    B" + (l+1) + "     * ";
                            }
                            else
                            {
                                chaine += " *           * ";
                            }
                        }
                        else
                        {
                            chaine += " | PV : " + cartes[j][l].getHealthPoints() + "    | ";
                        }
                    }
                    if(rangee==1)
                    {
                        chaine += "      Score : " + m_datas.getScore() + "\n";
                    }
                    else
                    {
                        chaine += "\n";
                    }
                    ligne++;
                    break;
                case 5:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else if(cartes[j][l].getAnimals() != null && cartes[j][l].getAnimals().getAttack() > 0)
                        {
                            chaine += " | Att : " + cartes[j][l].getAnimals().getAttack() + "   | ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    if(rangee==1)
                    {
                        if(m_player.getPlayerBones() < 10)
                        {
                            chaine += "      Os obtenus : " + m_player.getPlayerBones() + "\n";
                        }
                        else
                        {
                            chaine += "      Os obtenus : " + m_player.getPlayerBones() + "\n";
                        }
                    }
                    else
                    {
                        chaine += "\n";
                    }
                    ligne++;
                    break;
                case 6:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else if(cartes[j][l].getAnimals() != null && cartes[j][l].getAnimals().isFlying())
                        {
                            chaine += " | Volant    | ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    if(rangee==1)
                    {
                        chaine += "      " + message + "\n";
                    }
                    else
                    {
                        chaine += "\n";
                    }
                    ligne++;

                    break;
            }
        }
        return chaine;
    }

}