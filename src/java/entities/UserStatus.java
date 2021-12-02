/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;

/**
 * La enumeración del estado para el usuario, con los posibles valores del campo
 * @author Jaime San Sebastián y Enaitz Izaguirre
 */
public enum UserStatus implements Serializable{

    /**
     * Si la opción de enumeración es 0, el usuario está habilitado
     */
    ENABLED,

    /**
     * Si la opción de enumeración es 1, el usuario está deshabilitado
     */
    DISABLED;
}