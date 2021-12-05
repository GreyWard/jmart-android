package MichaelHardityaJmartFA.jmart_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import MichaelHardityaJmartFA.jmart_android.request.RegisterRequest;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText emailText = findViewById(R.id.register_email);
        EditText nameText = findViewById(R.id.register_name);
        EditText passText = findViewById(R.id.register_password);
        Button regButton = findViewById(R.id.register);
        regButton.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if(object != null){
                        Toast.makeText(RegisterActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(RegisterActivity.this, "Register Error", Toast.LENGTH_LONG);
            RegisterRequest regReq = new RegisterRequest(nameText.getText().toString(), emailText.getText().toString(),passText.getText().toString(),listener,null);
            RequestQueue queues = Volley.newRequestQueue(RegisterActivity.this);
            queues.add(regReq);
            finish();
        });
    }
}