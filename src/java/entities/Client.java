/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Clase con los parámetros para la creación y gestión de clientes
 *
 * @author Jaime San Sebastián
 */
@Entity
@Table(name = "CLIENT", schema = "reto2g1c")
public class Client extends User {

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(cascade = ALL, mappedBy = "client")
    private Set<Event> events;

    @ManyToOne
    private Commercial comercial;

    /**
     * Método Getter para obtener el tipo de un cliente
     *
     * @return el tipo de un cliente
     */
    public Type getType() {
        return type;
    }

    /**
     * Método Setter para asignar el tipo a un cliente
     *
     * @param type el tipo de un cliente a establecer
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Método Getter para obtener los eventos del cliente
     *
     * @return los eventos de un cliente
     */
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Método Setter para asignar eventos a un cliente
     *
     * @param events los eventos de un cliente a establecer
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * Método Geter para obtener el Comercial asignado a un cliente
     *
     * @return el comercial de un cliente
     */
    public Commercial getComercial() {
        return comercial;
    }

    /**
     * Método Setter para asignar un Comercial al cliente
     *
     * @param comercial el comercial de un cliente a establecer
     */
    public void setComercial(Commercial comercial) {
        this.comercial = comercial;
    }

    @Override
    public String toString() {
        return "Client{" + "Type=" + type + ", events=" + events + ", comercial=" + comercial + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.events);
        hash = 37 * hash + Objects.hashCode(this.comercial);
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
        final Client other = (Client) obj;
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        if (!Objects.equals(this.comercial, other.comercial)) {
            return false;
        }
        return true;
    }

}
