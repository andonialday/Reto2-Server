/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cypher.DecryptASim;
import cypher.Hashing;
import entities.Client;
import entities.Commercial;
import entities.Privilege;
import entities.User;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase RESTful del cliente con las queries generadas por Hibernate
 *
 * @author Jaime San Sebastián
 */
@Stateless
@Path("entities.client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;
    private static final Logger LOGGER = Logger.getLogger("package.class");

    public ClientFacadeREST() {
        super(Client.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity) {
        super.create(entity);
        //hashear la contraseña
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Client entity) {
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
    public Client find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Client> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Client> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    //Query propia para encontrar el comercial de un cliente
    @GET
    @Path("commercial/{idClient}")
    @Produces({MediaType.APPLICATION_XML})
    public Commercial findClientCommercial(@PathParam("idClient") Integer idClient)
            throws InternalServerErrorException {
        Commercial commercial = null;
        try {
            LOGGER.info("Finding client commercial");
            commercial = (Commercial) em.createNamedQuery("findClientCommercial")
                    .setParameter("idClient", idClient)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.severe("Error finding client commercial."
                    + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);
        }
        return commercial;
    }

    //Query propia para eliminar todos los clientes que estén deshabilitados
    @DELETE
    @Path("client/{privilege}")
    @Produces({MediaType.APPLICATION_XML})
    public void deleteAllClientDisabled(@PathParam("privilege") Privilege privilege)
            throws InternalServerErrorException {
        Client client = null;
        try {
            LOGGER.info("Deleting all clients disabled");
            client = (Client) em.createNamedQuery("deleteAllClientDisabled")
                    .setParameter("privilege", privilege)
                    .getSingleResult();
        } catch (Exception e) {
            LOGGER.severe("Error deleting all clients disabled."
                    + e.getLocalizedMessage());
            throw new InternalServerErrorException(e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("signUp/{login}/{email}/{password}/{name}/{tipo}")
    @Produces({MediaType.APPLICATION_XML})
    public User signUp(@PathParam("login") String login, @PathParam("email") String email, @PathParam("password") String password, @PathParam("name") String name, @PathParam("tipo") String tipo) throws InternalServerErrorException {
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
                StoredProcedureQuery query = em.createStoredProcedureQuery("reto2g1c.registerClient")
                        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, login)
                        .registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, email)
                        .registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, name)
                        .registerStoredProcedureParameter(4, String.class, ParameterMode.IN).setParameter(4, key)
                        .registerStoredProcedureParameter(5, String.class, ParameterMode.IN).setParameter(5, tipo);
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
