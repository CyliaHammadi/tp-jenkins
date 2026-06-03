package fr.epsi.service;

import fr.epsi.model.Article;
import fr.epsi.model.Panier;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class CommandeServiceTest {

    private CommandeService service;
    private Panier panier;

    @BeforeEach
    void setUp() {
        service = new CommandeService();
        panier = new Panier();
    }

    @Test void panierVide_totalZero() {
        assertEquals(0.0, panier.calculerTotal());
    }

    @Test void ajouterArticle_totalCorrect() {
        panier.ajouterArticle(new Article("Livre", 20.0, 2));
        assertEquals(40.0, panier.calculerTotal());
    }

    @Test void fraisLivraison_sousSeuilPaye() {
        panier.ajouterArticle(new Article("Stylo", 5.0, 1));
        assertEquals(5.90, service.calculerFraisLivraison(panier));
    }

    @Test void fraisLivraison_auDessusSeuil_gratuit() {
        panier.ajouterArticle(new Article("Livre", 60.0, 1));
        assertEquals(0.0, service.calculerFraisLivraison(panier));
    }

    @Test void fraisLivraison_panierVide_exception() {
        assertThrows(IllegalArgumentException.class,
            () -> service.calculerFraisLivraison(panier));
    }

    @Test void tva_calculCorrect() {
        panier.ajouterArticle(new Article("Clé USB", 10.0, 1));
        assertEquals(2.0, service.calculerTVA(panier), 0.001);
    }

    @Test void totalTTC_avecLivraison() {
        panier.ajouterArticle(new Article("Crayon", 10.0, 1));
        assertEquals(17.90, service.calculerTotalTTC(panier), 0.001);
    }

    @Test void totalTTC_livraisonGratuite() {
        panier.ajouterArticle(new Article("Laptop", 100.0, 1));
        assertEquals(120.0, service.calculerTotalTTC(panier), 0.001);
    }

    @Test void validerCommande_ok() {
        panier.ajouterArticle(new Article("Livre", 15.0, 1));
        assertTrue(service.validerCommande(panier, "12 rue de Paris"));
    }

    @Test void validerCommande_panierVide_false() {
        assertFalse(service.validerCommande(panier, "12 rue de Paris"));
    }

    @Test void validerCommande_adresseVide_false() {
        panier.ajouterArticle(new Article("Livre", 15.0, 1));
        assertFalse(service.validerCommande(panier, ""));
    }
}