package ets.pfe.aqs.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Zeldorine
 */
public class EmailUtil {

    private static final String SMTP_HOST_PROPERTY = "mail.smtp.host";
    private static final String SMTP_PORT_PROPERTY = "mail.smtp.port";
    private static String contentCreateUser = "<pre>Un nouveau compte a ete creer, voici vos identifiant :\n"
            + " identifiant  : [IDENTIFIANT] \n"
            + " mot de passe : [MOTDEPASSE] \n\n"
            + ""
            + "Merci d'avoir choisi PFE AQS.\n\n"
            + "Ceci est un message genere automatiquement, veuillez ne pas repondre s'il vous plait.</pre>";

    public static void sendEmailCreateAccount(String to, final ConfigUtil config, String identifiant, String motDePasse) throws MessagingException, IOException {
        String content = StringUtils.replace(contentCreateUser, "[IDENTIFIANT]", identifiant);
        content = StringUtils.replace(content, "[MOTDEPASSE]", motDePasse);
        sendEmail(to, config, content);
    }

    public static void sendEmail(String to, final ConfigUtil config, final String content)
            throws MessagingException, IOException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put(SMTP_HOST_PROPERTY, config.getSmtpHost());
        props.put(SMTP_PORT_PROPERTY, config.getSmtpPort());

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getPfeAqsEmailUsername(), config.getPfeAqsEmailPassword());
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setSubject("PFE AQS / Creation d'un nouveau compte");
        message.setFrom(new InternetAddress(config.getFromEmail()));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setContent(content, "text/html; charset=utf-8");

        Transport.send(message);
    }
}
