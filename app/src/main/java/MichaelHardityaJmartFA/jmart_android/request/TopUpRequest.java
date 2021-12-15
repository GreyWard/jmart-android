package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A Top Up request to top up an account
 */
public class TopUpRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/account/%d/topUp";
    private final Map<String,String> params;

    /**
     * TopUp request to top up an account, used to create StringRequest
     * @param id account identifier number
     * @param code top up code to add account balance
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public TopUpRequest(int id, String code, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Method.POST, String.format(URL,id), listener, errorListener);
        params =  new HashMap<>();
        params.put("couponCode", code);
    }
    public Map<String,String> getParams(){
        return this.params;
    }
}
