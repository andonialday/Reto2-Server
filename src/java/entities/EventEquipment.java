/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

/**
 *
 * @author Andoni Alday , Aitor Perez
 */
@Entity
@Table (name="EVENTEQUIPMENT")
public class EventEquipment implements Serializable{
    
    @EmbeddedId
    private EventEquipmentId eventEquipmentId;
    
    @ManyToOne
    @MapsId ("eventId")
    private Event event;
    
    @ManyToOne
    @MapsId ("equipmentId")
    private Equipment equipment;
    
    private Integer quantity;

    public EventEquipmentId getEventEquipmentId() {
        return eventEquipmentId;
    }

    public void setEventEquipmentId(EventEquipmentId eventEquipmentId) {
        this.eventEquipmentId = eventEquipmentId;
    }
    
    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.event);
        hash = 53 * hash + Objects.hashCode(this.equipment);
        hash = 53 * hash + Objects.hashCode(this.quantity);
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
        final EventEquipment other = (EventEquipment) obj;
        if (!Objects.equals(this.event, other.event)) {
            return false;
        }
        if (!Objects.equals(this.equipment, other.equipment)) {
            return false;
        }
        if (!Objects.equals(this.quantity, other.quantity)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EventEquipment{" + "event=" + event + ", equipment=" + equipment + ", quantity=" + quantity + '}';
    }
    
    
    
}
