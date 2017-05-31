package be.ift.services;

import be.ift.domain.Categorie;

import java.util.List;


public interface CategorieService {
    List<Categorie> getAllCategories();
    Categorie saveCategorie(Categorie categorie);
    Categorie getCategorieByID(Integer CAT_ID);
}
