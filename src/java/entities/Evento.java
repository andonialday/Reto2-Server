/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Evento para la gestion de eventos
 *
 * @author Andoni Alday
 */
@NamedQueries ({
    @NamedQuery (
            name="findDateStartRange" , query="SELECT v FROM Event v WHERE v.dateStart>:date1 AND v.dateStart<:date2"),
    @NamedQuery (
            name="findDateEndRange" , query="SELECT v FROM Event v WHERE v.dateEnd>:date1 AND v.dateEnd<:date2"),
    @NamedQuery (
            name="findDateRange" , query="SELECT v FROM Event v WHERE v.dateStart>:date1 AND v.dateEnd<:date2"),
    @NamedQuery (
            name="findDateStartRangeClient" , query="SELECT v FROM Event v WHERE v.dateStart>:date1 AND v.dateStart<:date2 AND v.client.id=:idCli"),
    @NamedQuery (
            name="findDateEndRangeClient" , query="SELECT v FROM Event v WHERE v.dateEnd>:date1 AND v.dateEnd<:date2 AND v.client.id=:idCli"),
    @NamedQuery (
            name="findDateRangeClient" , query="SELECT v FROM Event v WHERE v.dateStart>:date1 AND v.dateEnd<:date2 AND v.client.id=:idCli"),
    @NamedQuery (
            name="findEventByClient" , query="SELECT v FROM Event v WHERE v.client.id=:idCli"),
    @NamedQuery (
            name="deleteOldestEvents" , query="DELETE FROM Event v WHERE v.dateEnd<:date")
})
@Entity
@Table(name = "EVENT", schema="reto2g1c")
@XmlRootElement
public class Evento implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    private String description;

    @ManyToOne
    private Client client;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<EventEquipment> equipments;

    /**
     * Método Getter para obtener la ID del Evento
     * @return ID del Evento
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método Setter para definir la ID del Evento
     * @param id a asignar al Evento
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método Getter para obtener la DateStart <i>(Fecha de Inicio)</i> del Evento
     * @return DateStart <i>(Fecha de Inicio)</i> del Evento
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Método Setter para definir la DateStart <i>(Fecha de Inicio)</i> del Evento
     * @param dateStart <i>(Fecha de Inicio)</i> a asignar al Evento
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Método Getter para obtener la DateEnd <i>(Fecha de Fin)</i> del Evento
     * @return DateEnd <i>(Fecha de Fin)</i> del Evento
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Método Setter para asignar la DateEnd <i>(Fecha de Fin)</i> al Evento
     * @param dateEnd <i>(Fecha de Fin)</i> a asignar al Evento
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * Método Getter para obtener la Description <i>(Descripcion)</i> del Evento
     * @return Description <i>(Descripcion)</i> del Evento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método Setter para asignar la Description <i>(Descripcion)</i> al Evento
     * @param description <i>(Descripcion)</i> a asignar al Evento
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Método Getter para obtener el Client <i>(Client)</i> "propietario" del Evento
     * @return Client<i>(Cliente)</i> "propietario" del Evento
     */
    @XmlTransient
    public Client getClient() {
        return client;
    }

    /**
     * Método Setter para asignar el Client <i>(Client)</i> "propietario" al Evento
     * @param client Client <i>(Cliente)</i> "propietario" a asignar al Evento
     */
    public void setClient(Client client) {
        this.client = client;
    }
     
    /**
     * Método Getter para obtener los Equipments <i>(Equipamientos empleados)</i> del Evento
     * @return Equipments <i>(Equipamientos empleados)</i> del Evento
     */
    @XmlTransient
    public Set<EventEquipment> getEquipments() {
        return equipments;
    }

    /**
     * Método Setter para asignar los Equipments <i>(Equipamientos empleados)</i> al Evento
     * @param equipments Equipments <i>(Equipamientos empleados)</i> a asignar al Evento
     */
    public void setEquipments(Set<EventEquipment> equipments) {
        this.equipments = equipments;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", description=" + description + ", client=" + client + ", equipments=" + equipments + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.dateStart);
        hash = 13 * hash + Objects.hashCode(this.dateEnd);
        hash = 13 * hash + Objects.hashCode(this.description);
        hash = 13 * hash + Objects.hashCode(this.client);
        hash = 13 * hash + Objects.hashCode(this.equipments);
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
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateStart, other.dateStart)) {
            return false;
        }
        if (!Objects.equals(this.dateEnd, other.dateEnd)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.equipments, other.equipments)) {
            return false;
        }
        return true;
    }
    
}
