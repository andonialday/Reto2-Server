/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Equipment;
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
 * @author Aitor Perez
 */
@Stateless
@Path("entities.equipment")
public class EquipmentFacadeREST extends AbstractFacade<Equipment> {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    @PersistenceContext(unitName = "Reto2G1cServerPU")
    private EntityManager em;

    public EquipmentFacadeREST() {
        super(Equipment.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Equipment entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Equipment entity) {
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
    public Equipment find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("cost/{min}/{max}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findCostRange(@PathParam("min") Double min, @PathParam("max") Double max) {
        List<Equipment> equipments = null;
        try {
            equipments = (em.createNamedQuery("findCostRange")
                    .setParameter("cost1", min).setParameter("cost2", max).getResultList());

        } catch (Exception e) {
            LOGGER.severe("Cost -> findCostRange" + e.getMessage());
        }
        return equipments;
    }

    @GET
    @Path("datePrev/{datePrev}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findOrderPreviousDate(@PathParam("datePrev") Date datePrev) {
        List<Equipment> equipments = null;
        try {
            equipments = (em.createNamedQuery("findOrderPreviousDate")
                    .setParameter("date1", datePrev).getResultList());

        } catch (Exception e) {
            LOGGER.severe("DatePrev -> findOrderPreviousDate" + e.getMessage());
        }
        return equipments;
    }

    @GET
    @Path("dateAfter/{dateAfter}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findOrderAfterDate(@PathParam("dateAfter") Date dateAfter) {
        List<Equipment> equipments = null;
        try {
            equipments = (em.createNamedQuery("findOrderAfterDate")
                    .setParameter("date1", dateAfter).getResultList());

        } catch (Exception e) {
            LOGGER.severe("DateAfter -> findOrderAfterDate" + e.getMessage());
        }
        return equipments;
    }

    @GET
    @Path("oldEquip/{year}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> deleteOldEquip(@PathParam("year") Date year) {
        List<Equipment> equipments = null;
        try {
            em.createNamedQuery("deleteOldEquip")
                    .setParameter("date1", year).executeUpdate();
            em.flush();
            equipments = findAll();

        } catch (Exception e) {
            LOGGER.severe("OldEquip -> deleteOldEquip" + e.getMessage());
        }
        return equipments;
    }

    @GET
    @Path("updateCost/{percent}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> updateCost(@PathParam("percent") Double ratio) {
        List<Equipment> equipments = null;
        try {
            ratio = 1 + ratio / 100;
            em.createNamedQuery("updateCostIPC")
                    .setParameter("ratio", ratio).executeUpdate();
            em.flush();
            equipments = findAll();
            for (Equipment equipment : equipments) {
                Long cost = Math.round((equipment.getCost()*100));
                Double costt = (double)cost/100;
                equipment.setCost(costt);
            }
        } catch (Exception e) {
            LOGGER.severe("ratio -> updateCostIPC" + e.getMessage());
        }
        return equipments;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
