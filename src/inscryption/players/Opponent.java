package inscryption.players;

import inscryption.GameManager;
import inscryption.cartes.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Opponent {
    private ArrayList<Cartes>[] m_actions = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    };

    private GameManager m_datas;

    public Opponent()
    {
    }

    public void setGameManager(GameManager gameManager) {
        this.m_datas = gameManager;
    }

    public void setFirstMatch()
    {
        m_datas.setCard(new Louveteau(), 0, 0);
        m_datas.setCard(new Louveteau(), 0, 2);
      //  m_actions[0].addAll(Arrays.asList(new Louveteau(), null));
      //  m_actions[1].addAll(Arrays.asList(null, new Moineau()));
      //  m_actions[2].addAll(Arrays.asList(new Moineau(), new Chat()));
      //  m_actions[3].addAll(Arrays.asList(null, null));
    }

    public void setSecondMatch()
    {
        m_actions[0].add(new Ecureuil());
        //etc...
    }

    public void setThirdMatch()
    {
        m_actions[0].add(new Ecureuil());
        //etc...
    }

    public void play()
    {
        for(int i=0; i<4; i++)
        {
            if(m_datas.getCartes()[1][i] == null)
            {
                m_datas.getCartes()[1][i] = m_datas.getCartes()[0][i];
                m_datas.getCartes()[0][i] = null;
            }
            if(m_datas.getCartes()[0][i] == null && !m_actions[i].isEmpty())
            {
                m_datas.getCartes()[0][i] = m_actions[i].getFirst();
                m_actions[i].removeFirst();
            }
        }
    }
}
