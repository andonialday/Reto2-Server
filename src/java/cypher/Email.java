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

/**
 *
 * @author 2dam
 */
public class Email {

    private static final ResourceBundle configFile = ResourceBundle.getBundle("cypher.config");
    private static final String smtp = configFile.getString("HOST");
    private static final String port = configFile.getString("PORT");
    private static final String user = configFile.getString("USER");
    private static final String pass = configFile.getString("PASS");
    private static final Logger LOGGER = Logger.getLogger("package.class");
    private static final String subject = "Recuperación de contraseña solicitada";
    private static final String text = "Este es un mensaje deprueba";

    public static void sendPasswordReset(String receiver, String key) {

        LOGGER.info("Preparando conexión a servicio de correo");
        Properties props = new Properties();
        props.put("mail.smtp.user", user);
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
                return new PasswordAuthentication(user, pass);
            }
        });
        try {
            LOGGER.info("Preparando mensaje");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject(subject);
            LOGGER.info("Cabecera de mensaje lista");
            Multipart multipart = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(key, "text/html");
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
