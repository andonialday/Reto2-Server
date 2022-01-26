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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Andoni Alday y Aitor Pérez
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

    /**
     * Constructor del Restful en base al servicio genérico AbstractFacade para
     * la entidad EventEquipment
     */
    public EventEquipmentFacadeREST() {
        super(EventEquipment.class);
    }

    /**
     * Método para obtener el Gestor de Entidades encargado de convertir las
     * múltiples peticiones recibidas por el servidor en consultas SQL
     *
     * @return acceso al Gestor de Entidades
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Método para recibir peticiones de POST - Create para añadir datos
     * pertenecientes a la entidad a la BBDD
     *
     * @param entity con los datos de la nueva entrada en la BBDD
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(EventEquipment entity) {
        LOGGER.info("Añadiendo un eventequipment a la BBDD");
        super.create(entity);
    }

    /**
     * Método para actualizar datos pertenecientes a la entidad en la BBDD
     * mediante PUT - Edit
     *
     * @param id identificador de la entrada en la BBDD a actualizar
     * @param entity datos actualizados a escribirse en la BBDD
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, EventEquipment entity) {
        LOGGER.info("Editando un eventequipment en la BBDD");
        super.edit(entity);
    }

    /**
     * Método para recibir peticiones de DELETE - Remove para eliminar datos
     * pertenecientes a la entidad de la BBDD
     *
     * @param id identificador de la entrada a eliminar de la BBDD
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        LOGGER.info("Eliminando un eventequipment de la BBDD");
        entities.EventEquipmentId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    /**
     * Método para recibir peticiones GET - Find para buscar entradas
     * pertenecientes a la entidad específicas en la BBDD
     *
     * @param id identificador de la entrada de la que se quieren obtener los
     * datos
     * @return los datos de la entrada que se buscaba en la BBDD, nulos si la
     * entrada no existe
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public EventEquipment find(@PathParam("id") PathSegment id) {
        LOGGER.info("Buscando un eventequipment en la BBDD");
        entities.EventEquipmentId key = getPrimaryKey(id);
        return super.find(key);
    }

    /**
     * Método para recibir peticiones GET - FindAll para obtener todas las
     * entradas de la entidad en la BBDD
     *
     * @return los datos de la entrada que se buscaba en la BBDD, nulos si la
     * entrada no existe
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAll() {
        LOGGER.info("Leyendo todos los eventequipments en la BBDD");
        return super.findAll();
    }

    /**
     * Método para obtener las peticiones GET - FindRange para obtener las
     * entradas pertenecientes a la entidad con identificadores entre dos
     * otorgados
     *
     * @param from valor <i>mínimo</i> del identificador de las entradas
     * @param to valor <i>máximo</i> del identificador de las entradas
     * @return las entradas con identificadores entre los valores otorgados
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        LOGGER.info("Iniciando búsqueda de eventequipments en un rango en la BBDD");
        return super.findRange(new int[]{from, to});
    }

    /**
     * Método para obtener las peticiones GET - CountRest para obtener la
     * cantidad de entradas asociadas a la entidad existentes en la BBDD
     *
     * @return cantidad de entradas de la entidad en la BBDD
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        LOGGER.info("Iniciando cuenta de relaciones entre eventos y equipamientos en la BBDD");
        return String.valueOf(super.count());
    }

    /**
     * Método para obtener las peticiones GET - FindAssignedEquipment para
     * obtener los equipamientos alquilados para un evento
     *
     * @param idEvent identificador del evento del que se quieren obtener los
     * equipamientos alquilados
     * @return los equipamientos alquilados para el evento
     */
    @GET
    @Path("equipment/{idEvent}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAssignedEquipment(@PathParam("idEvent") Integer idEvent) {
        LOGGER.info("Iniciando búsqueda de equipamientos asignados a un evento en la BBDD");
        List<EventEquipment> equipments = null;
        try {
            equipments = (em.createNamedQuery("findAssignedEquipment").setParameter("idEvent", idEvent).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findAssignedEquipment" + e.getMessage());
        }
        return equipments;
    }

    /**
     * Método para obtener las peticiones GET - FindAssignedEvent para obtener
     * los eventos para los que se ha alquilado el equipamiento
     *
     * @param idEquipment identificador del equipamiento del que se quieren
     * obtener los eventos asociados
     * @return los eventos asociados al equipamiento
     */
    @GET
    @Path("event/{idEquipment}")
    @Produces({MediaType.APPLICATION_XML})
    public List<EventEquipment> findAssignedEvent(@PathParam("idEquipment") Integer idEquipment) {
        LOGGER.info("Iniciando búsqueda de eventos asignados a un equipamiento en la BBDD");
        List<EventEquipment> events = null;
        try {
            events = (em.createNamedQuery("findAssignedEvent").setParameter("idEquipment", idEquipment).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Equipment -> findAssignedEquipment" + e.getMessage());
        }
        return events;
    }
}
