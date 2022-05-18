package by.matsuk.shop.model.service;

import by.matsuk.shop.entity.Postcard;
import by.matsuk.shop.entity.UserDiscount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * The type CalculateService class.
 */
public class CalculateService {
    private static final CalculateService instance = new CalculateService();
    private CalculateService(){}

    /**
     * Get instance calculate service.
     *
     * @return the CalculateService object
     */
    public static CalculateService getInstance(){
        return instance;
    }

    /**
     * Calculate total price for order. If a product discount equals zero
     * it uses a user discount for calculating the total price.
     *
     * @param userDiscount the discount
     * @param orderedPostcards      the map
     * @return the big decimal
     */
    public BigDecimal calculateTotalPrice(UserDiscount userDiscount, Map<Postcard, Integer> orderedPostcards){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Postcard postcard: orderedPostcards.keySet()){
            int numberOfPostcards = orderedPostcards.get(postcard);
            BigDecimal postcardPrice = postcard.getPrice();
            BigDecimal postcardDiscount = postcard.getDiscount();
            if(postcardDiscount.equals(BigDecimal.valueOf(0.00)) && userDiscount != null){
                postcardDiscount = userDiscount.getDiscount();
            }
            totalPrice = totalPrice.add(postcardPrice.multiply(BigDecimal.valueOf(numberOfPostcards).multiply((BigDecimal.valueOf(100.00).subtract(postcardDiscount)).divide(BigDecimal.valueOf(100.00)))));
        }
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }
}