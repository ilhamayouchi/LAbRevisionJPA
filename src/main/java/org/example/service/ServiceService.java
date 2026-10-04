package org.example.service;

import org.example.dao.AbstractFacade;
import org.example.entities.Service;

public class ServiceService extends AbstractFacade<Service> {
    public ServiceService() {
        super(Service.class);
    }
}
