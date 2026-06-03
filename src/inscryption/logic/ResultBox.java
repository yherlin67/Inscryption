package inscryption.logic;

import java.util.ArrayList;

public class ResultBox {

    public int m_score;
    private ArrayList<Location> m_locs;

    public ResultBox(int score, ArrayList<Location> locs)
    {
        this.m_score = score;
        this.m_locs = locs;
    }

    public ArrayList<Location> getImpactedLocations() {
        return m_locs;
    }

    public int getScore() {
        return m_score;
    }
}
