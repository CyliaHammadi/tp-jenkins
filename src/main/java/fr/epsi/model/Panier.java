package fr.epsi.model;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<Article> articles = new ArrayList<>();
    private List<Integer> quantites = new ArrayList<>();

    public void ajouter(Article article, int quantite) {
        articles.add(article);
        quantites.add(quantite);
    }

    public List<Article> getArticles() { return articles; }
    public List<Integer> getQuantites() { return quantites; }

    public boolean estVide() { return articles.isEmpty(); }
}