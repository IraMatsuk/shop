package by.matsuk.shop.validator.impl;

import by.matsuk.shop.validator.Validator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserValidatorImplTest {
    Validator validator;

    @BeforeClass
    public void setUp() {
        validator = ValidatorImpl.getInstance();
    }

    @AfterClass
    public void end() {
        validator = null;
    }

    @DataProvider(name = "login")
    public Object[][] loginData() {
        return new Object[][]{
                {true, "login"},
                {true, "1234567812345678"},
                {true, "1_2a3_4_4"},
                {true, "hello_user"},
                {true, "dddddddddddddddd"},
                {false, "p"},
                {false, "log"},
                {false, ""},
                {false, "_@#$&?"},
                {false, "ddddddddddddddddd"},
                {false, "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"}
        };
    }

    @DataProvider(name = "password")
    public Object[][] passwordData() {
        return new Object[][]{
                {true, "password"},
                {true, "pass.pass9999"},
                {true, "pass#pass9999"},
                {true, "@password#!"},
                {true, "!#$_%@"},
                {false, "<script>password</script>"},
                {false, "p"},
                {false, ""},
                {false, "pass"},
                {false, "dscfsdd,vdc-_"},
                {false, "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"},
        };
    }

    @DataProvider(name = "mail")
    public Object[][] mailData() {
        return new Object[][]{
                {true, "g3726866@gmail.com"},
                {true, "noreply@mail.ru"},
                {true, "m@mail.ru"},
                {true, "noreplymail.noreplymail.norepl@gamil.com"},
                {false, ""},
                {false, "a@"},
                {false, "a@mail"},
                {false, "aa@mail."},
                {false, "anna@mail.r"},
                {false, "noreplymail.noreplymail.noreply@gamil.com"}
        };
    }

    @DataProvider(name = "phone")
    public Object[][] phoneData() {
        return new Object[][]{
                {true, "251111111"},
                {true, "339999999"},
                {true, "449999000"},
                {true, "295555555"},
                {false, "112222222"},
                {false, "2966666666"},
                {false, ""}
        };
    }

    @Test(dataProvider = "login")
    public void isCorrectLoginTest(boolean expected, String login) {
        boolean actual = validator.isCorrectLogin(login);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "password")
    public void isCorrectPasswordTest(boolean expected, String password) {
        boolean actual = validator.isCorrectPassword(password);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "mail")
    public void isCorrectMailTest(boolean expected, String mail) {
        boolean actual = validator.isCorrectEmail(mail);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "phone")
    public void isCorrectPhoneTest(boolean expected, String phone) {
        boolean actual = validator.isCorrectPhoneNumber(phone);
        assertEquals(actual, expected);
    }
}