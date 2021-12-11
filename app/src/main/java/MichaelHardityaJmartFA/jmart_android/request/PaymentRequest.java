package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentRequest extends StringRequest {
        private static final String URL = "http://10.0.2.2:8080/payment/create";
        private final Map<String,String> params;

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


