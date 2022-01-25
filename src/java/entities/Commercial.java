/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@NamedQueries({
    @NamedQuery(
            name = "findEspecializationeEnable", query = "DELETE FROM Commercial m WHERE m.status=:'DISABLE' ")
    ,
    @NamedQuery(
            name = "findEspecializationeEnable", query = "SELECT m FROM Commercial m SET m.status=:'DISABLE' ")
    ,
    //Busca comerciales por su especializacion siempre y cuando este habilitado y los ordenada de forma ascendente por id 
    @NamedQuery(
            name = "findEspecializationeEnable", query = "SELECT m FROM Commercial m WHERE m.especialization:especialization AND m.status=:'ENABLED' ORDER BY m.id ASC")
    ,
    //Busca comerciales por su especializacion siempre y cuando este deshabilitado y los ordenada de forma ascendente por id 
    @NamedQuery(
            name = "findEspecializationeDisable", query = "SELECT m FROM Commercial m WHERE m.especialization:especialization AND m.status=:'DISABLED' ORDER BY m.id ASC ")
    ,
    //Busca comerciales por su especializacion ,tanto habilitado y deshabilitado y los ordenada de forma ascendente por id 
    @NamedQuery(
            name = "findEspecializationeAll", query = "SELECT m FROM Commercial m WHERE m.especialization:especialization ORDER BY m.id ASC")
    ,
    //INsertar commercial
    @NamedQuery(name = "insertCommercial", query = "INSERT INTO Commercial m WHERE m.login=:login AND m.password=:password")
        ,
    //Actualiza la contraseña a todos los usuarios deshabilitados
    @NamedQuery(
    name="updateLoginCommercial", query="UPDATE Commercial m SET m.password = :password Where m.status=:'DISABLE'  "),

})

/**
 * Esta clase es un tipo de Usuario que extiende de User
 *
 * @author Enaitz Izagirre
 */
@Entity
@Table(name = "COMMERCIAL", schema = "reto2g1c")
@XmlRootElement
public class Commercial extends User implements Serializable {

    //La especializacion del Comercial se implementa mediante las opciones de la clase Especialization
    @Enumerated(EnumType.STRING)
    private Especialization especialization;

    //La lista de clientes que tiene un comercial 
    @OneToMany(mappedBy = "commercial")
    private Set<Client> clients;

    /**
     * Metodo para obtener la especializacion
     *
     * @return Devuelve una Especializacion
     */
    public Especialization getEspecialization() {
        return especialization;
    }

    /**
     * Metodo`para obtener el Listado de clientes
     *
     * @return Devuelve el array de clientes
     */
    // @XmlTransient
    public Set<Client> getClients() {
        return (Set<Client>) clients;
    }

    /**
     * Metodo para establecer la especializacion
     *
     * @param especialization
     */
    public void setEspecialization(Especialization especialization) {
        this.especialization = especialization;
    }

    /**
     * Metodo para establecer un nuevo Listado de Clientes
     *
     * @param clients
     */
    public void setClients(Set<Client> clients) {
        this.clients = (Set<Client>) clients;
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
