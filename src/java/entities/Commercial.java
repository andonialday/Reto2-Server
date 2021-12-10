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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Esta clase es un tipo de Usuario que extiende de User
 * @author Enaitz Izagirre
 */
@Entity
@Table(name = "COMMERCIAL", schema="reto2g1c")
@XmlRootElement
public class Commercial extends User implements Serializable{
    
    //La especializacion del Comercial se implementa mediante las opciones de la clase Especialization
    @Enumerated(EnumType.STRING)
    private Especialization especialization;
    
    //La lista de clientes que tiene un comercial 
    @OneToMany(mappedBy = "comercial")
    private List<Client> clients;

    /**
     * Metodo para obtener la especializacion
     * @return Devuelve una Especializacion
     */
    public Especialization getEspecialization() {
        return especialization;
    }

    /**
     * Metodo`para obtener el Listado de clientes
     * @return Devuelve el array de clientes
     */
    @XmlTransient
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Metodo para establecer la especializacion
     * @param especialization
     */
    public void setEspecialization(Especialization especialization) {
        this.especialization = especialization;
    }

    /**
     * Metodo para establecer un nuevo Listado de Clientes
     * @param clients
     */
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
