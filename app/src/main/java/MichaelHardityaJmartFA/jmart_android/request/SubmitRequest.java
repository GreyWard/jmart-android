package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A Payment request to submit a payment, changes the status to ON_DELIVERY
 */
public class SubmitRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/payment/%d/submit";
    private final Map<String,String> params;

    /**
     * Payment request to submit a payment, used to create StringRequest
     * @param paymentId payment identifier number
     * @param receipt receipt of the payment
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public SubmitRequest(int paymentId, String receipt,
                         Response.Listener<String> listener,
                         Response.ErrorListener errorListener) {
        super(Request.Method.POST, String.format(URL,paymentId), listener, errorListener);
        params = new HashMap<>();
        params.put("receipt",receipt);
    }
    public Map<String,String> getParams(){
        return this.params;
    }
}
