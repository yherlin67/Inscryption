package inscryption;

import java.util.ArrayList;

public class AttackResult {

    public int m_score;
    private ArrayList<Location> m_locs;

    public AttackResult(int score, ArrayList<Location> locs)
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
