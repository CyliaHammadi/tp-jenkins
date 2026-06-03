package fr.epsi.service;

import fr.epsi.model.Article;
import fr.epsi.model.Panier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandeServiceIT {

    private final CommandeService service = new CommandeService();

    @Test
    @DisplayName("Scenario complet panier mixte PETITE")
    void pipeline_PanierMixte_CategorisationCorrecte() {
        // GIVEN
        Panier panier = new Panier();
        panier.ajouter(new Article("Stylo",  2.0), 10);
        panier.ajouter(new Article("Cahier", 5.0),  4);
        // WHEN
        double total       = service.calculerTotal(panier);
        double apresRemise = service.appliquerRemise(total, 10);
        String categorie   = service.categoriserCommande(apresRemise);
        // THEN
        assertEquals(40.0,     total,       0.001);
        assertEquals(36.0,     apresRemise, 0.001);
        assertEquals("PETITE", categorie);
    }

    @Test
    @DisplayName("Commande premium GRANDE")
    void pipeline_PanierPremium_CategorieGrande() {
        // GIVEN
        Panier panier = new Panier();
        panier.ajouter(new Article("Ordinateur", 800.0), 1);
        panier.ajouter(new Article("Souris",      30.0), 2);
        // WHEN
        double total       = service.calculerTotal(panier);
        double apresRemise = service.appliquerRemise(total, 5);
        String categorie   = service.categoriserCommande(apresRemise);
        // THEN
        assertEquals(860.0,    total,       0.001);
        assertEquals(817.0,    apresRemise, 0.001);
        assertEquals("GRANDE", categorie);
    }

    @Test
    @DisplayName("Remise 100% total zero PETITE")
    void pipeline_RemiseTotale_TotalZeroCategorisePetite() {
        // GIVEN
        Panier panier = new Panier();
        panier.ajouter(new Article("Cadeau", 150.0), 1);
        // WHEN
        double total       = service.calculerTotal(panier);
        double apresRemise = service.appliquerRemise(total, 100);
        String categorie   = service.categoriserCommande(apresRemise);
        // THEN
        assertEquals(0.0,      apresRemise, 0.001);
        assertEquals("PETITE", categorie);
    }
}