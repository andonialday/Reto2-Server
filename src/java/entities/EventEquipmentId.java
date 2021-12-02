/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Andoni Alday , Aitor Perez
 */
@Embeddable
public class EventEquipmentId implements Serializable{
    
    private Event evenId;
    private Equipment equipmentId;

    public Event getEvenId() {
        return evenId;
    }

    public void setEvenId(Event evenId) {
        this.evenId = evenId;
    }

    public Equipment getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Equipment equipmentId) {
        this.equipmentId = equipmentId;
    }
}
