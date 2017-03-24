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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
    
    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getPfeAqsEmailUsername() {
        return pfeAqsEmailUsername;
    }

    public void setPfeAqsEmailUsername(String pfeAqsEmailUsername) {
        this.pfeAqsEmailUsername = pfeAqsEmailUsername;
    }

    public String getPfeAqsEmailPassword() {
        return pfeAqsEmailPassword;
    }

    public void setPfeAqsEmailPassword(String pfeAqsEmailPassword) {
        this.pfeAqsEmailPassword = pfeAqsEmailPassword;
    }

    public String getDefaultPass() {
        return defaultPass;
    }

    public void setDefaultPass(String defaultPass) {
        this.defaultPass = defaultPass;
    }
}
