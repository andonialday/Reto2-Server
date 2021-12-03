/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;

/**
 * La enumeración del estado de la cuenta del usuario, con los posibles valores
 * del campo
 *
 * @author Jaime San Sebastián y Enaitz Izaguirre
 */
public enum UserStatus implements Serializable {

    /**
     * Opción 0 de enumeración, el usuario está habilitado
     */
    ENABLED,
    /**
     * Opción 1 de enumeración, el usuario está deshabilitado
     */
    DISABLED;
}
