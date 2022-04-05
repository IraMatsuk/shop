package by.matsuk.shop.entity;

/**
 * @project Postcard shop
 * @author Ira
 * The enum User role.
 */
public enum UserRole {
    /**
     * Admin user role.
     */
    ADMIN,
    /**
     * Guest user role.
     */
    GUEST,
    /**
     * User user role.
     */
    USER;

    /**
     * Gets role.
     *
     * @return the role in string format
     */
    public String getRole() {
        return this.toString().toLowerCase();
    }
}
