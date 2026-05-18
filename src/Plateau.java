public class Plateau {
    private boolean m_victoire;
    private int m_score;
    private int m_tour;
    private String[][] m_cartes = {
            {"Louveteau", "", "Moineau", ""},
            {"", "", "", ""},
            {"", "Rocher", "", ""}
    };

    public Plateau()
    {
        m_tour = 1;
        m_score = 0;
    }

    public boolean isVictorieux() {
        return m_victoire;
    }

    public void afficherPlateau()
    {
        System.out.println("Tour n°" + m_tour + " :                                        Score : " + m_score + "\n");
        afficherCartes();
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
