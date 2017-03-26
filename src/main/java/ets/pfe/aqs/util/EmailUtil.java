package ets.pfe.aqs.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public abstract class EmailUtil {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EmailUtil.class);
    private static final String SMTP_HOST_PROPERTY = "mail.smtp.host";
    private static final String SMTP_PORT_PROPERTY = "mail.smtp.port";
    private static String contentCreateUser = "<pre>Un nouveau compte a ete creer, voici vos identifiant :\n"
            + " identifiant  : [IDENTIFIANT] \n"
            + " mot de passe : [MOTDEPASSE] \n\n"
            + ""
            + "Merci d'avoir choisi PFE AQS.\n\n"
            + "Ceci est un message genere automatiquement, veuillez ne pas repondre s'il vous plait.</pre>";

    private EmailUtil() {
    }

    /**
     * 
     * @param to
     * @param config
     * @param identifiant
     * @param motDePasse
     * @throws MessagingException 
     */
    public static void sendEmailCreateAccount(String to, final ConfigUtil config, String identifiant, String motDePasse) throws MessagingException {
        try {
            String content = StringUtils.replace(contentCreateUser, "[IDENTIFIANT]", identifiant);
            content = StringUtils.replace(content, "[MOTDEPASSE]", motDePasse);
            sendEmail(to, config, content);
        } catch (MessagingException ex) {
            LOGGER.error("An error occured during sending mail.", ex);
            throw ex;
        }
    }

    /**
     * 
     * @param to
     * @param config
     * @param content
     * @throws MessagingException 
     */
    public static void sendEmail(String to, final ConfigUtil config, final String content) throws MessagingException {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put(SMTP_HOST_PROPERTY, config.getSmtpHost());
            props.put(SMTP_PORT_PROPERTY, config.getSmtpPort());

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                @Override
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
        } catch (MessagingException ex) {
            LOGGER.error("An error occured during sending mail.", ex);
            throw ex;
        }
    }
}
