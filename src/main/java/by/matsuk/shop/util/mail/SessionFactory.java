package by.matsuk.shop.util.mail;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

import java.util.Properties;

/**
 * The type Session factory.
 */
public class SessionFactory {
    /**
     * Create session session.
     *
     * @param configProperties the config properties
     * @return the session
     */
    public static Session createSession(Properties configProperties) {
        String userName = configProperties.getProperty("mail.user.name");
        String userPassword = configProperties.getProperty("mail.user.password");
        return Session.getDefaultInstance(configProperties, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        });
    }
}
