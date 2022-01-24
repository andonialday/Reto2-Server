/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Jaime San Sebastián, Enaitz Izagirre y Andoni Alday
 */
public class DecryptASim {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cypher.config");
    private static String RSA = "RSA/ECB/PKCS1Padding";

    private static PrivateKey priv() {
        PrivateKey privKey = null;
        String pathPrivate = configFile.getString("PRIVATEKEYPATH");
        try {            
            String privK = new String(Files.readAllBytes(Paths.get(DecryptASim.class.getResource(pathPrivate).toURI())));
            PKCS8EncodedKeySpec pk = new PKCS8EncodedKeySpec(DatatypeConverter.parseHexBinary(privK));
            privKey = KeyFactory.getInstance("RSA").generatePrivate(pk);
            LOGGER.info("Clave privada lista para descifrar");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptASim.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(DecryptASim.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DecryptASim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return privKey;
    }

    public static byte[] decrypt(byte[] cipherText) {
        byte[] result = null;
        try {
            PrivateKey privKey = priv();
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privKey);
            LOGGER.info("Desencriptando");
            result = cipher.doFinal(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(DecryptASim.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
