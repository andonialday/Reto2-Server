/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para desencriptar el usuario o la contrasela del correo electronico
 * @author Jaime San Sebastián, Enaitz Izagirre, Andoni Alday y Aitor Perez
 */
public class DecryptSim {

    private static final String AES = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";
    private static byte[] salt = "esta es la salt!".getBytes();
    private static String key = "rental2g1c";

    /**
     * Metodo para desencriptar usuario o contraseña del correo 
     * electronico que usa el servidor cifrado de forma simetrica
     * @param data usuario o contraseña cifrados
     * @return usuario o contraseña desfridados
     */
    public byte[] decryptSim(byte[] data) {
    byte[] decodedMessage = null;
        // Fichero leíd
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {
            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(key.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(data, 0, 16)); // La IV est� aqu�
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            decodedMessage = cipher.doFinal(Arrays.copyOfRange(data, 16, data.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decodedMessage;    
    }
    
}
