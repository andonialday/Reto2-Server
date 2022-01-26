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

    /**
     * Constructor del Restful en la base al servicio generico AbstractFacade
     * para la entidad Equipment
     */
    public EquipmentFacadeREST() {       
        super(Equipment.class);
    }

    /**
     * Metodo para recibir peticiones POST - create (Crear) para añadir datos
     * de la entidad Equipment a la base de datos
     * 
     * @param entity contiene los datos de la nueva entrada para la BBDD
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Equipment entity) {
        LOGGER.info("Iniciado metodo para alta de Equipamiento" );
        super.create(entity);
    }

    /**
     * Metodo para actualizar los datos de la entidad Equipment en la
     * base de datos
     * 
     * @param id indentificador del equipamiento a actualizar en la base de datos
     * @param entity datos actuazliados a escribirse en la base de datos
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Equipment entity) {
        LOGGER.info("Iniciado metodo para actualizacion de Equipamiento" );
        super.edit(entity);
    }

    /**
     * Metodo para recibir peticiones de DELETE - remove(Borrar) para eliminar 
     * datos del Equipamiento en la base de datos
     * 
     * @param id identificador del equipamiento a borrar en la base de datos
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        LOGGER.info("Iniciado metodo para borrado de Equipamiento" );
        super.remove(super.find(id));
    }

    /**
     * Metodo para obtener un equipamiento mediante peticion GET - Find(obtener)
     * @param id identificador del equipamiento a obtener 
     * @return el equipamiento que se ha buscado en la base de datos, en el 
     * caso de no existir retorna nulo;
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Equipment find(@PathParam("id") Integer id) {
        LOGGER.info("Iniciado metodo para busqueda de Equipamiento" );
        return super.find(id);
    }

    /**
     * Metodo para obtener todos los equipamientos mediante peticion GET - 
     * FindAll (obtenerTodos)
     * @return todos los equipamientos
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findAll() {
        LOGGER.info("Iniciado metodo para listado de todos los Equipamientos" );
        return super.findAll();
    }

    /**
     * Metodo para obtener los equipamientos definido mediante peticion GET - FindRange
     * definidos por dos variables  
     * @param from valor minimo de las variables
     * @param to valor maximo de las variables
     * @return los equipamientos con parametros entre las dos variables definidas
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Equipment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        LOGGER.info("Iniciado metodo para busqueda por rango de Equipamiento" );
        return super.findRange(new int[]{from, to});
    }

    /**
     * Metodo para obtener la cantidad de equipamientos de la base de datos 
     * mediante una peticion GET - countREST
     * @return cantidad de los equipamientos 
     */
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        LOGGER.info("Iniciado metodo para contabilizar la cantidad de Equipamientos" );
        return String.valueOf(super.count());
    }

    /**
     * Metodo para obtener equipamientos entre dos variables mediante 
     * metodo GET - findCostRange 
     * @param min coste definido
     * @param max coste definido
     * @return los equipamientos establecidos por los parametro anteriores
     */
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

    /**
     * Metodo para obtener equipamientos de una variable mediante metodo 
     * GET - findOrderPreviousDate
     * @param datePrev fecha previa de la orden
     * @return equipamientos anteriores a la datePrev(fecha previa) definida
     */
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

    /**
     Metodo para obtener equipamientos de una variable mediante metodo 
     * GET - findOrderAfterDate
     * @param dateAfter fecha posterior a la orden
     * @return equipamientos posteriores a la DateAfter(fecha posterior) definida
     */
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

    /**
     * Metodo para borrar todos los equipamientos anteriores a una fecha definida
     * mediante metodo GET - deleteOldEquip
     * @param year es una fecha definida
     * @return los equipamientos restantes en la base de datos
     * 
     */
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

    /**
     * Metodo para aumentar en un porcentaje definido el coste de todos los 
     * equipamientos en la base de datos
     * @param ratio porcentaje definido
     * @return todos los equipamientos con el coste actualizado
     */
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

    /**
     * Metodo para obtener el Gestor de entidaddes que es el encargado 
     * de convertir todas las peticiones recibidas por el servidor en 
     * consultas SQL
     * 
     * @return acceso al gestor de las entidades
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
