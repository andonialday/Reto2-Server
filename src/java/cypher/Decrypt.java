/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
public class Decrypt {
    
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cypher.config");
    private static final String path = configFile.getString("PRIVATEKEYPATH");
    
    public static PrivateKey readPrivateKey(String filename) {
        PKCS8EncodedKeySpec keySpec;
        KeyFactory keyFactory;
        PrivateKey privateKey = null;
        try {
            keySpec = new PKCS8EncodedKeySpec(fileReader(filename));
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privateKey;
    }
    
    /**
     * Este metodo se encarga de descifrar el mensaje mediante clave privada
     * @param ciphertext el mensaje en bytes
     * @return el mensaje desencriptado
     */
    public static byte[] decrypt(byte[] ciphertext) {
        Cipher cipher;
        byte[] bs = null;
        PrivateKey key;
        try {
            // Leemos la clave publica del archivo en el cual lo hemos escrito
            key = readPrivateKey(path);
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
            cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            // Iniciamos el Cipher en DECRYPT_MODE y le pasamos la clave privada
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Le decimos que cifre (método doFinal(mensaje))
            bs = cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bs;
    }
    
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
        return ret;
    }
}
