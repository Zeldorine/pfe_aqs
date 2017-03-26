package UtilTest;

import ets.pfe.aqs.util.ConfigUtil;
import ets.pfe.aqs.util.EmailUtil;
import javax.mail.MessagingException;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Zeldorine
 */
public class EmailUtilTest {

    private String to = "cazorlatony@gmail.com";

    @Test
    public void testEmail() {
        ApplicationContext sprinContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-context.xml");
        ConfigUtil config = (ConfigUtil) sprinContext.getBean("configUtil");

        try {
            EmailUtil.sendEmailCreateAccount(to, config, "username", "123pass");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            fail();
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testEmailError() {
        ApplicationContext sprinContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-context.xml");
        ConfigUtil config = (ConfigUtil) sprinContext.getBean("configUtil");

        try {
            EmailUtil.sendEmailCreateAccount(null, config, "username", "123pass");
        } catch (MessagingException ex) {
        } catch (Exception ex) {
        }
    }

    @Test
    public void testEmailError2() {
        ApplicationContext sprinContext = new ClassPathXmlApplicationContext("META-INF/spring/spring-context.xml");
        ConfigUtil config = (ConfigUtil) sprinContext.getBean("configUtil");

        try {
            config.setPfeAqsEmailPassword(null);
            EmailUtil.sendEmailCreateAccount(null, config, "username", "123pass");
        } catch (MessagingException ex) {
        } catch (Exception ex) {
        }
    }
}
