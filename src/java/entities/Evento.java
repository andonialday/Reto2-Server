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
@NamedQueries({
    @NamedQuery(
            name = "findDateStartRange", query = "SELECT v FROM Evento v WHERE v.dateStart>:date1 AND v.dateStart<:date2")
    ,
    @NamedQuery(
            name = "findDateEndRange", query = "SELECT v FROM Evento v WHERE v.dateEnd>:date1 AND v.dateEnd<:date2")
    ,
    @NamedQuery(
            name = "findDateRange", query = "SELECT v FROM Evento v WHERE v.dateStart>:date1 AND v.dateEnd<:date2")
    ,
    @NamedQuery(
            name = "findDateStartRangeClient", query = "SELECT v FROM Evento v WHERE v.dateStart>:date1 AND v.dateStart<:date2 AND v.client.id=:idCli")
    ,
    @NamedQuery(
            name = "findDateEndRangeClient", query = "SELECT v FROM Evento v WHERE v.dateEnd>:date1 AND v.dateEnd<:date2 AND v.client.id=:idCli")
    ,
    @NamedQuery(
            name = "findDateRangeClient", query = "SELECT v FROM Evento v WHERE v.dateStart>:date1 AND v.dateEnd<:date2 AND v.client.id=:idCli")
    ,
    @NamedQuery(
            name = "findEventByClient", query = "SELECT v FROM Evento v WHERE v.client.id=:idCli")
    ,
    @NamedQuery(
            name = "deleteOldestEvents", query = "DELETE FROM Evento v WHERE v.dateEnd<:date")
})
@Entity
@Table(name = "EVENT", schema = "reto2g1c")
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

    private String name;

    @ManyToOne
    private Client client;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<EventEquipment> equipments;

    /**
     * M??todo Getter para obtener la ID del Evento
     *
     * @return ID del Evento
     */
    public Integer getId() {
        return id;
    }

    /**
     * M??todo Setter para definir la ID del Evento
     *
     * @param id a asignar al Evento
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * M??todo Getter para obtener la DateStart <i>(Fecha de Inicio)</i> del
     * Evento
     *
     * @return DateStart <i>(Fecha de Inicio)</i> del Evento
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * M??todo Setter para definir la DateStart <i>(Fecha de Inicio)</i> del
     * Evento
     *
     * @param dateStart <i>(Fecha de Inicio)</i> a asignar al Evento
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * M??todo Getter para obtener la DateEnd <i>(Fecha de Fin)</i> del Evento
     *
     * @return DateEnd <i>(Fecha de Fin)</i> del Evento
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * M??todo Setter para asignar la DateEnd <i>(Fecha de Fin)</i> al Evento
     *
     * @param dateEnd <i>(Fecha de Fin)</i> a asignar al Evento
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    /**
     * M??todo Getter para obtener la Description <i>(Descripcion)</i> del Evento
     *
     * @return Description <i>(Descripcion)</i> del Evento
     */
    public String getDescription() {
        return description;
    }

    /**
     * M??todo Setter para asignar la Description <i>(Descripcion)</i> al Evento
     *
     * @param description <i>(Descripcion)</i> a asignar al Evento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * M??todo Getter para obtener el Name <i>(Nombre)</i> del Evento
     *
     * @return Name <i>(Nombre)</i> del Evento
     */
    public String getName() {
        return name;
    }

    /**
     * M??todo Setter para asignar el Name <i>(Nombre)</i> al Evento
     *
     * @param name <i>(Nombre)</i> a asignar al Evento
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * M??todo Getter para obtener el Client <i>(Client)</i> "propietario" del
     * Evento
     *
     * @return Client<i>(Cliente)</i> "propietario" del Evento
     */
    @XmlTransient
    public Client getClient() {
        return client;
    }

    /**
     * M??todo Setter para asignar el Client <i>(Client)</i> "propietario" al
     * Evento
     *
     * @param client Client <i>(Cliente)</i> "propietario" a asignar al Evento
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * M??todo Getter para obtener los Equipments <i>(Equipamientos
     * empleados)</i> del Evento
     *
     * @return Equipments <i>(Equipamientos empleados)</i> del Evento
     */
    @XmlTransient
    public Set<EventEquipment> getEquipments() {
        return equipments;
    }

    /**
     * M??todo Setter para asignar los Equipments <i>(Equipamientos
     * empleados)</i> al Evento
     *
     * @param equipments Equipments <i>(Equipamientos empleados)</i> a asignar
     * al Evento
     */
    public void setEquipments(Set<EventEquipment> equipments) {
        this.equipments = equipments;
    }

    /**
     * M??todo ToString para obtener una representaci??n en forma de texto de los
     * datos de una instancia de Evento
     *
     * @return la representacion en texto de los valores
     */
    @Override
    public String toString() {
        return "Evento{" + "id=" + id + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", description=" + description + ", name=" + name + ", client=" + client + ", equipments=" + equipments + '}';
    }

    /**
     * M??todo para determinar un c??digo identificativo de la instancia de Evento
     * en funcion de sus datos
     *
     * @return el c??digo identificativo de la instancia
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.dateStart);
        hash = 41 * hash + Objects.hashCode(this.dateEnd);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.client);
        hash = 41 * hash + Objects.hashCode(this.equipments);
        return hash;
    }

    /**
     * M??todo para comparar si dos intancias de Evento son iguales en funci??n de sus datos
     * @param obj segunda instancia a comparar
     * @return boolean indicativo de la igualdad
     */
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
        if (!Objects.equals(this.name, other.name)) {
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
