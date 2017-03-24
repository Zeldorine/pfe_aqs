package UtilTest;

import ets.pfe.aqs.util.ConfigUtil;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zeldorine
 */
public class ConfigUtilTest {
    
    @Test
    public void configUtilTest() {
        ConfigUtil config = new ConfigUtil();
        config.setAppName("Pfe AQS");
        config.setFromEmail("from@email.com");
        config.setPfeAqsEmailPassword("pass");
        config.setPfeAqsEmailUsername("username");
        config.setServerPort("8085");
        config.setSmtpHost("smtp host");
        config.setSmtpPort("25");
        config.setDefaultPass("123");
        
        assertEquals("Pfe AQS", config.getAppName());
        assertEquals("from@email.com", config.getFromEmail());
        assertEquals("pass", config.getPfeAqsEmailPassword());
        assertEquals("username", config.getPfeAqsEmailUsername());
        assertEquals("8085", config.getServerPort());
        assertEquals("smtp host", config.getSmtpHost());
        assertEquals("25", config.getSmtpPort());
        assertEquals("123", config.getDefaultPass());
    }
}
