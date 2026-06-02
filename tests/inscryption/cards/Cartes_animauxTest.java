package inscryption.cards;

import inscryption.players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Cartes_animauxTest {

    @Test
    public void subirDegats() {
        Animals_cards chat = new Chat();
        chat.takeDamage(1);
        assertEquals(0, chat.getHealthPoints());
    }

    @Test
    public void attaquer() {
        Player p = new Player();
        Animals_cards grizzly = new Grizzly();
        Animals_cards loup = new Loup();
        p.attack();
        assertEquals(3, grizzly.getHealthPoints());
    }
}