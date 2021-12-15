package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * An Account request to register a new store for the account
 */
public class RegisterStoreRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/%d/registerStore";
    private final Map<String,String> params;

    /**
     * An Account request constructor, used to create a StringRequest
     * @param id account identifier number
     * @param name new store name
     * @param address new store address
     * @param phoneNumber new store phone number
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public RegisterStoreRequest(int id, String name, String address, String phoneNumber,
                           Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, String.format(URL,id), listener, errorListener);
        params =  new HashMap<>();
        params.put("name", name);
        params.put("address", address);
        params.put("phoneNumber", phoneNumber);
    }
    public Map<String,String> getParams(){
        return this.params;
    }
}

