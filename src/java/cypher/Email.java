/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;

/**
 * Clase para mandar emails de informacion en los procesos que afectan a la 
 * contraseña del usuario
 * @author Jaime San Sebastián, Enaitz Izagirre, Andoni Alday y Aitor Perez
 */
public class Email {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cypher.config");
    private static final ResourceBundle mailConfig = ResourceBundle.getBundle("cypher.mail");

    // Credenciales y datos de servicio
    private static final String smtp = configFile.getString("HOST");
    private static final String port = configFile.getString("PORT");
    private static final String user = configFile.getString("USER");
    private static final String password = configFile.getString("PASS");

    // Contenido de los mensajes
    private static final String subjectReset = mailConfig.getString("SUBJECTRESET");
    private static final String subjectChange = mailConfig.getString("SUBJECTCHANGE");
    private static final String text1 = mailConfig.getString("BODY1");
    private static final String text2 = mailConfig.getString("BODY2");
    private static final String cambioPassword = mailConfig.getString("CAMBIOPASSWORD");

    /**
     * Metodo para mandar la contraseña que se ha generado aleatoria que el servidor
     * considera segura tras peticion del usuario
     * @param receiver el email del reptor del mensaje
     * @param key clave nueva segura generada aleatoriamente
     * @throws MessagingException en el caso de que se genere un error al mandar un correo
     */
    public static void sendPasswordReset(String receiver, String key) throws MessagingException {

        DecryptSim decrypt = new DecryptSim();

        //Pasar de hexadecimal a byte
        byte[] usr = DatatypeConverter.parseHexBinary(user);
        byte[] pass = DatatypeConverter.parseHexBinary(password);
        //desencriptar
        byte[] usr2 = decrypt.decryptSim(usr);
        byte[] pass2 = decrypt.decryptSim(pass);
        //convertir byte to String para usarla 
        String userF = new String(usr2);
        String passF = new String(pass2);

        LOGGER.info("Preparando conexión a servicio de correo");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", true);
        props.put("mail.imap.partialfetch", false);
        props.put("mail.smtp.ssl.enable", false);

        
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userF, passF);
            }
        });
        LOGGER.info("Preparando mensaje");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(userF));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subjectReset);
        LOGGER.info("Cabecera de mensaje lista");
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text1, "text/html");
        multipart.addBodyPart(mimeBodyPart);
        MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
        mimeBodyPart2.setContent(key, "text/html");
        multipart.addBodyPart(mimeBodyPart2);
        MimeBodyPart mimeBodyPart3 = new MimeBodyPart();
        mimeBodyPart3.setContent(text2, "text/html");
        multipart.addBodyPart(mimeBodyPart3);
        message.setContent(multipart);
        LOGGER.info("Cuerpo de mensaje listo");
        Transport.send(message);
        LOGGER.info("Mensaje enviandose");
    }

    /**
     * Metodo que envia un email confirmando que se ha cambiado la contraseña
     * @param receiver correo del reptor
     * @throws MessagingException en el caso de que se genere un error al mandar un correo
     */
    public static void sendPasswordChange(String receiver) throws MessagingException {

        DecryptSim decrypt = new DecryptSim();

        //Pasar de hexadecimal a byte
        byte[] usr = DatatypeConverter.parseHexBinary(user);
        byte[] pass = DatatypeConverter.parseHexBinary(password);
        //desencriptar
        byte[] usr2 = decrypt.decryptSim(usr);
        byte[] pass2 = decrypt.decryptSim(pass);
        //convertir byte to String para usarla 
        String userF = new String(usr2);
        String passF = new String(pass2);

        LOGGER.info("Preparando conexión a servicio de correo");
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.trust", true);
        props.put("mail.imap.partialfetch", false);
        props.put("mail.smtp.ssl.enable", false);

       
        
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userF, passF);
            }
        });
        LOGGER.info("Preparando mensaje");
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subjectChange);
        LOGGER.info("Cabecera de mensaje lista");
        Multipart multipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(cambioPassword, "text/html");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        LOGGER.info("Cuerpo de mensaje listo");
        Transport.send(message);
        LOGGER.info("Mensaje enviandose");
    }
}
