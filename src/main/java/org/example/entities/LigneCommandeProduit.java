package org.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {

    @EmbeddedId
    private CommandeProduitPK pk;

    private int quantite;
    private double prixVente;

    @ManyToOne
    @JoinColumn(name = "produit", insertable = false, updatable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande", insertable = false, updatable = false)
    private Commande commande;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Commande commande, CommandeProduitPK pk, double prixVente, Produit produit, int quantite) {
        this.commande = commande;
        this.pk = pk;
        this.prixVente = prixVente;
        this.produit = produit;
        this.quantite = quantite;
    }

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite, double prixVente) {
        this.pk = new CommandeProduitPK(commande.getId(), produit.getId());
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixVente = prixVente;
    }

    public double getMontant() {
        return quantite * prixVente;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public CommandeProduitPK getPk() {
        return pk;
    }

    public void setPk(CommandeProduitPK pk) {
        this.pk = pk;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
