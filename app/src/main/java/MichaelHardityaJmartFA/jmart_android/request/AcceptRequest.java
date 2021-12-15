package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A Payment request for accept method, updates Payment object status
 */
public class AcceptRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/%d/accept";
    private final Map<String,String> params;

    /**
     * Payment request constructor, used to create a StringRequest object
     * @param paymentId payment identifier number
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public AcceptRequest(int paymentId,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(Request.Method.POST, String.format(URL,paymentId), listener, errorListener);
        params = new HashMap<>();
    }
    public Map<String,String> getParams(){
        return this.params;
    }
}
