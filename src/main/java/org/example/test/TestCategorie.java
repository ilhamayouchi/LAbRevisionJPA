package org.example.test;

import org.example.entities.Categorie;
import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestCategorie {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Categorie c = new Categorie("Ordinateurs");
        em.persist(c);

        tx.commit();
        em.close();

        System.out.println("Catégorie insérée avec l'ID : " + c.getId());
        JPAUtil.shutdown();
    }
}
