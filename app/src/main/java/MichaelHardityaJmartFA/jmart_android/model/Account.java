package MichaelHardityaJmartFA.jmart_android.model;

/**
 * Account class, stores account information and extends Serializable to store account identifier
 */
public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;

    /**
     * Get Account identifier number in type of String
     * @return identifier of the Account
     */
    public String getId(){
        return String.valueOf(id);
    }
}
