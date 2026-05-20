package inscryption.players;

import inscryption.GameManager;
import inscryption.cartes.Cartes;
import inscryption.cartes.Chat;
import inscryption.cartes.Ecureuil;
import inscryption.cartes.Rocher;

import java.util.ArrayList;
import java.util.List;

public class Opponent {
    private ArrayList<Cartes>[] m_actions = new ArrayList[]{
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    };

    private GameManager m_datas;

    public void setFirstMatch()
    {
        m_actions[0].addAll(List.of(new Ecureuil(), new Chat()));
        m_actions[1].addAll(List.of(new Ecureuil(), new Chat()));
        m_actions[2].addAll(List.of(new Ecureuil(), new Chat()));
        m_actions[3].addAll(List.of(new Ecureuil(), new Chat()));
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
    }
}
