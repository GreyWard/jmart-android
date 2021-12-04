package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class ProductRequest{
    private static final String URL_FORMAT_PAGE= "http://10.0.2.2:8080/product/getFiltered?page=%1$s&pageSize=%2$s&conditionUsed=%3$s&conditionNew=%4$s&search=%5$s&minPrice=%6$s&maxPrice=%7$s&category=%8$s";


    public StringRequest ProductRequest(String page, String pageSize, String conditionUsed,
                          String conditionNew, String search, String minPrice,
                          String maxPrice, String category,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
        String url = String.format(URL_FORMAT_PAGE,page,pageSize,conditionUsed,conditionNew,search,minPrice,maxPrice,category);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }
}