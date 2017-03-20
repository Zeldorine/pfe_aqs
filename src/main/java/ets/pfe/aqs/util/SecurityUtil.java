package ets.pfe.aqs.util;

import ets.pfe.aqs.PfeAqsServlet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Zeldorine
 */
public abstract class SecurityUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PfeAqsServlet.class);
    private static final String MD5_ALGORITHM = "MD5";

    private SecurityUtil(){}
    
    public static String cryptWithMD5(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
            byte[] passBytes = pass.getBytes();
            
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) {
                sb.append(Integer.toHexString(0xff & digested[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("An error occured during crypt password", ex);
        }
        
        return null;
    }
}
