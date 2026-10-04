package org.example.dao;

import org.example.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.util.List;

public abstract class AbstractFacade<T> implements IDao<T> {
    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return JPAUtil.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public boolean create(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.persist(o);
            tx.commit();
            return true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public boolean update(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.merge(o);
            tx.commit();
            return true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public boolean delete(T o) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            em.remove(em.contains(o) ? o : em.merge(o));
            tx.commit();
            return true;
        } catch (PersistenceException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) em.close();
        }
    }

    @Override
    public T findById(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        } finally {
            em.close();
        }
    }
}
