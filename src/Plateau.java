import Cartes.*;

import java.util.Random;
import java.util.ArrayList;

public class Plateau {
    private boolean m_victoire;
    private int m_score;
    private int m_tour;
    private String[][] m_cartes = {
            {"Louveteau", "", "Moineau", ""},
            {"", "", "", ""},
            {"", "Rocher", "", ""}
    };
    private ArrayList<Cartes_animaux> m_main = new ArrayList<Cartes_animaux>();
    private ArrayList<Cartes_animaux> m_pioche = new ArrayList<Cartes_animaux>();
    private Random m_aleatoire = new Random();

    public Plateau()
    {
        m_tour = 1;
        m_score = 0;
        ArrayList<Cartes_animaux> temp = new ArrayList<Cartes_animaux>();
        for(int i=0; i<6; i++)
        {
            Ecureuil e = new Ecureuil();
            temp.add(e);
        }
        temp.add(new Chat());
        temp.add(new Corbeau());
        temp.add(new Coyote());
        temp.add(new Grizzly());
        temp.add(new Hermine());
        temp.add(new Loup());
        temp.add(new Louveteau());
        temp.add(new Moineau());
        temp.add(new Punaise());
        for(int k=0; k<15; k++)
        {
            int index_aleatoire = m_aleatoire.nextInt(temp.size());
            m_pioche.add(temp.get(index_aleatoire));
            temp.remove(index_aleatoire);
        }
        for(int j=0; j<4; j++)
        {
            m_main.add(m_pioche.getLast());
            m_pioche.removeLast();
        }
    }

    public boolean isVictorieux() {
        return m_victoire;
    }

    public void afficherPlateau()
    {
        System.out.println("Tour n°" + m_tour + " :                                        Score : " + m_score + "\n");
        afficherCartes();
        System.out.println("Votre main :                                                    Pioche\n");
        for(int i=0; i<m_main.size(); i++)
        {
            System.out.println((i+1) + ". " + m_main.get(i).getNom() + "    " + "PV: ");
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
