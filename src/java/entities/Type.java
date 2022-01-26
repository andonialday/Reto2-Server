/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;

/**
 * Enumeración Type para el Cliente, indicando el tipo del mismo.
 * El cliente puede ser un particular, una asociación, 
 * una empresa o una entidad pública
 *
 * @author Jaime San Sebastian
 */
public enum Type implements Serializable {

    /**
     * Opción 0 de enumeración, el cliente es un particular
     */
    PARTICULAR,
    /**
     * Opción 1 de enumeración, el cliente es una asociación
     */
    ASOCIATION,
    /**
     * Opción 2 de enumeración, el cliente es una empresa
     */
    ENTERPRISE,
    /**
     * Opción 3 de enumeración, el cliente es un ente público
     */
    PUBLIC_ENTITY;
}
