package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.Categorie;
import org.example.entities.Produit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProduitService extends AbstractFacade<Produit> {
    public ProduitService() {
        super(Produit.class);
    }

    // Named Query
    public List<Produit> findByCategorie(Categorie categorie) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Produit.findByCategorie", Produit.class)
                     .setParameter("categorie", categorie)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // Named Query
    public List<Produit> findBetweenDates(Date d1, Date d2) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Produit.findBetweenDates", Produit.class)
                     .setParameter("d1", d1)
                     .setParameter("d2", d2)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // Named Native Query
    @SuppressWarnings("unchecked")
    public List<Produit> findByPrixMin(double minPrix) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Produit.findByPrixMin")
                     .setParameter("minPrix", minPrix)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // Recherche dynamique : Criteria API
    public List<Produit> search(Categorie categorie, Double prixMin, Double prixMax, String motCle) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Produit> cq = cb.createQuery(Produit.class);
            Root<Produit> root = cq.from(Produit.class);

            List<Predicate> predicates = new ArrayList<>();

            if (categorie != null) {
                predicates.add(cb.equal(root.get("categorie"), categorie));
            }
            if (prixMin != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.<Double>get("prix"), prixMin));
            }
            if (prixMax != null) {
                predicates.add(cb.lessThanOrEqualTo(root.<Double>get("prix"), prixMax));
            }
            if (motCle != null && !motCle.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.<String>get("nom")), "%" + motCle.toLowerCase() + "%"));
            }

            cq.where(predicates.toArray(new Predicate[0]));
            return em.createQuery(cq).getResultList();
        } finally {
            em.close();
        }
    }

    // Pagination
    public List<Produit> findPage(int page, int pageSize) {
        EntityManager em = getEntityManager();
        try {
            int firstResult = (page - 1) * pageSize;
            return em.createQuery("FROM Produit p ORDER BY p.id ASC", Produit.class)
                     .setFirstResult(firstResult)
                     .setMaxResults(pageSize)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}
