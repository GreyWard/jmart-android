package MichaelHardityaJmartFA.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.request.LoginRequest;

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount=null;
    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registrationPage = findViewById(R.id.registrationPage);
        registrationPage.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
        EditText emailText = findViewById(R.id.email);
        EditText passText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        loggedAccount = gson.fromJson(object.toString(), Account.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Login Failed, check user and password!", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            LoginRequest loginReq = new LoginRequest(emailText.getText().toString(), passText.getText().toString(), listener, errorListener);
            RequestQueue queues = Volley.newRequestQueue(LoginActivity.this);
            queues.add(loginReq);
        });

    }
}