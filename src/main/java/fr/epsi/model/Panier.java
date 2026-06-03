package fr.epsi.model;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private List<Article> articles = new ArrayList<>();

    public void ajouterArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() { return articles; }

    public double calculerTotal() {
        return articles.stream()
            .mapToDouble(a -> a.getPrix() * a.getQuantite())
            .sum();
    }

    public boolean estVide() { return articles.isEmpty(); }
}