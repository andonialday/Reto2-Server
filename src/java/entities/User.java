/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase con los parámetros para la creación de usuarios
 * @author Jaime San Sebastián y Enaitz Izaguirre
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER", schema="reto2g1c")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    
    private String login;
    private String email;
    private String fullName;
    
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    
    @Enumerated(EnumType.STRING)
    private Privilege privilege;
    private String password;
    
    @Temporal (TemporalType.TIMESTAMP)
    private Timestamp lastPasswordChange;

    /**
     * @return el id de un usuario
     */
    public int getId() {
        return id;
    }

    /**
     * @param id el id de un usuario a establecer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return el login de un usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login el login de un usuario a establecer
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return el email de un usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email el email de un usuario a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return el nombre completo de un usuario
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName el nombre completo de un usuario a establecer
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return el estado de un usuario
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * @param status el estado de un cliente a establecer
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * @return el privilegio de un usuario
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * @param privilege el privilegio de un usuario a establecer
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    /**
     * @return la contraseña de un usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password la contraseña de un usuario a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return el cambio de contraseña de un usuario
     */
    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * @param lastPasswordChange el cambio de contraseña de un usuario a establecer
     */
    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", email=" + email + ", fullName=" + fullName + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.id;
        hash = 73 * hash + Objects.hashCode(this.login);
        hash = 73 * hash + Objects.hashCode(this.email);
        hash = 73 * hash + Objects.hashCode(this.fullName);
        hash = 73 * hash + Objects.hashCode(this.status);
        hash = 73 * hash + Objects.hashCode(this.privilege);
        hash = 73 * hash + Objects.hashCode(this.password);
        hash = 73 * hash + Objects.hashCode(this.lastPasswordChange);
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
        final User other = (User) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (this.privilege != other.privilege) {
            return false;
        }
        if (!Objects.equals(this.lastPasswordChange, other.lastPasswordChange)) {
            return false;
        }
        return true;
    }
    
}

