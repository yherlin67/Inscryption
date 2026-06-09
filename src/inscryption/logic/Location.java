package inscryption.logic;

import inscryption.cards.Card;

public class Location {

    private int m_x;
    private int m_y;
    private int m_damage;
    private Card m_card;
    private boolean m_damageAction;


    public Location(int x, int y, int damage) {
        this.m_x = x;
        this.m_y = y;
        this.m_damage = damage;
        this.m_damageAction = true;
    }

    public Location(int x, int y, Card card) {
        this.m_x = x;
        this.m_y = y;
        this.m_card = card;
        this.m_damageAction = false;
    }

    public boolean isDamageAction() { return m_damageAction; }

    public int getX() {
        return m_x;
    }

    public int getY() {
        return m_y;
    }

    public int getDamage() {
        return m_damage;
    }

    public Card getCard() {
        return m_card;
    }
}
