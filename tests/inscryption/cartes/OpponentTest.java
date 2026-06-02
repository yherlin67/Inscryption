package inscryption.cartes;

import inscryption.GameManager;
import inscryption.players.Opponent;
import inscryption.players.Player;
import inscryption.cards.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OpponentTest {

    @Test
    void InitialStateTest() {
        Opponent adversaire = new Opponent();

        assertEquals(0, adversaire.getTurnAttack(), "L'attaque de base du tour devrait être à 0");
    }

    @Test
    void SetMatchTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        ArrayList<Cards> col0 = new ArrayList<>(); col0.add(new Loup()); col0.add(new Ecureuil());
        ArrayList<Cards> col1 = new ArrayList<>(); col1.add(new Loup());
        ArrayList<Cards> col2 = new ArrayList<>(); col2.add(new Loup());
        ArrayList<Cards> col3 = new ArrayList<>(); col3.add(new Loup());

        adversaire.setMatch(col0, col1, col2, col3);

        assertTrue(gm.isCard(0, 0), "Une carte devrait être préparée en (0,0)");

        assertEquals(1, col0.size(), "Il ne devrait rester qu'une carte (l'écureuil) dans la file 0");
        assertEquals(0, col1.size(), "La file 1 devrait être vide");
    }

    @Test
    void OpponentTurnTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        ArrayList<Cards> col0 = new ArrayList<>(); col0.add(new Loup()); col0.add(new Ecureuil());
        ArrayList<Cards> col1 = new ArrayList<>(); col1.add(new Loup());
        ArrayList<Cards> col2 = new ArrayList<>(); col2.add(new Loup());
        ArrayList<Cards> col3 = new ArrayList<>(); col3.add(new Loup());

        adversaire.setMatch(col0, col1, col2, col3);

        assertFalse(gm.isCard(1, 0), "La ligne de combat devrait être vide avant de jouer");

        adversaire.play();

        assertTrue(gm.isCard(1, 0), "La carte de préparation aurait dû avancer en ligne de combat (1,0)");

        assertTrue(gm.isCard(0, 0), "La carte suivante (Ecureuil) devrait être arrivée en préparation (0,0)");
        assertEquals("Ecureuil", gm.getCardName(0, 0), "La nouvelle carte préparée devrait être l'écureuil");
    }

    @Test
    void DirectAttackTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        gm.setCard(new Loup(), 1, 0);

        adversaire.attack();

        assertEquals(-3, gm.getScore(), "Le Loup adverse aurait dû infliger 3 de dégâts directs au score");
        assertEquals(3, adversaire.getTurnAttack(), "Le récapitulatif d'attaque du tour de l'adversaire devrait être 3");
    }

    @Test
    void BlockedAttackTest() {
        Player joueur = new Player();
        Opponent adversaire = new Opponent();
        GameManager gm = new GameManager(joueur, adversaire);

        Loup loupAdversaire = new Loup();
        gm.setCard(loupAdversaire, 1, 0);

        Loup loupJoueur = new Loup();
        gm.setCard(loupJoueur, 2, 0);

        adversaire.attack();

        assertEquals(0, gm.getScore(), "Le score global ne devrait pas bouger car l'attaque a été bloquée");

        assertFalse(gm.isCard(2, 0), "Le Loup du joueur devrait être détruit et retiré du plateau (2 PV - 3 Dégâts <= 0)");
    }
}