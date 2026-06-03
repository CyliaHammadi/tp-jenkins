package fr.epsi.service;

import fr.epsi.model.Article;
import fr.epsi.model.Panier;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandeServiceIT {

    @Test void scenarioAchatComplet_avecLivraison() {
        Panier panier = new Panier();
        panier.ajouterArticle(new Article("Cahier", 3.0, 3));
        CommandeService service = new CommandeService();
        double total = service.calculerTotalTTC(panier);
        assertTrue(total > 9.0);
        assertTrue(service.validerCommande(panier, "5 avenue Lyon"));
    }

    @Test void scenarioAchatComplet_livraisonGratuite() {
        Panier panier = new Panier();
        panier.ajouterArticle(new Article("Écran", 200.0, 1));
        CommandeService service = new CommandeService();
        assertEquals(0.0, service.calculerFraisLivraison(panier));
    }

    @Test void scenarioPanierMultiArticles() {
        Panier panier = new Panier();
        panier.ajouterArticle(new Article("Souris", 25.0, 1));
        panier.ajouterArticle(new Article("Clavier", 45.0, 1));
        CommandeService service = new CommandeService();
        assertEquals(84.0, service.calculerTotalTTC(panier), 0.001);
    }
}