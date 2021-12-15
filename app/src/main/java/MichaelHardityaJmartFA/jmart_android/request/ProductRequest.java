package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * @deprecated A Product request to populate available products to buy, use RequestFactory.getProduct instead
 */
public class ProductRequest{
    private static final String URL_FORMAT_PAGE= "http://10.0.2.2:8080/product/getFiltered?page=%1$s&pageSize=%2$s&conditionUsed=%3$s&conditionNew=%4$s&search=%5$s&minPrice=%6$s&maxPrice=%7$s&category=%8$s";

    /**
     * Product request constructor, get product lists filtered by the parameter sent
     * @param page page for paginate
     * @param pageSize page size for paginate
     * @param conditionUsed product condition for used
     * @param conditionNew product condition for new
     * @param search product name (if searched)
     * @param minPrice product minimum price
     * @param maxPrice product maximum price
     * @param category product category
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     * @return StringRequest of the method
     */
    public StringRequest ProductRequest(String page, String pageSize, String conditionUsed,
                          String conditionNew, String search, String minPrice,
                          String maxPrice, String category,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
        String url = String.format(URL_FORMAT_PAGE,page,pageSize,conditionUsed,conditionNew,search,minPrice,maxPrice,category);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}