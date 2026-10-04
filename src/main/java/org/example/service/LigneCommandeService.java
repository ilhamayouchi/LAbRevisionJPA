package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.LigneCommandeProduit;

public class LigneCommandeService extends AbstractFacade<LigneCommandeProduit> {
    public LigneCommandeService() {
        super(LigneCommandeProduit.class);
    }
}
