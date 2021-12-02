/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 * Enumeracion Especialization del Commercial, indicando su especialziacion profesional
 * @author Enaitz Izagirre
 */
public enum Especialization implements Serializable{

    /**
     * Opcion 0 de la enumeracion, el Commercial es un técnico de sonido
     */
    ADMIN,

    /**
     * Enumeration option 1, the user has User privileges
     */
    USER;
}