/*
 * Para cambiar este encabezado de licencia, elija Encabezados de licencia en Propiedades del proyecto.
 * Para cambiar este archivo de plantilla, elija Herramientas | Plantillas
 * y abra la plantilla en el editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase con los parámetros para la creación y gestión de usuarios
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */

@NamedQueries({
    
    @NamedQuery(name="findUserByLogin", query="SELECT u FROM User u WHERE u.login=:login AND u.password=:password AND u.status IS 'ENABLED'"),
    
})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "USER", schema = "reto2g1c")
@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String login;
    private String email;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Privilege privilege;
    private String password;

    @Temporal(TemporalType.DATE)
    private Date lastPasswordChange;

    /**
     * Método Getter para obtener la ID del usuario
     *
     * @return el id de un usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Método Setter para asignar una ID al usuario
     *
     * @param id el id de un usuario a establecer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Método Getter para obtener el login del usuario
     *
     * @return el login de un usuario
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método Setter para asignar un login al usuario
     *
     * @param login el login de un usuario a establecer
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Métogo Getter para obtener el email del usuario
     *
     * @return el email de un usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método Setter para asignar el email al usuario
     *
     * @param email el email de un usuario a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Método Getter para obtener el Nombre Completo del usuario
     *
     * @return el nombre completo de un usuario
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Método Setter para asignar el Nombre Completo al usuario
     *
     * @param fullName el nombre completo de un usuario a establecer
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Método Getter para obtener el estado de la cuenta del usuario
     *
     * @return el estado de la cuente de un usuario
     */
    public UserStatus getStatus() {
        return status;
    }

    /**
     * Método Setter para determinar el estado de la cuenta del usuario
     *
     * @param status el estado de la cuenta del usuario
     */
    public void setStatus(UserStatus status) {
        this.status = status;
    }

    /**
     * Método Getter para obtener el nivel de privilegio del usuario
     *
     * @return el privilegio de un usuario
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     * Método Setter para asignar el nivel de privilegio al usuario
     *
     * @param privilege el privilegio de un usuario a establecer
     */
    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    /**
     * Método Getter para obtener la contraseña del usuario
     *
     * @return la contraseña de un usuario
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método Setter para asignar una contraseña al usuario
     *
     * @param password la contraseña de un usuario a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método Getter para obtener el momento del último cambio de contraseña del
     * usuario
     *
     * @return el cambio de contraseña de un usuario
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Método Getter para obtener el momento del último cambio de contraseña del
     * usuario
     *
     * @param lastPasswordChange el cambio de contraseña de un usuario a
     * establecer
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
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
