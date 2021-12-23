/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import static cypher.Decrypt.decrypt;
import static cypher.Encrypt.encrypt;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaime San Sebastián y Enaitz Izagirre
 */
public class Main {
    
    public void generate() {
        final String algorithm = "RSA";
        KeyPairGenerator keyPairGenerator;
        try {
            //Crear una instancia de KeyPairGenerator
            keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            //Inicialización del KeyPairGenerator
            keyPairGenerator.initialize(2048);
            //Generando un par de claves
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //Crea el archivo para guardar la clave pública
            File filePublic = new File("./src/files/public.key");
            //Escribir la clave pública en un archivo externo
            fileWriter(filePublic.getPath(), keyPair.getPublic().getEncoded());
            //Crea el archivo para guardar la clave privada
            File filePrivate = new File("./src/files/private.key");
            //Escribir la clave privada en un archivo externo
            fileWriter(filePrivate.getPath(), keyPair.getPrivate().getEncoded());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(javax.crypto.KeyGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            Logger.getLogger(javax.crypto.KeyGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void encriptar() {
        // Generamos el par de claves y los guardamos en archivos.
        new KeyGenerator().generate();
        System.out.println("Introduce el mensaje a cifrar");
        // Leemos un mensaje escrito por por el usuario
        byte[] mensaje = new Scanner(System.in).nextLine().trim().getBytes();
        // Recojemos el mensaje encriptado
        byte[] encrypt = encrypt(mensaje);
        // Lo sacamos por pantalla para saber que lo ha hecho bien
        System.out.println(new String(encrypt));
        // Desencriptamos el mensaje
        byte[] decrypt = decrypt(encrypt);
        // Sacamos el mensaje desencriptado por pantalla
        System.out.println(new String(decrypt));
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