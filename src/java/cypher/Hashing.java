/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * Clase dedicada a hashear las contraseñas de usuario para su almacenamiento
 * en la base de datos
 * @author Jaime San Sebastián, Enaitz Izagirre y Aitor Perez
 */
public class Hashing {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    /**
     * Aplica hasheo mediante SHA al texto pasado por parámetro
     * 
     * @param texto texto a hashear
     * @return contraseña haseada
     */
    public static byte[] cifrarTexto(String texto) {
        MessageDigest messageDigest;
        byte[] pass = null;
        try {
            LOGGER.info("Initiating Password security Hashing");
            // Obtén una instancia de MessageDigest que usa MD5 (estaba en SHA)
            messageDigest = MessageDigest.getInstance("MD5");
            // Convierte el texto en un array de bytes 
            byte[] bytes = texto.getBytes();
            // Actualiza el MessageDigest con el array de bytes 
            messageDigest.update(texto.getBytes());
            // Calcula el resumen (función digest)
            pass = messageDigest.digest();
            LOGGER.info("Password succesfully Hashed");
        } catch (NoSuchAlgorithmException e) {
        }
        return pass;
    }

}
