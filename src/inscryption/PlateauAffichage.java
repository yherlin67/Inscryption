package inscryption;

import inscryption.cartes.Cartes;
import inscryption.cartes.Cartes_animaux;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class PlateauAffichage {

    private PlateauLogic m_datas;

    public PlateauAffichage()
    {
        m_datas = new PlateauLogic();
    }

    public void afficherPlateau()
    {
        while(m_datas.getScore() <= 3)
        {
            System.out.println("Tour n°" + m_datas.getTurn() + " :                                        Score : " + m_datas.getScore() + "\n");
            afficherCartes();
            System.out.println("Votre main :                                                    Pioche\n");
            System.out.println("                                                             *___________*");
            int pourlaboucle = (6-m_datas.getMain().size());
            if(pourlaboucle<0)
            {
                pourlaboucle = 0;
            }
            for(int j=0; j<m_datas.getMain().size()+pourlaboucle; j++)
            {
                String chaine = "";
                if(m_datas.getMain().size() > j)
                {
                    Cartes_animaux animal = m_datas.getMain().get(j);

                    String ligneFormatee = String.format("%-1d. %-12s PV: %-3d Att: %-2d Sang: %-2d Os: %-10d",
                            (j + 1),
                            animal.getNom(),
                            animal.getPointsDeVie(),
                            animal.getAttaque(),
                            animal.getGouttesDeSang(),
                            animal.getOs()
                    );
                    if(j==2)
                    {
                        if(m_datas.getPioche().size() >= 10)
                        {
                            chaine += ligneFormatee + "      |     " + m_datas.getPioche().size() + "    | ";
                        }
                        else
                        {
                            chaine += ligneFormatee + "      |     " + m_datas.getPioche().size() + "     | ";
                        }
                    }
                    else if(j==3)
                    {
                        chaine += ligneFormatee + "      |   cartes  | ";
                    }
                    else if(j==5)
                    {
                        chaine += ligneFormatee + "      *___________* ";
                    }
                    else if(j<6)
                    {
                        chaine += ligneFormatee + "      |           | ";
                    }
                    else
                    {
                        chaine += ligneFormatee;
                    }
                }
                else
                {
                    if(j!=5)
                    {
                        chaine += "                                                             |           |";
                    }
                    else
                    {
                        chaine += "                                                             *___________* ";
                    }
                }
                System.out.println(chaine);
            }
            boolean showErr = false;
            System.out.println("Actions possibles :");
            System.out.println(" [fin] Terminer votre tour");
            System.out.println(" [piocher] Piocher une carte");
            System.out.println(" [placer <numero carte> <position>] Placer une carte sur le plateau");
            Scanner sc = new Scanner(System.in);
            String action = sc.nextLine();
            if(action.equals("fin"))
            {
                m_datas.nextTurn();
                System.out.println("Vous passez au tour suivant");
            }
            if(action.equals("piocher"))
            {
                m_datas.Draw();
                System.out.println("Vous piochez une carte");
            }
            if(action.substring(0,6).equals("placer") && action.length() >= 10)
            {
                int indHand = 0;
                int indBoard = 0;

                try {
                    indHand = parseInt(action.substring(7,8));
                    indBoard = parseInt(action.substring(9,11));
                } catch (NumberFormatException e) {
                    showErr = true;
                    System.out.println("Entrez une chaîne valide !");
                }
                m_datas.placeCard(indHand,indBoard);
                System.out.println("Vous piochez une carte");
            }
            else
            {
                if(showErr)
                {
                    System.out.println("Entrez une chaîne valide !");
                }

            }

        }

    }

    public void afficherCartes()
    {
        for(int i=0; i<3; i++)
        {
            String chaine = "";
            chaine = afficherRangee(chaine, i);
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

    public String afficherRangee(String chaine, int rangee)
    {
        int ligne = 1;
        int j=rangee;
        Cartes[][] cartes = m_datas.getCartes();
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
                            chaine += " | " + cartes[j][l].getNom();
                            for(int x=0; x<(10-cartes[j][l].getNom().length()); x++)
                            {
                                chaine += " ";
                            }
                            chaine += "| ";
                        }
                    }
                    chaine += "\n";
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
                    chaine += "\n";
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
                            chaine += " | PV : " + cartes[j][l].getPointsDeVie() + "    | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                    break;
                case 5:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else if(cartes[j][l].getAnimaux() != null && cartes[j][l].getAnimaux().getAttaque() > 0)
                        {
                            chaine += " | Att : " + cartes[j][l].getAnimaux().getAttaque() + "   | ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                    break;
                case 6:
                    for(int l=0; l<4; l++)
                    {
                        if(cartes[j][l] == null)
                        {
                            chaine += " *           * ";
                        }
                        else if(cartes[j][l].getAnimaux() != null && cartes[j][l].getAnimaux().isVolant())
                        {
                            chaine += " | Volant    | ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;

                    break;
            }
        }
        return chaine;
    }
}