/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 * UserStatus enumeration for the user, with the possbile values ofthe field
 * @author Andoni Alday
 */
public enum UserStatus implements Serializable{

    /**
     * Enumeration option 0, the user is Enabled
     */
    ENABLED,

    /**
     * Enumeration option 1, the useris Dissabled
     */
    DISABLED;
}