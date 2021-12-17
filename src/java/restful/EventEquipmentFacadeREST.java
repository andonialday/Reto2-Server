/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.EventEquipment;
import entities.EventEquipmentId;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entities.eventequipment")
public class EventEquipmentFacadeREST extends AbstractFacade<EventEquipment> {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    private EventEquipmentId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;eventId=eventIdValue;equipmentId=equipmentIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.EventEquipmentId key = new entities.EventEquipmentId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> eventId = map.get("eventId");

        if (eventId != null && !eventId.isEmpty()) {
            key.setEventId(new java.lang.Integer(eventId.get(0)));
        }
        java.util.List<String> equipmentId = map.get("equipmentId");
        if (equipmentId != null && !equipmentId.isEmpty()) {
            key.setEquipmentId(new java.lang.Integer(equipmentId.get(0)));
        }

        return key;
    }

    public EventEquipmentFacadeREST() {
        super(EventEquipment.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(EventEquipment entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, EventEquipment entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.EventEquipmentId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public EventEquipment find(@PathParam("id") PathSegment id) {
        entities.EventEquipmentId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("equipment/{idEvent}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAssignedEquipment(@PathParam("idEvent") Integer idEvent) {
        List<EventEquipment> equipments = null;
        try {
            equipments = (em.createNamedQuery("findAssignedEquipment").setParameter("idEvent", idEvent).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findAssignedEquipment" + e.getMessage());
        }
        return equipments;
    }

    @GET
    @Path("event/{idEquipment}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAssignedEvent(@PathParam("idEquipment") Integer idEquipment) {
        List<EventEquipment> events = null;
        try {
            events = (em.createNamedQuery("findAssignedEquipment").setParameter("idEquipment", idEquipment).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Equipment -> findAssignedEquipment" + e.getMessage());
        }
        return events;
    }
}
