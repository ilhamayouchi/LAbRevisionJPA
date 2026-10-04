package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.Commande;

public class CommandeService extends AbstractFacade<Commande> {
    public CommandeService() {
        super(Commande.class);
    }
}
