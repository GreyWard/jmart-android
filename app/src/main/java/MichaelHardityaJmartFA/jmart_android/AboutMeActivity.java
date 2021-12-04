package MichaelHardityaJmartFA.jmart_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.request.RegisterStoreRequest;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        Account logged = LoginActivity.getLoggedAccount();
        TextView name = findViewById(R.id.name_text);
        TextView email = findViewById(R.id.email_text);
        TextView balance = findViewById(R.id.balance_text);
        name.setText(logged.name);
        email.setText(logged.email);
        balance.setText(String.valueOf(logged.balance));
        //register store
        EditText RegStoreName = findViewById(R.id.editTextStoreName);
        EditText RegStoreAddress = findViewById(R.id.editTextAddress);
        EditText RegStorePhone = findViewById(R.id.editTextPhoneNum);
        TextView storeName = findViewById(R.id.storename_text);
        TextView storeAddress = findViewById(R.id.address_text);
        TextView storePhone = findViewById(R.id.phone_text);
        ConstraintLayout RegisterStoreConst = findViewById(R.id.RegisterStoreConstraint);
        ConstraintLayout StoreInfo = findViewById(R.id.StoreInfo);
        CardView RegisterCard = findViewById(R.id.store_card);
        Button RegisterStoreButton = findViewById(R.id.buttonRegisterStoreMenu);
        Button RegisterStore = findViewById(R.id.buttonRegisterStore);
        Button RegisterCancel = findViewById(R.id.buttonRegisterStoreCancel);
        if (logged.store == null) {
            RegisterCard.setVisibility(View.GONE);
            RegisterStoreButton.setVisibility(View.VISIBLE);
            StoreInfo.setVisibility(View.GONE);
            RegisterStoreConst.setVisibility(View.VISIBLE);
        }else{
            RegisterCard.setVisibility(View.VISIBLE);
            RegisterStoreButton.setVisibility(View.GONE);
            StoreInfo.setVisibility(View.VISIBLE);
            RegisterStoreConst.setVisibility(View.GONE);
            storeName.setText(logged.store.name);
            storeAddress.setText(logged.store.address);
            storePhone.setText(logged.store.phoneNumber);
        }
        RegisterStoreButton.setOnClickListener(v -> {
            RegisterCard.setVisibility(View.VISIBLE);
            RegisterStoreButton.setVisibility(View.GONE);
        });
        RegisterCancel.setOnClickListener(v -> {
            RegisterCard.setVisibility(View.GONE);
            RegisterStoreButton.setVisibility(View.VISIBLE);
        });
        RegisterStore.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try{
                    JSONObject object = new JSONObject(response);
                    if(object != null){
                        Toast.makeText(AboutMeActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(AboutMeActivity.this, "Register Error", Toast.LENGTH_LONG).show();
                }
            };
            RegisterStoreRequest regReq = new RegisterStoreRequest(logged.getId(),RegStoreName.getText().toString(), RegStoreAddress.getText().toString(),
                    RegStorePhone.getText().toString(),listener,null);
            RequestQueue queues = Volley.newRequestQueue(AboutMeActivity.this);
            queues.add(regReq);
            StoreInfo.setVisibility(View.VISIBLE);
            RegisterStoreConst.setVisibility(View.GONE);
            storeName.setText(logged.store.name);
            storeAddress.setText(logged.store.address);
            storePhone.setText(logged.store.phoneNumber);
        });
    }
}