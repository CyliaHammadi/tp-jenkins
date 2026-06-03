package fr.epsi.service;

import fr.epsi.model.Panier;

public class CommandeService {

    public static final double SEUIL_LIVRAISON_GRATUITE = 50.0;
    public static final double FRAIS_LIVRAISON = 5.90;
    public static final double TAUX_TVA = 0.20;

    public double calculerFraisLivraison(Panier panier) {
        if (panier.estVide()) throw new IllegalArgumentException("Panier vide");
        return panier.calculerTotal() >= SEUIL_LIVRAISON_GRATUITE ? 0.0 : FRAIS_LIVRAISON;
    }

    public double calculerTVA(Panier panier) {
        return panier.calculerTotal() * TAUX_TVA;
    }

    public double calculerTotalTTC(Panier panier) {
        return panier.calculerTotal() + calculerTVA(panier) + calculerFraisLivraison(panier);
    }

    public boolean validerCommande(Panier panier, String adresse) {
        if (panier.estVide()) return false;
        if (adresse == null || adresse.isBlank()) return false;
        return true;
    }
}