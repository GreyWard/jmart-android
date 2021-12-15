package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * An Account request to register a new account
 */
public class RegisterRequest extends StringRequest{
    private static final String URL = "http://10.0.2.2:8080/account/register";
    private final Map<String,String> params;

    /**
     * Account request to register new account, used to create StringRequest
     * @param name account name
     * @param email account email
     * @param password account password
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public RegisterRequest(String name, String email, String password,
                           Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, URL, listener, errorListener);
        params =  new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }
    public Map<String,String> getParams(){
        return this.params;
    }
}
