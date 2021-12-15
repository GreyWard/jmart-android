package MichaelHardityaJmartFA.jmart_android.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * An Account request to log in an account and enters the app
 */
public class LoginRequest extends StringRequest{
    private static final String URL = "http://10.0.2.2:8080/account/login";
    private final Map<String,String> params;

    /**
     * Login request constructor, used to create a StringRequest
     * @param email account email
     * @param password account password
     * @param listener listener for the request
     * @param errorListener listener for the request if an error happens
     */
        public LoginRequest(String email, String password,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
            super(Method.POST, URL, listener, errorListener);
            params = new HashMap<>();
            params.put("email", email);
            params.put("password", password);
        }
    public Map<String,String> getParams(){
        return this.params;
    }
}
