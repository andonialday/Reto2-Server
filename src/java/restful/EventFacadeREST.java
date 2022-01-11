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
 *
 * @author 2dam
 */
@Stateless
@Path("entities.event")
public class EventFacadeREST extends AbstractFacade<Evento> {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    public EventFacadeREST() {
        super(Evento.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Evento entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Evento entity) {
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
    public Evento find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("dateStart/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findStartRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        List<Evento> events = null;
        try {
            events = em.createNamedQuery("findDateStartRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Event -> findStartRange" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("dateEnd/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findEndRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateEndRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findEndRange" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("date/{dateMin}/{dateMax}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findDateRange(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax) {
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateRange").setParameter("date1", dateMin).setParameter("date2", dateMax).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findDateRange" + e.getMessage());
        }
        return events;
    }

    @GET
    @Path("dateStartClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findStartRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        List<Evento> events = null;
        try {
            events = em.createNamedQuery("findDateStartRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Event -> findStartRange" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("dateEndClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findEndRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateEndRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findEndRange" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("dateClient/{dateMin}/{dateMax}/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findDateRangeClient(@PathParam("dateMin") Date dateMin, @PathParam("dateMax") Date dateMax, @PathParam("idCli") Integer idCli) {
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findDateRangeClient").setParameter("date1", dateMin).setParameter("date2", dateMax).setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findDateRange" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("byClient/{idCli}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> findEventByClient(@PathParam("idCli") Integer idCli) {
        List<Evento> events = null;
        try {
            events = (em.createNamedQuery("findEventByClient").setParameter("idCli", idCli).getResultList());
        } catch (Exception e) {
            LOGGER.severe("Event -> findAssignedEquipment" + e.getMessage());
        }
        return events;
    }
     
    @GET
    @Path("oldest/{year}")
    @Produces({MediaType.APPLICATION_XML})
     public List<Evento> deleteOldestEvents(@PathParam("year") Integer year) {
        List<Evento> events = null;
        LocalDate time = LocalDate.now();
        Date date = Date.from(time.atStartOfDay().toInstant(ZoneOffset.UTC));
        date.setYear(date.getYear()-year);
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
