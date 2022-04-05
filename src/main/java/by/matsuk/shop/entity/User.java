package by.matsuk.shop.entity;

import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * The type User.
 */
public class User extends AbstractEntity implements Cloneable {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private String phone;
    private Timestamp registrationDate;
    private UserRole userRole;
    private UserStatus userStatus;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets login.
     *
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets login.
     *
     * @param login the login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets registration date.
     *
     * @return the registration date
     */
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets registration date.
     *
     * @param registrationDate the registration date
     */
    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Gets user role.
     *
     * @return the user role
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets user role.
     *
     * @param userRole the user role
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets user status.
     *
     * @return the user status
     */
    public UserStatus getUserStatus() {
        return userStatus;
    }

    /**
     * Sets user status.
     *
     * @param userStatus the user status
     */
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }
        User user = (User) o;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) {
            return  false;
        }
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) {
            return  false;
        }
        if (login != null ? !login.equals(user.login) : user.login != null) {
            return  false;
        }
        if (password != null ? !password.equals(user.password) : user.password != null) {
            return  false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return  false;
        }
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) {
            return  false;
        }
//        if (address != null ? !address.equals(user.address) : user.address != null) {
//            return  false;
//        }
        if (registrationDate != null ? !registrationDate.equals(user.registrationDate) : user.registrationDate != null) {
            return  false;
        }
        if (userRole != null ? !userRole.equals(user.userRole) : user.userRole != null) {
            return  false;
        }
        return  userStatus != null ? userStatus.equals(user.userStatus) : user.userStatus == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (firstName != null ? firstName.hashCode() : 0);
        result = result * 31 + (lastName != null ? lastName.hashCode() : 0);
        result = result * 31 + (login != null ? login.hashCode() : 0);
        result = result * 31 + (password != null ? password.hashCode() : 0);
        result = result * 31 + (email != null ? email.hashCode() : 0);
        result = result * 31 + (phone != null ? phone.hashCode() : 0);
       // result = result * 31 + (address != null ? address.hashCode() : 0);
        result = result * 31 + (userRole != null ? userRole.hashCode() : 0);
        result = result * 31 + (userStatus != null ? userStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                //.add("address='" + address + "'")
                .add("registrationDate='" + registrationDate + "'")
                .add("userRole=" + userRole)
                .add("userStatus=" + userStatus)
                .toString();
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

    /**
     * The type User builder.
     */
    public static class UserBuilder {
        private final User user;

        /**
         * Instantiates a new User builder.
         */
        public UserBuilder() {
            user = new User();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public UserBuilder setId(long id) {
            user.setId(id);
            return this;
        }

        /**
         * Sets first name.
         *
         * @param firstName the first name
         * @return the first name
         */
        public UserBuilder setFirstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        /**
         * Sets last name.
         *
         * @param lastName the last name
         * @return the last name
         */
        public UserBuilder setLastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        /**
         * Sets login.
         *
         * @param login the login
         * @return the login
         */
        public UserBuilder setLogin(String login) {
            user.login = login;
            return this;
        }

        /**
         * Sets password.
         *
         * @param password the password
         * @return the password
         */
        public UserBuilder setPassword(String password) {
            user.password = password;
            return this;
        }

        /**
         * Sets email.
         *
         * @param email the email
         * @return the email
         */
        public UserBuilder setEmail(String email) {
            user.email = email;
            return this;
        }

        /**
         * Sets phone.
         *
         * @param phone the phone
         * @return the phone
         */
        public UserBuilder setPhone(String phone) {
            user.phone = phone;
            return this;
        }

//        /**
//         * Sets address.
//         *
//         * @param address the address
//         * @return the address
//         */
//        public UserBuilder setAddress(String address) {
//            user.address = address;
//            return this;
//        }

        /**
         * Sets registration date.
         *
         * @param registrationDate the registration date
         * @return the registration date
         */
        public UserBuilder setRegistrationDate(Timestamp registrationDate) {
            user.registrationDate = registrationDate;
            return this;
        }

        /**
         * Sets user role.
         *
         * @param userRole the user role
         * @return the user role
         */
        public UserBuilder setUserRole(UserRole userRole) {
            user.userRole = userRole;
            return this;
        }

        /**
         * Sets user status.
         *
         * @param userStatus the user status
         * @return the user status
         */
        public UserBuilder setUserStatus(UserStatus userStatus) {
            user.userStatus = userStatus;
            return this;
        }

        /**
         * Build user.
         *
         * @return the user
         */
        public User build() {
            return user;
        }
    }
}
