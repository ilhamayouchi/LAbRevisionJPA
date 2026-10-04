package org.example.test;

import org.example.entities.*;
import org.example.service.*;
import org.example.util.JPAUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestGlobal {

    private static Date date(int y, int m, int d) {
        Calendar c = Calendar.getInstance();
        c.set(y, m - 1, d, 0, 0, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static void main(String[] args) {
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();
        UserService us = new UserService();
        ServiceService ss = new ServiceService();

        System.out.println("\n===== CRUD CATEGORIE =====");
        Categorie info = new Categorie("Ordinateurs");
        Categorie tel = new Categorie("Téléphones");
        cs.create(info);
        cs.create(tel);
        info.setNom("Ordinateurs portables");
        cs.update(info);
        cs.findAll().forEach(System.out::println);

        System.out.println("\n===== PRODUITS =====");
        ps.create(new Produit("Lenovo ThinkPad", 12000, date(2024, 1, 10), info));
        ps.create(new Produit("HP ProBook", 8000, date(2024, 3, 5), info));
        ps.create(new Produit("Dell XPS", 15000, date(2024, 6, 20), info));
        ps.create(new Produit("iPhone 15", 11000, date(2024, 2, 14), tel));
        ps.create(new Produit("Samsung S24", 9500, date(2024, 4, 1), tel));
        ps.findAll().forEach(System.out::println);

        System.out.println("\n===== NAMED QUERIES =====");
        System.out.println("-- Par catégorie :");
        ps.findByCategorie(info).forEach(System.out::println);
        System.out.println("-- Entre 2024-01-01 et 2024-03-31 :");
        ps.findBetweenDates(date(2024, 1, 1), date(2024, 3, 31)).forEach(System.out::println);
        System.out.println("-- Prix >= 10000 (SQL natif) :");
        ps.findByPrixMin(10000).forEach(System.out::println);

        System.out.println("\n===== RECHERCHE DYNAMIQUE (Criteria) =====");
        ps.search(info, 9000.0, null, null).forEach(System.out::println);
        System.out.println("-- Mot-clé 'phone' :");
        ps.search(null, null, null, "phone").forEach(System.out::println);

        System.out.println("\n===== PAGINATION (taille 2) =====");
        for (int page = 1; page <= 3; page++) {
            System.out.println("-- Page " + page);
            ps.findPage(page, 2).forEach(System.out::println);
        }

        System.out.println("\n===== JOIN FETCH =====");
        for (Categorie c : cs.findAllWithProduits()) {
            System.out.println(c.getNom() + " -> " + c.getProduits().size() + " produit(s)");
        }

        System.out.println("\n===== HÉRITAGE JOINED =====");
        us.create(new Client("ali@mail.com", "1234", "Alami", "Ali"));
        us.create(new Employe("sara@mail.com", "abcd", "EMP001"));
        us.findAll().forEach(u -> System.out.println(u.getClass().getSimpleName() + " : " + u.getEmail()));

        System.out.println("\n===== EMPLOYE <-> SERVICE =====");
        Service s1 = new Service("Informatique");
        Service s2 = new Service("Comptabilité");
        ss.create(s1);
        ss.create(s2);
        Employe emp2 = new Employe("omar@mail.com", "xyz", "EMP002");
        emp2.getServices().add(s1);
        emp2.getServices().add(s2);
        us.create(emp2);

        System.out.println("\n===== COMMANDE + LIGNES (clé composée) =====");
        Commande cmd = new Commande(new Date());
        cms.create(cmd);
        List<Produit> produits = ps.findAll();
        ls.create(new LigneCommandeProduit(cmd, produits.get(0), 2, 11500));
        ls.create(new LigneCommandeProduit(cmd, produits.get(3), 1, 10500));
        double total = 0;
        for (LigneCommandeProduit l : ls.findAll()) {
            System.out.println(l.getProduit().getNom() + " x" + l.getQuantite() + " = " + l.getMontant());
            total += l.getMontant();
        }
        System.out.println("TOTAL COMMANDE = " + total);

        JPAUtil.shutdown();

    }
}
