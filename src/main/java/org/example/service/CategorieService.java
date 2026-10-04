package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.Categorie;

import javax.persistence.EntityManager;
import java.util.List;

public class CategorieService extends AbstractFacade<Categorie> {
    public CategorieService() {
        super(Categorie.class);
    }

    // Évite le problème N+1 : JOIN FETCH sur la collection LAZY
    public List<Categorie> findAllWithProduits() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT c FROM Categorie c LEFT JOIN FETCH c.produits", Categorie.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
