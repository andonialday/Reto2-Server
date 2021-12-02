/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;

/**
 * Enumeracion Type para el User Client, indicando su tipo
 * @author Jaime San Sebastian
 */
public enum Type implements Serializable{

    /**
     * Opcion 0 de la enumeracion, el Client es un particular
     */
    PARTICULAR,

    /**
     * Opcion 1 de la enumeracion, el Client es una asociacion
     */
    ASOCIATION,
    
    /**
     * Opcion 2 de la enumeracion, el Client es una empresa
     */
    ENTERPRISE,
    
    /**
     * Opcion 3 de la enumeracion, el Client es un ente público
     */
    PUBLIC_ENTITY;
}