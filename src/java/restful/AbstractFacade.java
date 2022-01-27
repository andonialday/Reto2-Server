/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Servicio RestFul para genéricos para implementar por el resto de entidades
 *
 * @author Andoni Alday, Enaitz Izagirre, Aitor Pérez, Jaime SanSebastian
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Constructor del servicio
     *
     * @param entityClass clase de la entidad para la que se van a usar los
     * métodos genéricos
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Método para obtener el Gestor de Entidades encargado de convertir las
     * múltiples peticiones recibidas por el servidor en consultas SQL
     *
     * @return acceso al Gestor de Entidades
     */
    protected abstract EntityManager getEntityManager();

    /**
     *
     * @param entity
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Método para actualizar datos pertenecientes a la entidad en la BBDD
     * mediante PUT - Edit
     *
     * @param entity datos actualizados a escribirse en la BBDD
     */
    public void edit(T entity) {
        if (!getEntityManager().contains(entity)) {
            getEntityManager().merge(entity);
        }
        getEntityManager().flush();

    }

    /**
     * Método para recibir peticiones de DELETE - Remove para eliminar datos
     * pertenecientes a la entidad de la BBDD
     *
     * @param entity entrada a eliminar de la BBDD
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
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
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Método para recibir peticiones GET - FindAll para obtener todas las
     * entradas de la entidad en la BBDD
     *
     * @return todos los datos de la entradas en la BBDD
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Método para obtener las peticiones GET - FindRange para obtener las
     * entradas pertenecientes a la entidad con identificadores entre dos
     * otorgados
     *
     * @param range rango en el que tienen que estar los identificadores de las
     * entidades
     * @return las entradas con identificadores entre los valores otorgados
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * Método para obtener las peticiones GET - Count para obtener la cantidad
     * de entradas asociadas a la entidad existentes en la BBDD
     *
     * @return cantidad de entradas de la entidad en la BBDD
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
