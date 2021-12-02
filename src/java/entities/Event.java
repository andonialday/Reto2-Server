/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.CascadeType.ALL;

/**
 * Entidad Event para la gestion
 *
 * @author Andoni Alday
 */
@Entity
@Table(name = "EVENT")
public class Event implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateStart;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateEnd;

    private String description;

    @ManyToOne
    private Client client;
    
    @OneToMany(cascade = ALL, mappedBy = "event")
    private Set<EventEquipment> equipments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
     
    public Set<EventEquipment> getEquipments() {
        return equipments;
    }

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
        final Event other = (Event) obj;
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
