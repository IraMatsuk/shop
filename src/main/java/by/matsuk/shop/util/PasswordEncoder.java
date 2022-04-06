package by.matsuk.shop.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Ira  The type Password encoder.
 * @project Postcard shop "Card4You"
 * The type Password encoder.
 */
public class PasswordEncoder {
    /**
     * Md 5 apache string.
     *
     * @param password the password
     * @return the string
     */
    public static String md5Apache(String password){
        String md5Hex = DigestUtils.md5Hex(password);
        return md5Hex;
    }
}
