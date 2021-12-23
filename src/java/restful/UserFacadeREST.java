/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cypher.*;
import entities.Client;
import entities.Commercial;
import entities.Privilege;
import entities.User;
import entities.UserStatus;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
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

/**
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
        String descifrado = new String(Decrypt.decrypt(entity.getPassword().getBytes()), StandardCharsets.UTF_8);
        entity.setPassword(Hashing.cifrarTexto(descifrado));
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, User entity) { 
        String descifrado = new String(Decrypt.decrypt(entity.getPassword().getBytes()), StandardCharsets.UTF_8);
        entity.setPassword(Hashing.cifrarTexto(descifrado));
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
    @Path("signIn/{user}")
    @Produces({MediaType.APPLICATION_XML})
    public User SignIn(@PathParam("user") User user)
            throws InternalServerErrorException {

        try {
            LOGGER.info("Finding user");
            String key = Hashing.cifrarTexto(user.getPassword());
            user = (User) em.createNamedQuery("findUserByLogin")
                    .setParameter("login", user.getLogin()).setParameter("password", key)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error finding user."
                    + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);

        }
        if (user.getPrivilege() == Privilege.ADMIN) {
            return user;
        } else {

            if (user.getStatus() == UserStatus.ENABLED) {
                LOGGER.info("User enabled");

                if (user instanceof Client) {
                    LOGGER.info("The User is a client");
                    Client client = (Client) user;
                    return client;

                } else if (user instanceof Commercial) {
                    LOGGER.info("The User is a commercial");
                    Commercial commercial = (Commercial) user;
                    return commercial;
                }

            } else {
                LOGGER.info("User disabled");
            }
        }

        return user;
    }
    
    @GET
    @Path("resetPassword/{user}")
    public void resetPasswordByLogin(@PathParam("user") String login)
            throws InternalServerErrorException {
            User user;
            String newKey;
            String safeKey;
        try {
            //FIND USER BY LOGIN
            LOGGER.info("Finding user");
            user = (User) em.createNamedQuery("findUserByLogin")
                    .setParameter("login", login)
                    .getSingleResult();
            if (user != null) {
            //GENERATE RANDOM PASSWORD IF USER EXISTS
                newKey = RandomPasswordGenerator.generateRandomKey();
            //ENVIAR POR EMAIL
                Email.sendPasswordReset(user.getEmail(), newKey);
            //HASH PASSWORD
                safeKey = Hashing.cifrarTexto(newKey);
            //UPDATE USER PASSWORD ON DATABASE
                user.setPassword(safeKey);
                updatePass(user.getId(), user);
            }
        } catch (Exception e) {
            LOGGER.severe("Error finding user."
                    + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);

        }
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void updatePass(@PathParam("id") Integer id, User entity) {
        String descifrado = new String(Decrypt.decrypt(entity.getPassword().getBytes()), StandardCharsets.UTF_8);
        entity.setPassword(Hashing.cifrarTexto(descifrado));
        super.create(entity);
    }
    
@Override
        protected EntityManager getEntityManager() {
        return em;
    }

}
