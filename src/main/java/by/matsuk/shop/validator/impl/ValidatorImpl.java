package by.matsuk.shop.validator.impl;

import by.matsuk.shop.validator.Validator;

import java.util.Map;

import static by.matsuk.shop.controller.Parameter.*;

/**
 * The type Validator.
 */
public class ValidatorImpl implements Validator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-я]{3,50}$";
    private static final String PRODUCT_NAME_PATTERN = "^[A-Za-zА-Яа-я\\s]{3,50}$";
    private static final String PRODUCT_AUTHOR_PATTERN = "^[A-Za-zА-Яа-я\\s]{1,50}$";
    private static final String USER_LOGIN_PATTERN = "^[A-Za-zА-Яа-я0-9_]{4,16}$";
    private static final String USER_PASSWORD_PATTERN = "^[A-Za-zА-Яа-я0-9\\.]{5,40}$";
    private static final String USER_MAIL_PATTERN = "^[A-Za-z0-9\\.]{1,30}@[a-z]{2,7}\\.[a-z]{2,4}$";
    private static final String USER_PHONE_NUMBER_PATTERN = "(29|33|25|44)\\d{7}";
    private static final String DIGIT_PRODUCT_PATTERN = "\\d{1,6}(\\.[0-9]{1,2})?";
    private static final String DISCOUNT_PATTERN = "\\d{1,6}(\\.[0-9]{1,2})?";
    private static final String DESCRIPTION_PATTERN = "^.{0,100}$";
    private static final String ADDRESS_PATTERN = "^.{1,100}$";
    private static final String SECTION_NAME_PATTERN = "^.{1,20}$";
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    private static final ValidatorImpl instance = new ValidatorImpl();

    private ValidatorImpl(){}

    /**
     * Get instance validator.
     *
     * @return the validator
     */
    public static ValidatorImpl getInstance(){
        return instance;
    }

    @Override
    public boolean isCorrectName(String name){
        return isNotNullOrEmpty(name) && name.matches(NAME_PATTERN);
    }

    @Override
    public boolean isCorrectLogin(String login){
        return isNotNullOrEmpty(login) && login.matches(USER_LOGIN_PATTERN);
    }

    @Override
    public boolean isCorrectPassword(String password){
        return isNotNullOrEmpty(password) && password.matches(USER_PASSWORD_PATTERN);
    }

    @Override
    public boolean isCorrectEmail(String mail){
        return isNotNullOrEmpty(mail) && mail.matches(USER_MAIL_PATTERN);
    }

    @Override
    public boolean isCorrectPhoneNumber(String phoneNumber){
        return isNotNullOrEmpty(phoneNumber) && phoneNumber.matches(USER_PHONE_NUMBER_PATTERN);
    }

    @Override
    public boolean isCorrectProductDigit(String digit) {
        return isNotNullOrEmpty(digit) && digit.matches(DIGIT_PRODUCT_PATTERN);
    }

    @Override
    public boolean isCorrectDiscount(String discount) {
        return isNotNullOrEmpty(discount) && discount.matches(DISCOUNT_PATTERN);
    }

    @Override
    public boolean isCorrectProductName(String name) {
        return isNotNullOrEmpty(name) && name.matches(PRODUCT_NAME_PATTERN);
    }

    @Override
    public boolean isCorrectProductAuthor(String author) {
        return isNotNullOrEmpty(author) && author.matches(PRODUCT_AUTHOR_PATTERN);
    }

    @Override
    public boolean isCorrectDescription(String description) {
        return description.matches(DESCRIPTION_PATTERN);
    }

    @Override
    public boolean isCorrectAddress(String address) {
        return isNotNullOrEmpty(address) && address.matches(ADDRESS_PATTERN);
    }

    @Override
    public boolean isCorrectSectionName(String sectionName) {
        return isNotNullOrEmpty(sectionName) && sectionName.matches(SECTION_NAME_PATTERN);
    }

    @Override
    public boolean isCorrectDate(String date) {
        return isNotNullOrEmpty(date) && date.matches(DATE_PATTERN);
    }

    @Override
    public boolean checkRegistration(Map<String, String> map) {
        boolean result = true;
        String firstName = map.get(USER_FIRST_NAME);
        String lastName = map.get(USER_LAST_NAME);
        String login = map.get(LOGIN);
        String password = map.get(PASSWORD);
        String email = map.get(USER_EMAIL);
        String phone = map.get(USER_PHONE_NUMBER);
        if(!isCorrectName(firstName)){
            map.put(USER_FIRST_NAME,INVALID_FIRST_NAME);
            result = false;
        }
        if(!isCorrectName(lastName)){
            map.put(USER_LAST_NAME,INVALID_LAST_NAME);
            result = false;
        }
        if(!isCorrectLogin(login)){
            map.put(LOGIN,INVALID_LOGIN);
            result = false;
        }
        if(!isCorrectPassword(password)){
            map.put(PASSWORD,INVALID_PASSWORD);
            result = false;
        }
        if(!isCorrectEmail(email)){
            map.put(USER_EMAIL, INVALID_EMAIL);
            result = false;
        }
        if(!isCorrectPhoneNumber(phone)){
            map.put(USER_PHONE_NUMBER,INVALID_PHONE_NUMBER);
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkUpdateProfile(Map<String, String> updateData) {
        boolean result = true;
        String firstName = updateData.get(USER_FIRST_NAME);
        String lastName = updateData.get(USER_LAST_NAME);
        String email = updateData.get(USER_EMAIL);
        String phone = updateData.get(USER_PHONE_NUMBER);

        if(!isCorrectName(firstName)){
            updateData.put(USER_FIRST_NAME,INVALID_FIRST_NAME);
            result = false;
        }
        if(!isCorrectName(lastName)){
            updateData.put(USER_LAST_NAME,INVALID_LAST_NAME);
            result = false;
        }
        if(!isCorrectEmail(email)){
            updateData.put(USER_EMAIL, INVALID_EMAIL);
            result = false;
        }
        if(!isCorrectPhoneNumber(phone)){
            updateData.put(USER_PHONE_NUMBER,INVALID_PHONE_NUMBER);
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkProductData(Map<String, String> map) {
        boolean result = true;
        String name = map.get(PRODUCT_NAME);
        String author = map.get(PRODUCT_AUTHOR);
        String description = map.get(PRODUCT_DESCRIPTION);
        String discount = map.get(PRODUCT_DISCOUNT);
        String price = map.get(PRODUCT_PRICE);
        String section = map.get(PRODUCT_SECTION);
        if(!isCorrectProductName(name)){
            map.put(PRODUCT_NAME, INVALID_PRODUCT_NAME);
            result = false;
        }
        if(!isCorrectProductAuthor(author)){
            map.put(PRODUCT_AUTHOR, INVALID_PRODUCT_AUTHOR);
            result = false;
        }
        if(!isCorrectDiscount(discount)){
            map.put(PRODUCT_DISCOUNT, INVALID_PRODUCT_DISCOUNT);
            result = false;
        }
        if(!isCorrectProductDigit(price)){
            map.put(PRODUCT_PRICE, INVALID_PRODUCT_PRICE);
            result = false;
        }
        if(!isCorrectDescription(description)){
            map.put(PRODUCT_DESCRIPTION, INVALID_PRODUCT_COMPOSITION);
            result = false;
        }
        if(!isNotNullOrEmpty(section)){
            map.put(PRODUCT_SECTION, INVALID_PRODUCT_SECTION);
            result = false;
        }
        return result;
    }

    @Override
    public boolean checkOrderInfo(Map<String, String> orderInfo) {
        boolean result = true;
        String address = orderInfo.get(ADDRESS);
        if(!isCorrectAddress(address)){
            orderInfo.put(ADDRESS, INVALID_ORDER_ADDRESS);
            result = false;
        }
        return result;
    }

    private boolean isNotNullOrEmpty(String line){
        return line != null && !line.isEmpty();
    }
}
