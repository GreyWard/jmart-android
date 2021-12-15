package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A Payment request to create new payment
 */
public class PaymentRequest extends StringRequest {
        private static final String URL = "http://10.0.2.2:8080/payment/create";
        private final Map<String,String> params;

    /**
     * Payment request constructor, used to create a StringRequest
     * @param buyerId buyer account identifier number
     * @param productId product identifier number
     * @param productCount product quantity
     * @param address shipment address destination
     * @param plan shipment plan
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
        public PaymentRequest(String buyerId, String productId,
                            String productCount, String address,
                            String plan,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
            super(Method.POST, URL, listener, errorListener);
            params = new HashMap<>();
            params.put("buyerId", buyerId);
            params.put("productId", productId);
            params.put("productCount",productCount);
            params.put("shipmentAddress",address);
            params.put("shipmentPlan", plan);
        }
        public Map<String,String> getParams(){
            return this.params;
        }
}


