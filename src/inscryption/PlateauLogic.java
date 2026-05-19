package inscryption;

import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Random;

public class PlateauLogic {

    private boolean m_victoire;
    private int m_score;
    private int m_tour;
    private ArrayList<Cartes_animaux> m_main = new ArrayList<Cartes_animaux>();
    private ArrayList<Cartes_animaux> m_pioche = new ArrayList<Cartes_animaux>();
    private Random m_aleatoire = new Random();
    private Cartes m_cartes[][] = {
            {new Louveteau(), null, new Moineau(), null},
            {null, null, null, null},
            {null, new Rocher(), null, null}
    };

    public PlateauLogic()
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

    public boolean getVictoire(){return m_victoire;}

    public int getScore(){return m_score;}

    public int getTour(){return m_tour;}

    public Cartes[][] getCartes(){return m_cartes;}

    public ArrayList<Cartes_animaux> getMain(){return m_main;}

    public ArrayList<Cartes_animaux> getPioche(){return m_pioche;}
}
