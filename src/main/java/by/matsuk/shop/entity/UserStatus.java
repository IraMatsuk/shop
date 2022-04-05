package by.matsuk.shop.entity;

/**
 * project Postcard shop
 * @author Ira
 * The enum User status.
 */
public enum UserStatus {
    /**
     * Active user status.
     */
    ACTIVE,
    /**
     * Blocked user status.
     */
    BLOCKED;

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return this.toString().toLowerCase();
    }
}
