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
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
public class Hashing {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    /**
     * Aplica SHA al texto pasado por parámetro
     * 
     * @param texto
     * @return 
     */
    public static String cifrarTexto(String texto) {
        MessageDigest messageDigest;
        String hashedText = null;
        try {
            LOGGER.info("Initiating Password security Hashing");
            // Obtén una instancia de MessageDigest que usa MD5 (estaba en SHA)
            messageDigest = MessageDigest.getInstance("MD5");
            // Convierte el texto en un array de bytes 
            byte[] bytes = texto.getBytes();
            // Actualiza el MessageDigest con el array de bytes 
            messageDigest.update(texto.getBytes());
            // Calcula el resumen (función digest)
            byte[] pass = messageDigest.digest();
            hashedText = new String(pass);
            LOGGER.info("Password succesfully Hashed");
        } catch (NoSuchAlgorithmException e) {
        }
        return hashedText;
    }

}
