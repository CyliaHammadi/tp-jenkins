package fr.epsi.service;

import fr.epsi.model.Panier;

public class CommandeService {

    public double calculerTotal(Panier panier) {
        if (panier == null || panier.estVide()) {
            throw new IllegalArgumentException("Panier null ou vide");
        }
        double total = 0;
        for (int i = 0; i < panier.getArticles().size(); i++) {
            total += panier.getArticles().get(i).getPrix()
                     * panier.getQuantites().get(i);
        }
        return total;
    }

    public double appliquerRemise(double total, int pourcentage) {
        if (pourcentage < 0 || pourcentage > 100) {
            throw new IllegalArgumentException("Pourcentage invalide");
        }
        return total * (1 - pourcentage / 100.0);
    }

    public String categoriserCommande(double total) {
        if (total < 50)  return "PETITE";
        if (total < 200) return "MOYENNE";
        return "GRANDE";
    }
}