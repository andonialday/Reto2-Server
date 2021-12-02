/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;

/**
 * El tipo de enumeración para el cliente, indicando su tipo
 * @author Jaime San Sebastian
 */
public enum Type implements Serializable{

    /**
     * Si la opción de enumeración es 0, el cliente es un particular
     */
    PARTICULAR,

    /**
     * Si la opción de enumeración es 1, el cliente es una asociación
     */
    ASOCIATION,
    
    /**
     * Si la opción de enumeración es 2, el cliente es una empresa
     */
    ENTERPRISE,
    
    /**
     * Si la opción de enumeración es 3, el cliente es un ente público
     */
    PUBLIC_ENTITY;
}