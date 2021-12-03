/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Clase para generación de ID Combinada de la relación EventEquipment 
 * @author Andoni Alday , Aitor Perez
 */
@Embeddable
public class EventEquipmentId implements Serializable{
    
    private Event eventId;
    private Equipment equipmentId;

    /**
     * Método Getter de la ID del Event de la relación EventEquipment
     * @return eventId ID del Event de la relación EventEquipment
     */
    public Event getEventId() {
        return eventId;
    }

    /**
     * Método Setter para asignar un Event a la relación EventEquipment
     * @param eventId ID del Event a asignar a la relación EventEquipment
     */
    public void setEvenId(Event eventId) {
        this.eventId = eventId;
    }

    /**
     * Método Getter de la ID del Equipment de la relación EventEquipment
     * @return equipmentId ID del Equipment de la relación EventEquipment
     */
    public Equipment getEquipmentId() {
        return equipmentId;
    }

    /**
     * Método Setter para asignar un Equipment a la relación EventEquipment
     * @param equipmentId ID del Equipment a asignar a la relación EventEquipment
     */
    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }
}
