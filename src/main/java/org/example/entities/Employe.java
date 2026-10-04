package org.example.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employes")
public class Employe extends User {

    private String matricule;

    @ManyToMany
    @JoinTable(name = "employe_service",
               joinColumns = @JoinColumn(name = "employe_id"),
               inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services = new ArrayList<>();

    public Employe() {}

    public Employe(String email, String password, String matricule) {
        this.email = email;
        this.password = password;
        this.matricule = matricule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "matricule='" + matricule + '\'' +
                ", services=" + services +
                '}';
    }
}
