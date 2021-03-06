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
 * @author Andoni Alday, Enaitz Izagirre, Aitor Perez, Jaime San Sebastian
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
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
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
