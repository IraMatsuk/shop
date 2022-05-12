package by.matsuk.shop.validator.impl;

import by.matsuk.shop.validator.Validator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MenuValidatorImplTest {
    Validator validator;

    @BeforeClass
    public void setUp() {
        validator = ValidatorImpl.getInstance();
    }

    @AfterClass
    public void end() {
        validator = null;
    }

    @DataProvider(name = "productName")
    public Object[][] productNameData() {
        return new Object[][]{
                {false, "A"},
                {false, "8f8f88f"},
                {false, ""},
                {false, "Postcard \"Mops\""},
                {false, "<script>alert(1)</script"},
                {true, "Name"},
                {true, "Product name"},
                {true, "Postcard Animal Dog"}
        };
    }

    @DataProvider(name = "productDigit")
    public Object[][] digitData() {
        return new Object[][]{
                {true, "0.75"},
                {true, "2"},
                {true, "2.5"},
                {true, "2.50"},
                {true, "44.44"},
                {true, "1000.5"},
                {false, "400."},
                {false, "400.444"},
                {false, "400,4"},
                {false, "1111111.10"},
        };
    }

    @DataProvider(name = "discount")
    public Object[][] discountData() {
        return new Object[][]{
                {true, "0.1"},
                {true, "0.11"},
                {true, "0"},
                {true, "1.10"},
                {false, "0."},
                {false, "0.111"},
                {false, "0,11"}
        };
    }

    @Test(dataProvider = "productName")
    public void isCorrectNameTest(boolean expected, String name) {
        boolean actual = validator.isCorrectProductName(name);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "productDigit")
    public void isCorrectDigitTest(boolean expected, String productDigit) {
        boolean actual = validator.isCorrectProductDigit(productDigit);
        assertEquals(actual, expected);
    }

    @Test(dataProvider = "discount")
    public void isCorrectDiscountTest(boolean expected, String discount) {
        boolean actual = validator.isCorrectDiscount(discount);
        assertEquals(actual, expected);
    }
}