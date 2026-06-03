package fr.epsi.service;

import fr.epsi.model.Article;
import fr.epsi.model.Panier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandeServiceTest {

    private CommandeService service;
    private Panier panier;

    @BeforeEach
    void setUp() {
        service = new CommandeService();
        panier  = new Panier();
    }

    @Test
    @DisplayName("Total correct pour 3 stylos a 2 euros")
    void calculerTotal_TroisStylos_RetourneSix() {
        // GIVEN
        panier.ajouter(new Article("Stylo", 2.0), 3);
        // WHEN
        double total = service.calculerTotal(panier);
        // THEN
        assertEquals(6.0, total, 0.001);
    }

    @Test
    @DisplayName("Total correct pour plusieurs articles")
    void calculerTotal_PlusieursArticles_RetourneSomme() {
        // GIVEN
        panier.ajouter(new Article("Stylo",  2.0), 3);
        panier.ajouter(new Article("Cahier", 5.0), 2);
        // WHEN
        double total = service.calculerTotal(panier);
        // THEN
        assertEquals(16.0, total, 0.001);
    }

    @Test
    @DisplayName("Panier vide leve une exception")
    void calculerTotal_PanierVide_LeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.calculerTotal(panier));
    }

    @Test
    @DisplayName("Panier null leve une exception")
    void calculerTotal_PanierNull_LeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.calculerTotal(null));
    }

    @Test
    @DisplayName("Remise 10% sur 100 euros = 90 euros")
    void appliquerRemise_DixPourcent_RetourneQuatreVingtDix() {
        // GIVEN + WHEN
        double resultat = service.appliquerRemise(100.0, 10);
        // THEN
        assertEquals(90.0, resultat, 0.001);
    }

    @Test
    @DisplayName("Remise 0% ne change pas le total")
    void appliquerRemise_ZeroPourcent_RetourneTotalInchange() {
        double resultat = service.appliquerRemise(100.0, 0);
        assertEquals(100.0, resultat, 0.001);
    }

    @Test
    @DisplayName("Remise negative leve une exception")
    void appliquerRemise_RemiseNegative_LeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.appliquerRemise(100.0, -5));
    }

    @Test
    @DisplayName("Remise superieure a 100 leve une exception")
    void appliquerRemise_RemiseSupCent_LeveException() {
        assertThrows(IllegalArgumentException.class,
            () -> service.appliquerRemise(100.0, 150));
    }

    @Test
    @DisplayName("30 euros = categorie PETITE")
    void categoriser_TrenteEuros_RetournePetite() {
        assertEquals("PETITE", service.categoriserCommande(30.0));
    }

    @Test
    @DisplayName("150 euros = categorie MOYENNE")
    void categoriser_CentCinquanteEuros_RetourneMoyenne() {
        assertEquals("MOYENNE", service.categoriserCommande(150.0));
    }

    @Test
    @DisplayName("500 euros = categorie GRANDE")
    void categoriser_CinqCentsEuros_RetourneGrande() {
        assertEquals("GRANDE", service.categoriserCommande(500.0));
    }

    @Test
    @DisplayName("Frontiere 50 euros = MOYENNE pas PETITE")
    void categoriser_CinquanteEuros_RetourneMoyenne() {
        assertEquals("MOYENNE", service.categoriserCommande(50.0));
    }

    @Test
    @DisplayName("Frontiere 200 euros = GRANDE pas MOYENNE")
    void categoriser_DeuxCentsEuros_RetourneGrande() {
        assertEquals("GRANDE", service.categoriserCommande(200.0));
    }
}