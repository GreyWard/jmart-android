package MichaelHardityaJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.request.LoginRequest;
import MichaelHardityaJmartFA.jmart_android.request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView registrationPage = findViewById(R.id.text);
        EditText emailText = findViewById(R.id.email);
        EditText nameText = findViewById(R.id.name);
        EditText passText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Response.Listener<String> listener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                Toast.makeText(RegisterActivity.this,"Register Succesful",Toast.LENGTH_LONG);
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG);
                    }
                };
                RegisterRequest regReq = new RegisterRequest(emailText.getText().toString(), nameText.toString(),passText.getText().toString(),listener,errorListener);
                RequestQueue queues = Volley.newRequestQueue(RegisterActivity.this);
                queues.add(regReq);
            }
        });
    }
}