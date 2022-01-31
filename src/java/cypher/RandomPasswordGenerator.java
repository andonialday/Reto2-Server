/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.security.SecureRandom;
import java.util.logging.Logger;

/**
 * Clase para generar nuevas contraseñas tras peticion de usuario
 * @author Jaime San Sebastián, Enaitz Izagirre y Andoni Alday
 */
public class RandomPasswordGenerator {

    private static final Logger LOGGER = Logger.getLogger("package.class");

    /**
     * Metodo para generar contrasenia aleatoria segura
     * @return contrasenia aleatoria segura
     */
    public static String generateRandomKey() {
        LOGGER.info("Generating new password");
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        boolean aceptable = false;

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance
        // until it is a valid password (contains a upper letter, a lower case, 
        // a number and a special symbol [manually introduced a * ])
        do {
            sb.append("*");
            for (int i = 0; i < 8; i++) {
                int randomIndex = random.nextInt(chars.length());
                sb.append(chars.charAt(randomIndex));
            }
            LOGGER.info("Checking new password is safe");
            aceptable = validatePassword(sb.toString());
            if (!aceptable) {
                sb.delete(0, 9);
                LOGGER.info("Non-safe password");
            }
        } while (!aceptable);
        return sb.toString();
    }

    private static boolean validatePassword(String key) {
        boolean upper = false;
        boolean lower = false;
        boolean num = false;
        boolean aceptable = false;
        for (int i = 0; i < key.length(); i++) {
            //comprobacion de que contiene un caracter numerico
            if (Character.isDigit(key.charAt(i))) {
                num = true;
            }
            //comprobacion de que contiene una mayuscula
            if (Character.isUpperCase(key.charAt(i))) {
                upper = true;
            }
            //comprobacion de que contiene una minuscula
            if (Character.isLowerCase(key.charAt(i))) {
                lower = true;
            }
        }
        if (num && upper && lower) {
            aceptable = true;
            LOGGER.info("New password is safe");
        }
        return aceptable;
    }

}
