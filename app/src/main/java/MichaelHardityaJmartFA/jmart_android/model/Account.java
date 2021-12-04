package MichaelHardityaJmartFA.jmart_android.model;

public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;
    public String getId(){
        return String.valueOf(id);
    }
}
