/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Configuracion de la aplicacion 
 * @author Enaitz Izagirre
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    /**
     * Toma el set de clases
     * @return devuelve
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * No modifique el método addRestResourceClasses(). 
     * Se rellena automáticamente con todos los recursos definidos en el proyecto. 
     * Si es necesario, comente llamar a este método en getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(restful.ClientFacadeREST.class);
        resources.add(restful.CommercialFacadeREST.class);
        resources.add(restful.EquipmentFacadeREST.class);
        resources.add(restful.EventEquipmentFacadeREST.class);
        resources.add(restful.EventFacadeREST.class);
        resources.add(restful.UserFacadeREST.class);
    }

}
