package UtilTest;

import ets.pfe.aqs.util.SecurityUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Zeldorine
 */
public class SecurityUtilTest {
    @Test
    public void encryptionTest(){
        String password = "Hello";
        String crypted = SecurityUtil.cryptWithMD5(password);
        Assert.assertEquals("8b1a9953c4611296a827abf8c4784d7", crypted);
    }
}
