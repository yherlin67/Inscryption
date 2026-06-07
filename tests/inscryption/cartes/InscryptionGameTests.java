package inscryption.cartes;

import inscryption.cards.*;
import inscryption.logic.*;
import inscryption.players.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class InscryptionGameTests {

    // Test la mise en place d'une partie (plateau et pioches) & piocher une carte
    @Test
    public void testMiseEnPlacePartieEtPioche() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        // Vérification de la création de la pioche initiale
        assertFalse(player.isDrawEmpty(), "La pioche ne doit pas être vide à l'initialisation"); 

        // La méthode setGame(2) place automatiquement une carte Rock et fait piocher 4 cartes au joueur
        gameManager.setGame(2); 

        assertEquals(4, player.getHandSize(), "Le joueur doit avoir pioché 4 cartes lors de la mise en place"); 
        assertTrue(gameManager.isCard(2, 0), "Une carte de mise en place (Rock) doit être présente en ligne 2, colonne 0"); 
    }

    // Test l'attaque d'une carte, l'attaque de toutes les cartes & la mise à jour du score
    @Test
    public void testAttaqueEtMiseAJourScore() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        // Création d'une carte alliée simulée avec 3 d'attaque
        AnimalsCards attaquantAllie = new AnimalsCards("Loup Simulé", 3, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        }; 

        // On place la carte sur le plateau du joueur (ligne 2, colonne 0)
        gameManager.setCard(attaquantAllie, 2, 0); 

        int scoreInitial = gameManager.getScore(); 

        // L'attaque du joueur va déclencher player.attack() et mettre à jour le score global
        gameManager.playerAttack(); 

        // Puisqu'il n'y a pas de défenseur en face (ligne 1), les dégâts sont infligés directement
        assertEquals(scoreInitial + 3, gameManager.getScore(), "Le score du GameManager doit augmenter de 3 suite à l'attaque directe"); 
    }

    // Test les pouvoirs (ex: le pouvoir STINKY/Puant)
    @Test
    public void testPouvoirs() {
        Player player = new Player(); 
        Cards[][] gameboard = new Cards[3][4]; 

        // Carte attaquante avec 2 d'attaque
        AnimalsCards attaquant = new AnimalsCards("Attaquant", 2, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        }; 

        // Carte défenseuse avec le pouvoir STINKY (Puant)
        AnimalsCards defenseurPuant = new AnimalsCards("Punaise Simulée", 1, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.STINKY))) {
            @Override public Cards clone() { return this; }
        }; 

        gameboard[2][0] = attaquant; 
        gameboard[1][0] = defenseurPuant; 

        // Exécution de l'attaque
        player.attack(gameboard); 

        // Le pouvoir Puant (STINKY) réduit l'attaque adverse de 1 (degats--)
        // 2 dégâts - 1 = 1 dégât infligé. Le défenseur avait 2 PV, il doit lui en rester 1.
        assertEquals(1, defenseurPuant.getHealthPoints(), "Les PV restants du défenseur doivent être de 1 car le pouvoir STINKY a réduit l'attaque de 1"); 
    }

    // Test le mécanisme de la pierre de sacrifice
    @Test
    public void testMecanismePierreDeSacrifice() {
        Player player = new Player();
        player.setGamecards();

        // On récupère deux cartes depuis la pioche générée automatiquement
        AnimalsCards carteSource = player.getAnimalAtInDraw(0);
        AnimalsCards carteCible = player.getAnimalAtInDraw(1);

        int nbPouvoirsCibleAvant = carteCible.getPowerSizeAnimal();

        // Simulation de logicSacrificeStone : ajout d'un pouvoir à la carte cible et retrait de la source de la pioche
        PowerEnum pouvoirTransfere = PowerEnum.DEATH_TOUCH;
        carteCible.addPower(pouvoirTransfere);
        player.removeAtInDraw(0);

        assertEquals(nbPouvoirsCibleAvant + 1, carteCible.getPowerSizeAnimal(), "La carte cible doit posséder un pouvoir supplémentaire");
        assertEquals(pouvoirTransfere, carteCible.getLastPowerAnimal(), "Le dernier pouvoir de la carte cible doit être celui reçu lors du sacrifice");
    }

    // Test le fait de piocher une carte
    @Test
    public void testPiocherUneCarte() {
        Player player = new Player();
        player.setGamecards();

        int tailleMainInitiale = player.getHandSize();
        int taillePiocheInitiale = player.getDrawSize();

        player.draw();

        assertEquals(tailleMainInitiale + 1, player.getHandSize(), "La main doit avoir augmenté d'une carte");
        assertEquals(taillePiocheInitiale - 1, player.getDrawSize(), "La pioche doit avoir diminué d'une carte");
    }

    // Test le placement des cartes sur le plateau
    @Test
    public void testPlacementDesCartesSurPlateau() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        player.draw(); // On pioche au moins une carte dans la main
        int tailleMainAvant = player.getHandSize(); 

        // Placement de la première carte de la main (index 0) sur la première colonne du plateau (index 0)
        gameManager.placeCard(0, 0); 

        assertEquals(tailleMainAvant - 1, player.getHandSize(), "La carte doit disparaître de la main du joueur une fois placée"); 
        assertTrue(gameManager.isCard(2, 0), "La carte doit être physiquement placée sur le plateau (ligne 2, colonne 0)"); 
    }

    // Test le fait de gagner ou perdre une partie / le jeu
    @Test
    public void testConditionsDeVictoireEtDefaite() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent); 

        gameManager.setScore(0); 
        assertNull(gameManager.gameReview(), "Avec un score de 0, la partie n'est ni gagnée ni perdue (retourne null)"); 

        gameManager.setScore(5); 
        assertTrue(gameManager.gameReview(), "Avec un score de 5, le joueur doit gagner la partie (retourne true)"); 

        gameManager.setScore(-10); // Le score passe de 5 à -5
        assertFalse(gameManager.gameReview(), "Avec un score de -5, le joueur doit perdre la partie (retourne false)"); 
    }

    // Test l'ajout de nouvelles cartes dans la pioche à la fin de la deuxième partie
    @Test
    public void testAjoutNouvellesCartesDansPioche() {
        Player player = new Player(); 
        int taillePiocheAvant = player.getDrawSize(); 

        // Création de la carte à ajouter
        AnimalsCards nouvelleCarteAjoutee = new AnimalsCards("Ours Simulé", 4, 6, 2, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        }; 

        player.addInDraw(nouvelleCarteAjoutee); 

        assertEquals(taillePiocheAvant + 1, player.getDrawSize(), "La pioche doit contenir une carte de plus"); 
        assertEquals("Ours Simulé", player.getAnimalAtInDraw(player.getDrawSize() - 1).getName(), "La carte ajoutée doit se trouver à la toute fin de la pioche"); 
    }

    // Complément pour l'attaque de toutes les cartes à la fin d'un tour (Plateau complet)
    @Test
    public void testAttaquePlateauComplet() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent);

        // Création de 4 cartes attaquantes avec des dégâts variés (total attendu : 2 + 1 + 3 + 2 = 8)
        AnimalsCards carteCol0 = new AnimalsCards("Carte 1", 2, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        };
        AnimalsCards carteCol1 = new AnimalsCards("Carte 2", 1, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        };
        AnimalsCards carteCol2 = new AnimalsCards("Carte 3", 3, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        };
        AnimalsCards carteCol3 = new AnimalsCards("Carte 4", 2, 2, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE))) {
            @Override public Cards clone() { return this; }
        };

        // On remplit entièrement la ligne du joueur (Ligne 2, colonnes 0, 1, 2 et 3)
        gameManager.setCard(carteCol0, 2, 0);
        gameManager.setCard(carteCol1, 2, 1);
        gameManager.setCard(carteCol2, 2, 2);
        gameManager.setCard(carteCol3, 2, 3);

        int scoreInitial = gameManager.getScore();

        // Exécution de l'attaque globale du joueur à la fin de son tour
        gameManager.playerAttack();

        // Les 4 cartes font des dégâts directs à l'adversaire (cases en face vides).
        // 2 + 1 + 3 + 2 = 8 dégâts.
        assertEquals(scoreInitial + 8, gameManager.getScore(), "Les attaques des 4 cartes présentes sur le plateau doivent s'additionner correctement");
    }

    // Complément pour l'attaque d'une carte (Combat direct carte contre carte)
    @Test
    public void testAttaqueCarteContreCarte() {
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameManager gameManager = new GameManager(player, opponent);

        // Création d'une classe locale pour avoir un clone() fonctionnel pour les tests
        class CarteTest extends AnimalsCards {
            public CarteTest(String nom, int att, int pdv) {
                super(nom, att, pdv, 1, 0, false, new ArrayList<>(Arrays.asList(PowerEnum.NONE)));
            }
            // Utilisation de ton constructeur de copie existant !
            protected CarteTest(CarteTest target) {
                super(target);
            }
            @Override
            public Cards clone() {
                return new CarteTest(this); // Vrai clone indépendant !
            }
        }

        // Création d'un attaquant (2 d'attaque) et d'un défenseur (4 PV)
        AnimalsCards attaquant = new CarteTest("Allié", 2, 3);
        AnimalsCards defenseur = new CarteTest("Ennemi", 1, 4);

        // On les place face à face sur la colonne 1
        gameManager.setCard(attaquant, 2, 1); // Ligne du joueur
        gameManager.setCard(defenseur, 1, 1); // Ligne de l'adversaire

        int scoreInitial = gameManager.getScore();
        gameManager.playerAttack();

        // Le défenseur avait 4 PV. L'attaquant inflige 2 dégâts. Il doit rester 2 PV.
        assertEquals(2, defenseur.getHealthPoints(), "La carte adverse doit perdre des PV égaux à l'attaque de la carte alliée");

        // Comme la carte a été bloquée par le défenseur, le score du joueur ne doit pas bouger
        assertEquals(scoreInitial, gameManager.getScore(), "Le score du joueur ne doit pas augmenter quand une carte adverse encaisse l'attaque");
    }
}