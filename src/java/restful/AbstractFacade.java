/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Desarrollo de los metodos del restfull
 *
 * @author Enaitz Izagirre
 */
public abstract class AbstractFacade<T> {

    /**
     * Entidad de la clase
     */
    private final Class<T> entityClass;

    /**
     * Constructor de la clase
     *
     * @param entityClass clase abstracta
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Generar el entity manager para gestionar la entidad
     *
     * @return devuelve una entidad
     */
    protected abstract EntityManager getEntityManager();

    /**
     * Crea una nueva entidad
     *
     * @param entity entidad de la clase
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Edita una entidad
     * @param entity entidad de la clase
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Elimina una entidad
     * @param entity entidad de la clase
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Busca una entidad
     * @param id Parametro del objeto de la entidad
     * @return devuelve la entidad que encontro
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Busca todas las entidades en la lista
     * @return Todas las entidades de la lista
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Busca en la lista dentro del rango
     * @param range rango introducirdo con minimo y maximo
     * @return devuelve la lista resultante
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
     * Cuenta de la la lista 
     * @return devuelve un numero con la cuenta
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
