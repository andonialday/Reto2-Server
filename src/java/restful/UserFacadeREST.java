/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cypher.*;
import entities.Privilege;
import entities.User;
import entities.UserStatus;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase RESTful del cliente con las queries generadas por Hibernate
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
@Stateless
@Path("entities.user")
public class UserFacadeREST extends AbstractFacade<User> {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        byte[] pass = DatatypeConverter.parseHexBinary(entity.getPassword());
        String descifrado = new String(DecryptASim.decrypt(pass), StandardCharsets.UTF_8);
        entity.setPassword(DatatypeConverter.printHexBinary(Hashing.cifrarTexto(descifrado)));
        super.create(entity);
    }

    @PUT
    @Path("{id}/{password}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, User entity) {
        byte[] pass = DatatypeConverter.parseHexBinary(entity.getPassword());
        String descifrado = new String(DecryptASim.decrypt(pass), StandardCharsets.UTF_8);
        entity.setPassword(DatatypeConverter.printHexBinary(Hashing.cifrarTexto(descifrado)));
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("signIn/{login}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> signIn(@PathParam("login") String login, @PathParam("password") String password) throws InternalServerErrorException {
        List<User> user = new ArrayList<>();
        User usr = null;
        try {
            LOGGER.info("Finding user");
            byte[] pass = DatatypeConverter.parseHexBinary(password);
            String descifrado = new String(DecryptASim.decrypt(pass), StandardCharsets.UTF_8);
            //Hasear la contraseña con MD5
            String key = DatatypeConverter.printHexBinary(Hashing.cifrarTexto(descifrado));
            usr = (User) em.createNamedQuery("signInQuery").setParameter("loginId", login).setParameter("key", key).getSingleResult();
            if (usr != null) {
                LOGGER.info("User found!!");
                StoredProcedureQuery query = em.createStoredProcedureQuery("reto2g1c.login").registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN).setParameter(1, usr.getId());
                LOGGER.info("Initianing post-login procedure");
                query.execute();
                //hacer q la contraseña nunca vuelva al cliente llena
                if (usr.getPrivilege() != Privilege.ADMIN) {
                    if (usr.getStatus() == UserStatus.ENABLED) {
                        LOGGER.info("User enabled");
                        user.add(usr);
                        return user;
                    } else {
                        usr = null;
                        user.add(usr);
                        return user;
                    }
                } else {
                    user.add(usr);
                    return user;
                }
            }
        } catch (Exception e) {
            LOGGER.severe("Error finding user." + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);
        }
        user.add(usr);
        return user;
    }

    //Query propia para resetear la contraseña por el login
    @GET
    @Path("resetPassword/{log}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetPasswordByLogin(@PathParam("log") String log)
            throws InternalServerErrorException {
        String newKey;
        String safeKey;
        User user;
        try {
            //FIND USER BY LOGIN
            LOGGER.info("Finding user");
            user = (User) em.createNamedQuery("resetPasswordByLogin")
                    .setParameter("login", log)
                    .getSingleResult();
            if (user != null) {
                //GENERATE RANDOM PASSWORD IF USER EXISTS
                newKey = RandomPasswordGenerator.generateRandomKey();
                //ENVIAR POR EMAIL
                Email.sendPasswordReset(user.getEmail(), newKey);
                //HASH PASSWORD
                safeKey = DatatypeConverter.printHexBinary(Hashing.cifrarTexto(newKey));
                //UPDATE USER PASSWORD ON DATABASE
                user.setPassword(safeKey);
                //super.edit(user);
            }
        } catch (MessagingException e) {
            LOGGER.severe("Error on password reset " + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @PUT
    @Path("updatePassword/{id}/{password}")
    @Consumes({MediaType.APPLICATION_XML})
    public void updatePass(@PathParam("id") String id, @PathParam("password") String password) {
        LOGGER.info("Cambiando contraseña");
        User user;
        try {
            user = this.find(Integer.valueOf(id));
            byte[] pass = DatatypeConverter.parseHexBinary(password);
            String descifrado = new String(DecryptASim.decrypt(pass), StandardCharsets.UTF_8);
            String key = DatatypeConverter.printHexBinary(Hashing.cifrarTexto(descifrado));
            user.setPassword(key);
            Email.sendPasswordChange(user.getEmail());
            super.edit(user);
        } catch (MessagingException ex) {
            Logger.getLogger(UserFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("signUp/{login}/{email}/{password}/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public User signUp(@PathParam("login") String login,
            @PathParam("email") String email,
            @PathParam("password") String password,
            @PathParam("name") String name) throws InternalServerErrorException {
        User user = null;
        LOGGER.info("Finding existing user with that login");
        try {
            user = (User) em.createNamedQuery("resetPasswordByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception ex) {
            LOGGER.info("Login disponible");
        }
        if (user == null) {
            try {
                byte[] pass = DatatypeConverter.parseHexBinary(password);
                String descifrado = new String(DecryptASim.decrypt(pass), StandardCharsets.UTF_8);
                //Hasear la contraseña con MD5
                String key = DatatypeConverter.printHexBinary(Hashing.cifrarTexto(descifrado));
                // Añadiendo Cliente mediante procedimiento
                StoredProcedureQuery query = em.createStoredProcedureQuery("reto2g1c.register")
                        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, login)
                        .registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, email)
                        .registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, name)
                        .registerStoredProcedureParameter(4, String.class, ParameterMode.IN).setParameter(4, key);
                query.execute();
                user = (User) em.createNamedQuery("resetPasswordByLogin").setParameter("login", login).getSingleResult();
            } catch (Exception ex) {
                LOGGER.info("Login disponible");
            }
        } else {
            LOGGER.info("Login on use");
        }
        return user;
    }

}
