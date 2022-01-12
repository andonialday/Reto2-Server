/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author 2dam
 */
public class MainCifradoSim {

    private static byte[] salt = "esta es la salt!".getBytes();
    private static String key = "rental2g1c";

    public static void main(String[] args) {
        MainCifradoSim cif = new MainCifradoSim();
        byte[] cifrado = cif.cifrarTexto(key, "Abcd*1234");
        String mensajeCifrado = DatatypeConverter.printHexBinary(cifrado);
        System.out.println(mensajeCifrado);
        byte[] descifrado = cif.decryptSim(cifrado);
        String data = new String(descifrado);
        System.out.println(data);
    }

    public byte[] cifrarTexto(String clave, String mensaje) {
        byte[] combined = null;
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            // Creamos un SecretKey usando la clave + salt
            keySpec = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); // AES-128
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");

            // Creamos un Cipher con el algoritmos que vamos a usar
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicializaci�n por modo CBC

            // Guardamos el mensaje codificado: IV (16 bytes) + Mensaje
            combined = concatArrays(iv, encodedMessage);

            fileWriter("c:\\claves\\EjemploAES.dat", combined);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return combined;
    }

    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
