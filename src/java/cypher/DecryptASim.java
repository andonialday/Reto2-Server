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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
public class DecryptASim {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static PublicKey pubKey;
    private static final String pathPublic = "cypher.public.key";

    public static byte[] decrypt(byte[] key) {
        byte[] pass = null;
        
        return pass;
    }
    
    public static void pub() {
        try {
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(pathPublic));
            String pubK = reader.readLine();               
            X509EncodedKeySpec x5 = new X509EncodedKeySpec(DatatypeConverter.parseHexBinary(pubK));
            /*
            byte[] publica = Files.readAllBytes(Paths.get(pathPublic));
            X509EncodedKeySpec x5 = new X509EncodedKeySpec(publica);
            */
            pubKey = KeyFactory.getInstance("RSA/ECB/PKCS1Padding").generatePublic(x5);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }      
        
    }
    
}
