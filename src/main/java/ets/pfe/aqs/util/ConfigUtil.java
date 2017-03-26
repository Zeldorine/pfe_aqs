package ets.pfe.aqs.util;

/**
 *
 * @author Zeldorine
 */
public class ConfigUtil {
    private String appName;
    private String serverPort;
    private String smtpHost;
    private String smtpPort;
    private String fromEmail;
    private String pfeAqsEmailUsername;
    private String pfeAqsEmailPassword;
    private String defaultPass;

    /**
     * 
     * @return 
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 
     * @param appName 
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    /**
     * 
     * @return 
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * 
     * @param serverPort 
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * 
     * @return 
     */
    public String getSmtpHost() {
        return smtpHost;
    }

    /**
     * 
     * @param smtpHost 
     */
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * 
     * @return 
     */
    public String getSmtpPort() {
        return smtpPort;
    }

    /**
     * 
     * @param smtpPort 
     */
    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
    
    /**
     * 
     * @return 
     */
    public String getFromEmail() {
        return fromEmail;
    }

    /**
     * 
     * @param fromEmail 
     */
    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    /**
     * 
     * @return 
     */
    public String getPfeAqsEmailUsername() {
        return pfeAqsEmailUsername;
    }

    /**
     * 
     * @param pfeAqsEmailUsername 
     */
    public void setPfeAqsEmailUsername(String pfeAqsEmailUsername) {
        this.pfeAqsEmailUsername = pfeAqsEmailUsername;
    }

    /**
     * 
     * @return 
     */
    public String getPfeAqsEmailPassword() {
        return pfeAqsEmailPassword;
    }

    /**
     * 
     * @param pfeAqsEmailPassword 
     */
    public void setPfeAqsEmailPassword(String pfeAqsEmailPassword) {
        this.pfeAqsEmailPassword = pfeAqsEmailPassword;
    }

    /**
     * 
     * @return 
     */
    public String getDefaultPass() {
        return defaultPass;
    }

    /**
     * 
     * @param defaultPass 
     */
    public void setDefaultPass(String defaultPass) {
        this.defaultPass = defaultPass;
    }
}
