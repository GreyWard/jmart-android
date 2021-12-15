package MichaelHardityaJmartFA.jmart_android.model;

/**
 * Product class, stores product information such as store identifier, category, condition, discount, name, price, shipment plan, and weight
 */
public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    /**
     * Get product data (product name) in String type
     * @return product name in String
     */
    @Override
    public String toString(){
        return " " + name;
    }

    /**
     * Get product id (product identifier) in String type
     * @return product identifier number in String
     */
    public String getId(){
        return String.valueOf(id);
    }
}
