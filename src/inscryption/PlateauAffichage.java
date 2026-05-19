package inscryption;

import java.util.Scanner;

public class PlateauAffichage {

    private PlateauLogic m_datas;

    public PlateauAffichage()
    {
        m_datas = new PlateauLogic();
    }

    public boolean isVictorieux() {
        return m_victoire;
    }

    public void afficherPlateau()
    {
        while(m_tour <= 3)
        {
            System.out.println("Tour n°" + m_tour + " :                                        Score : " + m_score + "\n");
            afficherCartes();
            System.out.println("Votre main :                                                    Pioche\n");
            for(int i=0; i<m_main.size(); i++)
            {
                System.out.println((i+1) + ". " + m_main.get(i).getNom() + "    " + "PV: ");
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
        System.out.println("Actions possibles :");
        System.out.println(" [fin] Terminer votre tour");
        System.out.println(" [piocher] Piocher une carte");
        System.out.println(" [placer <numero carte> <position>] Placer une carte sur le plateau");
        Scanner sc = new Scanner(System.in);
        String action = sc.nextLine();
        if(action.equals("fin"))
        {
            System.out.println("Vous passez au tour suivant");
            m_tour ++;
        }
        else if(action.equals("piocher"))
        {
            m_main.add(m_pioche.getLast());
            m_pioche.removeLast();
            System.out.println("Vous piochez une carte");
            m_tour ++;
        }
        else
        {
            System.out.println("Entrez une chaîne valide !");
        }
    }

    public String afficherRangee(String chaine, int rangee)
    {
        int ligne = 1;
        int j=rangee;
        for(int i=0; i<7; i++)
        {
            switch(ligne)
            {
                case 1:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
                        {
                            chaine += " ************* ";
                        }
                        else {
                            chaine += " *___________* ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                case 2:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
                        {
                            chaine += " *           * ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                case 3:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
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
                case 4:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
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
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                case 5:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
                        {
                            chaine += " *           * ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                case 6:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
                        {
                            chaine += " *           * ";
                        }
                        else
                        {
                            chaine += " |           | ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
                case 7:
                    for(int l=0; l<4; l++)
                    {
                        if(m_cartes[j][l] == "")
                        {
                            chaine += " ************* ";
                        }
                        else
                        {
                            chaine += " *___________* ";
                        }
                    }
                    chaine += "\n";
                    ligne++;
            }
        }
        return chaine;
    }
}
