/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Enaitz Izagirre
 */
@Entity
@Table(name = "COMMERCIAL")
public class Commercial extends User implements Serializable{
    
    @Enumerated(EnumType.STRING)
    private Especialization especialization;
    
    @OneToMany(mappedBy = "comercial")
    private List<Client> clients;

    public Especialization getEspecialization() {
        return especialization;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setEspecialization(Especialization especialization) {
        this.especialization = especialization;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return super.toString() + " Tipo - Commercial{" + "especialization=" + especialization + ", clients=" + clients + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = super.hashCode() * hash;
        hash = 41 * hash + Objects.hashCode(this.especialization);
        hash = 41 * hash + Objects.hashCode(this.clients);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commercial other = (Commercial) obj;
        if (!Objects.equals(this.especialization, other.especialization)) {
            return false;
        }
        if (!Objects.equals(this.clients, other.clients)) {
            return false;
        }
        return true;
    }
    
    
    
}
