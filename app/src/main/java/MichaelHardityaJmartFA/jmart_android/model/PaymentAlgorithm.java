package MichaelHardityaJmartFA.jmart_android.model;

/**
 * Payment algorithm, used to count adjusted price of the payment (for front-end purposes).
 * Need to be updated over time
 */
public class PaymentAlgorithm
{

    public static final double COMMISSION_MULTIPLIER = 0.05;
    public static final double BOTTOM_PRICE = 20000.0;
    public static final double BOTTOM_FEE = 1000.0;

    /**
     * Get adjusted price from the price and discount
     * @param price total price
     * @param discount applied discount
     * @return adjusted price
     */
    public static double getAdjustedPrice(double price, double discount){
        return (getDiscountedPrice(price,discount)+getAdminFee(price,discount));
    }

    /**
     * Get admin fee taken from the price and discount
     * @param price total price
     * @param discount applied discount
     * @return admin fee
     */
    public static double getAdminFee(double price, double discount){
        if (getDiscountedPrice(price,discount)<BOTTOM_PRICE){
            return BOTTOM_FEE;
        }
        else{
            return (getDiscountedPrice(price,discount)*COMMISSION_MULTIPLIER);
        }
    }

    /**
     * Get discounted price from the price and discount
     * @param price total price
     * @param discount applied discount
     * @return discounted price
     */
    public static double getDiscountedPrice(double price, double discount){
        if (discount > 100.0){
            return 0.0;
        }
        else if (discount == 100.0){
            return price;
        }
        else{
            return (price / 100 * (100 - discount));
        }
    }
}