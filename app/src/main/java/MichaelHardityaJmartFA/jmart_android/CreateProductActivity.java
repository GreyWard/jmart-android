package MichaelHardityaJmartFA.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.request.CreateProductRequest;

public class CreateProductActivity extends AppCompatActivity {
    private final Account logged = LoginActivity.getLoggedAccount();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        EditText prodName = findViewById(R.id.editTextCreateProdName);
        EditText prodWeight = findViewById(R.id.editTextCreateProdWeight);
        EditText prodPrice = findViewById(R.id.editTextCreateProdPrice);
        EditText prodDisc = findViewById(R.id.editTextCreateProdDisc);
        Button create = findViewById(R.id.buttonCreateProduct);
        Button cancel = findViewById(R.id.buttonCancelCreateProduct);
        Spinner category = findViewById(R.id.CreateProdCategorySpinner);
        Spinner shipment = findViewById(R.id.CreateProdShipPlanSpinner);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        create.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try {
                    Toast.makeText(CreateProductActivity.this, "Product Created!", Toast.LENGTH_LONG).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CreateProductActivity.this, "Creation Failed", Toast.LENGTH_LONG).show();
                }
            };
            int radioId = radioGroup.getCheckedRadioButtonId();
            final RadioButton radioButton = findViewById(radioId);
            try{
                String condition;
                if (radioButton.getText().toString().contentEquals("New")) {
                    condition = "0";
                } else {
                    condition = "1";
                }
                CreateProductRequest regReq = new CreateProductRequest(logged.getId(), prodName.getText().toString(), prodWeight.getText().toString(), condition,
                        prodPrice.getText().toString(), prodDisc.getText().toString(), category.getSelectedItem().toString(), shipment.getSelectedItem().toString(), listener, null);
                RequestQueue queues = Volley.newRequestQueue(CreateProductActivity.this);
                queues.add(regReq);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(CreateProductActivity.this,"Fill the necessary data!", Toast.LENGTH_SHORT).show();
            }

        });
        cancel.setOnClickListener(v -> {
            Toast.makeText(CreateProductActivity.this,"Cancelled",Toast.LENGTH_LONG).show();
            finish();
        });
    }
}