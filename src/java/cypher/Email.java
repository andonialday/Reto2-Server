/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cypher;

import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
 *
 * @author Jaime San Sebastián, Enaitz Izagirre y Andoni Alday
 */
public class Email {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final ResourceBundle configFile = ResourceBundle.getBundle("cypher.config");
    private static final ResourceBundle mailConfig = ResourceBundle.getBundle("cypher.config");
    
    // Credenciales y datos de servicio
    private static final String smtp = configFile.getString("HOST");
    private static final String port = configFile.getString("PORT");
    private static final String user = configFile.getString("USER");
    private static final String password = configFile.getString("PASS");
    
    // Contenido de los mensajes
    private static final String subjectReset = mailConfig.getString("SUBJECTRESET");
    private static final String subjectChange = mailConfig.getString("SUBJECTCHANGE");
    private static final String text1 = mailConfig.getString("BODY1");
    private static final String text2 = mailConfig.getString("BODY1");    
    private static final String cambioPassword = configFile.getString("CAMBIOPASSWORD");

    public static void sendPasswordReset(String receiver, String key) {

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
        props.put("mail.smtp.user", userF);
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, passF);
            }
        });
        try {
            LOGGER.info("Preparando mensaje");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
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
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendPasswordChange(String receiver) {

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
        props.put("mail.smtp.user", userF);
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, passF);
            }
        });
        try {
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
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}
