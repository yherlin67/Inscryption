package inscryption.players;

import inscryption.GameManager;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class Player {

    private Boolean m_victoire;
    private ArrayList<Cartes_animaux> m_main;
    private ArrayList<Cartes_animaux> m_pioche;
    private Random m_aleatoire;
    private GameManager m_datas;

    public Player()
    {
        m_victoire = null;
        m_main = new ArrayList<Cartes_animaux>();
        m_pioche = new ArrayList<Cartes_animaux>();
        m_aleatoire = new Random();
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void createDraw()
    {
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
    }

    public void draw()
    {
        Cartes_animaux carte = m_pioche.removeLast();
        m_main.add(carte);
    }

    public void attack()
    {
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCartes()[2][i] != null)
            {
                if(m_datas.getCartes()[1][i] == null)
                {
                    if (m_datas.getCartes()[2][i].getAnimaux() != null)
                    {
                        m_datas.setScore(m_datas.getCartes()[2][i].getAnimaux().getAttaque());
                    }
                }
            }
        }
    }

    public Cartes removeCard(int indHand)
    {
        return m_main.remove(indHand);
    }

    public ArrayList<Cartes_animaux> getHand(){return m_main;}

    public ArrayList<Cartes_animaux> getDraw(){return m_pioche;}

    public Boolean getVictoire(){return m_victoire;}

}
