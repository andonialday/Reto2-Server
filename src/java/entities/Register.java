/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para la gestión de los últimos inicios de sesión de los usuarios
 *
 * @author Andoni Alday
 */
@Entity
@Table(name = "SIGNIN", schema = "reto2g1c")
@XmlRootElement
public class Register implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User user;

    @Temporal(TemporalType.DATE)
    private Date lastSignIn;

    /**
     * Método Getter para obtener la ID de un inicio se sesión del usuario
     *
     * @return el id de un inicio de sesión del usuario
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método Setter para determinar la ID de un inicio se sesión del usuario
     *
     * @param id el id de un inicio de sesión del usuario
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método Getter para obtener el User al que pertenece el inicio de sesion
     *
     * @return el User al que pertenece el inicio de sesion
     */
    public User getUser() {
        return user;
    }

    /**
     * Método Setter para asignar el User al que pertenece el inicio de sesion
     *
     * @param user el User al que pertenece el inicio de sesion
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Método Getter para obtener la fecha en la que se inició sesión
     *
     * @return la fecha del inicio desesión
     */
    public Date getLastSignIn() {
        return lastSignIn;
    }

    /**
     * Método Setter para determinar la fecha en la que se inició sesión
     *
     * @param lastSignIn fecha del inicio de sesión
     */
    public void setLastSignIn(Date lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    @Override
    public String toString() {
        return "SignIn{" + "id=" + id + ", user=" + user + ", lastSignIn=" + lastSignIn + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.user);
        hash = 97 * hash + Objects.hashCode(this.lastSignIn);
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
        final Register other = (Register) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.lastSignIn, other.lastSignIn)) {
            return false;
        }
        return true;
    }

}
