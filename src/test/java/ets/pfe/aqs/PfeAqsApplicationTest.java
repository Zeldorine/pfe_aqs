package ets.pfe.aqs;


import ets.pfe.aqs.PfeAqsApplication;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zeldorine
 */
public class PfeAqsApplicationTest {

    @Test
    public void mainTest() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        
        try {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    PfeAqsApplication.stop();
                }
            }, 6, TimeUnit.SECONDS);

            PfeAqsApplication.main(new String[]{});
            assertNotNull(PfeAqsApplication.getConfig());

        } catch (Exception ex) {
            Logger.getLogger(PfeAqsApplicationTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            PfeAqsApplication.stop();
            scheduler.shutdown();
        }
    }
    
    @Test
    public void mainErrorTest(){
        try{
            PfeAqsApplication.stop();
        } catch (Exception ex) {
            return;
        }
    }
}
