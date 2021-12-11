package MichaelHardityaJmartFA.jmart_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.model.PaymentAlgorithm;
import MichaelHardityaJmartFA.jmart_android.model.Plans;
import MichaelHardityaJmartFA.jmart_android.model.Product;
import MichaelHardityaJmartFA.jmart_android.request.PaymentRequest;

public class PaymentActivity extends AppCompatActivity {
    private final Product selectedProduct = MainActivity.getSelectedProduct();
    private final Account selectedAccount = LoginActivity.getLoggedAccount();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView productName = findViewById(R.id.product_buy_name);
        TextView productCategory = findViewById(R.id.product_buy_category);
        TextView productCondition = findViewById(R.id.product_buy_condition);
        TextView productDiscount = findViewById(R.id.product_buy_discount);
        TextView productPrice = findViewById(R.id.product_buy_price);
        TextView productPlans = findViewById(R.id.product_buy_plan);
        TextView productWeight = findViewById(R.id.product_buy_weight);
        productName.setText(selectedProduct.name);
        productCategory.setText(selectedProduct.category.toString());
        if (selectedProduct.conditionUsed){
            productCondition.setText("Used");
        }else{
            productCondition.setText("New");
        }
        productDiscount.setText(String.valueOf(selectedProduct.discount));
        productPrice.setText(String.valueOf(selectedProduct.price));
        productPlans.setText(Plans.check(selectedProduct.shipmentPlans));
        productWeight.setText(String.valueOf(selectedProduct.weight));
        EditText quantity = findViewById(R.id.product_buy_quantity);
        TextView balance = findViewById(R.id.account_buy_balance);
        TextView total = findViewById(R.id.total_buy_fee);
        TextView minus = findViewById(R.id.balance_not_enough);
        total.setText(String.valueOf(PaymentAlgorithm.getAdjustedPrice(selectedProduct.price,selectedProduct.discount)));
        double counted =selectedAccount.balance-PaymentAlgorithm.getAdjustedPrice(selectedProduct.price,selectedProduct.discount);
        balance.setText(String.valueOf(counted));
        EditText address = findViewById(R.id.product_buy_address);
        Button cancel = findViewById(R.id.button_cancel_buy);
        cancel.setOnClickListener(v -> finish());
        Button checkOut = findViewById(R.id.button_checkout);
        checkOut.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        Toast.makeText(PaymentActivity.this, "Purchase Successful", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(PaymentActivity.this, "Purchase Failed, try again later!", Toast.LENGTH_LONG).show();
                }
            };
            Response.ErrorListener errorListener = error -> Toast.makeText(PaymentActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            PaymentRequest loginReq = new PaymentRequest(selectedAccount.getId(), selectedProduct.getId(),quantity.getText().toString(),address.getText().toString(),
                    Byte.toString(selectedProduct.shipmentPlans), listener, errorListener);
            RequestQueue queues = Volley.newRequestQueue(PaymentActivity.this);
            queues.add(loginReq);
        });
        if (counted < 0){
            balance.setTextColor(Color.RED);
            minus.setVisibility(View.VISIBLE);
            checkOut.setClickable(false);
            checkOut.setBackgroundColor(Color.GRAY);
        }
    }
}