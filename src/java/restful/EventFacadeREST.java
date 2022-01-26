/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Evento;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
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

/**
 * Servicio Restful para la entidad Eventr
 *
 * @author Andoni Alday
 */
@Stateless
@Path("entities.event")
public class EventFacadeREST extends AbstractFacade<Evento> {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    /**
     * Constructor del Restful en base al servicio genérico AbstractFacade para
     * la entidad Event
     */
    public EventFacadeREST() {
        super(Evento.class);
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
    public void create(Evento entity) {
        LOGGER.info("Añadiendo un evento a la BBDD");
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
    public void edit(@PathParam("id") Integer id, Evento entity) {
        LOGGER.info("Editando un evento en la BBDD");
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
    public void remove(@PathParam("id") Integer id) {
        LOGGER.info("Eliminando un evento de la BBDD");
        super.remove(super.find(id));
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
    public Evento find(@PathParam("id") Integer id) {
        LOGGER.info("Buscando un eventequipment en la BBDD");
        return super.find(id);
    }

    /**
     * Método para recibir peticiones GET - FindAll para obtener todas las
     * entradas de la entidad en la BBDD
     *
     * @return todos los datos de la entradas en la BBDD
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findAll() {
        LOGGER.info("Leyendo todos los eventos en la BBDD");
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
    public List<Evento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        LOGGER.info("Iniciando búsqueda de eventos en un rango en la BBDD");
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
        LOGGER.info("Iniciando cuenta de eventos en la BBDD");
        return String.valueOf(super.count());
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos con fecha de inicio entre dos fechas determinadas
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @return los eventos que comienzan en el plazo
     */
    @GET
    @Path("dateStart/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findStartRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        LOGGER.info("Iniciando búsqueda de eventos con fecha de inicio en un rango en la BBDD");
        List<Evento> events = null;
        try {
            events = em.createNamedQuery("findDateStartRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Event -> findStartRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos con fecha de finalizacion entre dos fechas determinadas
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @return los eventos que finalizan en el plazo
     */
    @GET
    @Path("dateEnd/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findEndRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        LOGGER.info("Iniciando búsqueda de eventos con fecha de finalizacion en un rango en la BBDD");
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateEndRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findEndRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos sucedidos entre dos fechas determinadas
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @return los eventos sucedidos en el plazo
     */
    @GET
    @Path("date/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findDateRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        LOGGER.info("Iniciando búsqueda de eventos que suceden en un rango en la BBDD");
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findDateRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos con fecha de inicio entre dos fechas determinadas pertenecientes
     * a un cliente
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @param idCli identificador del cliente del que se quieren obtener eventos
     * @return los eventos que comienzan en el plazo pertenecientes al cliente
     */
    @GET
    @Path("dateStartClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findStartRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        LOGGER.info("Iniciando búsqueda de eventos con fecha de inicio en un rango pertenecientes a un cliente en la BBDD");
        List<Evento> events = null;
        try {
            events = em.createNamedQuery("findDateStartRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Event -> findStartRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos con fecha de finalizacion entre dos fechas determinadas
     * pertenecientes a un cliente
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @param idCli identificador del cliente del que se quieren obtener eventos
     * @return los eventos que finalizan en el plazo pertenecientes al cliente
     */
    @GET
    @Path("dateEndClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findEndRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        LOGGER.info("Iniciando búsqueda de eventos con fecha de finalizacion en un rango pertenecientes a un cliente en la BBDD");
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateEndRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findEndRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindStartRange para obtener los
     * eventos que sucenden entre dos fechas determinadas pertenecientes a un
     * cliente
     *
     * @param dateMin dia <i>minimo</i> para el plazo en el que deben suceder
     * los eventos
     * @param dateMax dia <i>maximo</i> para el plazo en el que deben suceder
     * los eventos
     * @param idCli identificador del cliente del que se quieren obtener eventos
     * @return los eventos sucedidos en el plazo pertenecientes a un cliente
     */
    @GET
    @Path("dateClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findDateRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        LOGGER.info("Iniciando búsqueda de eventos que suceden en un rango pertenecientes a un cliente en la BBDD");
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findDateRange" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - FindEventByClient para obtener
     * los eventos pertenecientes a un cliente
     *
     * @param idCli identificador del cliente del que se quieren obtener eventos
     * @return los eventos pertenecientes al cliente
     */
    @GET
    @Path("byClient/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findEventByClient(@PathParam("idCli") Integer idCli) {
        LOGGER.info("Iniciando búsqueda de eventos pertenecientes a un cliente en la BBDD");
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findEventByClient").setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findAssignedEquipment" + e.getMessage());
        }
        return events;
    }

    /**
     * Método para obtener las peticiones GET - DeleteOldestEvents para obtener
     * los eventos para los que han sucedido hace menos de una cantidad
     * determinada de anios y eliminar los anteriores
     *
     * @param year anios
     * @return los eventos sucedidos en un plazo más reciente
     */
    @GET
    @Path("oldest/{year}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> deleteOldestEvents(@PathParam("year") Integer year) {
        LOGGER.info("Eliminando eventos sucedidos hace más de " + year + " anios");
        List<Evento> events = null;
        LocalDate time = LocalDate.now();
        Date date = Date.from(time.atStartOfDay().toInstant(ZoneOffset.UTC));
        date.setYear(date.getYear() - year);
        try {
            em.createNamedQuery("deleteOldestEvents").setParameter("date", date).executeUpdate();
            em.flush();
            events = findAll();
        } catch (Exception e) {
            LOGGER.severe("Event -> deledteOldestEvents" + e.getMessage());
        }
        return events;
    }
}
