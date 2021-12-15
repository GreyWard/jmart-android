package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * A Product request to create new product
 */
public class CreateProductRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/product/create";
    private final Map<String,String> params;

    /**
     * CreateProduct request constructor, to create new product using StringRequest
     * @param accountId store account identifier number
     * @param name name of the product
     * @param weight weight of the product
     * @param conditionUsed condition of the product
     * @param price price of the product
     * @param discount discount of the product
     * @param category category of the product
     * @param shipmentPlans shipment plan of the product
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
    public CreateProductRequest(String accountId, String name,
                        String weight, String conditionUsed, String price,
                        String discount, String category, String shipmentPlans,
                        Response.Listener<String> listener,
                        Response.ErrorListener errorListener) {
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("accountId", accountId);
        params.put("name", name);
        params.put("weight",weight);
        params.put("conditionUsed", conditionUsed);
        params.put("price",price);
        params.put("discount",discount);
        params.put("category", category);
        params.put("shipmentPlans", shipmentPlans);
    }
    @Override
    public Map<String,String> getParams(){ return this.params; }
}