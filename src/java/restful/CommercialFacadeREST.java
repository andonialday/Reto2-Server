/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cypher.DecryptASim;
import cypher.Hashing;
import entities.Commercial;
import entities.Especialization;
import entities.User;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
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
import javax.xml.bind.DatatypeConverter;

/**
 * Clase Restfull del commercial
 *
 * @author Enaitz Izagirre
 */
@Stateless
@Path("entities.commercial")
public class CommercialFacadeREST extends AbstractFacade<Commercial> {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    /**
     * Constructor vacio de la clase
     */
    public CommercialFacadeREST() {
        super(Commercial.class);
    }

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Crea la entidad Comercial
     *
     * @param entity la entidad comercial
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Commercial entity) {
        super.create(entity);
    }

    /**
     * Edita la entidad Comercial
     *
     * @param id del Commercial
     * @param entity la entidad Commercial
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Commercial entity) {
        super.edit(entity);
    }

    /**
     * Elimina la entidad Comercial
     *
     * @param id del Commercial
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     * Recoge la id del COmmercial
     *
     * @param id la id del Commercial
     * @return devuelve el id encontrado
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Commercial find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    /**
     * busca todos los commerciales de la lista
     *
     * @return Devuelve toda la lista de commerciales
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> findAll() {
        return super.findAll();
    }

    /**
     * Busca en el rango que le introduce de la lista de commerciales
     *
     * @param from rango minimo
     * @param to rango maximo
     * @return devuelve el rango
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    /**
     * Cuenta la cantidad de Commerciales
     *
     * @return Devuelve la cuenta
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Ejecuta la query findEspecializationeEnable
     *
     * @param especialization parametro que introduce el usuario para filtrar
     * los comerciales
     * @return Devuelve los comerciales que cumplen con los requisitos de la
     * query , en este caso por specializacion
     */
    @GET
    @Path("especializationEnabled/{especialization}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> getEspecializationByCommercialEnable(@PathParam("especialization") String especialization) {

        List<Commercial> commercials = null;

        try {
            commercials = em.createNamedQuery("findEspecializationEnable").setParameter("especialization", Especialization.valueOf(especialization)).getResultList();
        } catch (Exception e) {
            LOGGER.info("Error query findEspecialization");
        }
        return commercials;
    }

    /**
     * Ejecuta la query findEspecializationeDisable
     *
     * @param especialization parametro que introduce el usuario para filtrar
     * los comerciales
     * @return Devuelve los comerciales que cumplen con los requisitos de la
     * query , en este caso por specializacion
     */
    @GET
    @Path("especializationDisable/{especialization}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> getEspecializationByCommercialDisable(@PathParam("especialization") String especialization) {

        List<Commercial> commercials = null;

        try {
            commercials = em.createNamedQuery("findEspecializationDisable").setParameter("especialization", Especialization.valueOf(especialization)).getResultList();
        } catch (Exception e) {
            LOGGER.info("Error query findEspecialization");
        }
        return commercials;
    }

    /*    
     * Ejecuta la query findEspecializationeAll 
     * @param especialization parametro que introduce el usuario para filtrar los comerciales 
     * @return Devuelve los comerciales que cumplen con los requisitos de la query , en este caso por specializacion
     */
    @GET
    @Path("especializationAll/{especialization}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> getEspecializationByCommercialAll(@PathParam("especialization") String especialization) {

        List<Commercial> commercials = null;

        try {
            commercials = em.createNamedQuery("findEspecializationAll").setParameter("especialization", Especialization.valueOf(especialization)).getResultList();
        } catch (Exception e) {
            LOGGER.info("Error query findEspecialization");
        }
        return commercials;
    }

    
    @GET
    @Path("signUp/{login}/{email}/{password}/{name}/{espec}")
    @Produces({MediaType.APPLICATION_XML})
    public User signUp(@PathParam("login") String login, @PathParam("email") String email, @PathParam("password") String password, @PathParam("name") String name, @PathParam("espec") String espec) throws InternalServerErrorException {
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
                // Añadiendo Commercial mediante procedimiento
                StoredProcedureQuery query = em.createStoredProcedureQuery("reto2g1c.registerCommercial")
                        .registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, login)
                        .registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, email)
                        .registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(3, name)
                        .registerStoredProcedureParameter(4, String.class, ParameterMode.IN).setParameter(4, key)
                        .registerStoredProcedureParameter(5, String.class, ParameterMode.IN).setParameter(5, espec);
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
