/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Commercial;
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

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entities.commercial")
public class CommercialFacadeREST extends AbstractFacade<Commercial> {

    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    public CommercialFacadeREST() {
        super(Commercial.class);
    }

    /**
     * Crea la entidad Comercial
     * @param entity la entidad comercial 
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Commercial entity) {
        super.create(entity);
    }

    /**
     * 
     * @param id
     * @param entity 
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Commercial entity) {
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
    public Commercial find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    // NO Usada / Modificada abajo 
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> findAll() {
        return super.findAll();
    }

    
    // NO Usada 
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Commercial> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    // NO Usada
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    // COmmercial no tiene tipe ???
    @GET
    @Path("{tipe}")
    @Produces({MediaType.APPLICATION_XML})
    public Commercial find(@PathParam("tipe") String tipe) {
        return super.find(tipe);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
//    
//    /**
//     * Ejecuta la query findEspecializationeEnable 
//     * @param especialization parametro que introduce el usuario para filtrar los comerciales 
//     * @return Devuelve los comerciales que cumplen con los requisitos de la query , en este caso por specializacion
//     */
//    @GET
//    @Path("especialization/{especializationEnable}")
//    @Produces({MediaType.APPLICATION_XML})
//    public List<Commercial> getEspecializationByCommercialEnable(@PathParam("especialization") String especialization) {
//
//        List<Commercial> commercials = null;
//
//        try {
//            commercials = em.createNamedQuery("findEspecializationeEnable").setParameter("especialization", especialization).getResultList();
//        } catch (Exception e) {
//             logger.info("Error query findEspecialization");
//        }
//        return commercials;
//    }
//    
//    
//    /**
//     * Ejecuta la query findEspecializationeDisable 
//     * @param especialization parametro que introduce el usuario para filtrar los comerciales 
//     * @return Devuelve los comerciales que cumplen con los requisitos de la query , en este caso por specializacion
//     */
//    @GET
//    @Path("especialization/{especializationDisable}")
//    @Produces({MediaType.APPLICATION_XML})
//    public List<Commercial> getEspecializationByCommercialDisable(@PathParam("especialization") String especialization) {
//
//        List<Commercial> commercials = null;
//
//        try {
//            commercials = em.createNamedQuery("findEspecializationeDisable").setParameter("especialization", especialization).getResultList();
//        } catch (Exception e) {
//             logger.info("Error query findEspecialization");
//        }
//        return commercials;
//    }
//    
//    
//    
//    /**
//     * Ejecuta la query findEspecializationeAll 
//     * @param especialization parametro que introduce el usuario para filtrar los comerciales 
//     * @return Devuelve los comerciales que cumplen con los requisitos de la query , en este caso por specializacion
//     */
//    @GET
//    @Path("especialization/{especializationAll}")
//    @Produces({MediaType.APPLICATION_XML})
//    public List<Commercial> getEspecializationByCommercialAll(@PathParam("especialization") String especialization) {
//
//        List<Commercial> commercials = null;
//
//        try {
//            commercials = em.createNamedQuery("findEspecializationeAll").setParameter("especialization", especialization).getResultList();
//        } catch (Exception e) {
//             logger.info("Error query findEspecialization");
//        }
//        return commercials;
//    }

}
