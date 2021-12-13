package MichaelHardityaJmartFA.jmart_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import MichaelHardityaJmartFA.jmart_android.model.Payment;
import MichaelHardityaJmartFA.jmart_android.model.Product;
import MichaelHardityaJmartFA.jmart_android.request.RequestFactory;

public class StoreOrderActivity extends AppCompatActivity {
    private ListView orderList;
    private ArrayAdapter<Payment> adapter;
    private ArrayList<Payment> orders;
    private static final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        EditText pageText = findViewById(R.id.edit_text_page_order);
        Button prev = findViewById(R.id.button_order_prev);
        Button next = findViewById(R.id.button_order_next);
        Button go = findViewById(R.id.button_order_go);
        orderList = findViewById(R.id.order_listview);
        Response.Listener<String> listener = response -> {
            try {
                orders.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                orders = gson.fromJson(String.valueOf(jsonArray), type);
                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, orders);
                orderList.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(StoreOrderActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
            }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(StoreOrderActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            StringRequest orderReq = RequestFactory.getOrder(LoginActivity.getLoggedAccount().id,0,10,
                    listener,errorListener);
            RequestQueue queues = Volley.newRequestQueue(StoreOrderActivity.this);
            queues.add(orderReq);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(StoreOrderActivity.this, "Please input all the necessary fields!",Toast.LENGTH_LONG).show();
        }
        orderList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(StoreOrderActivity.this, ProductDetailActivity.class);
            startActivity(intent);
        });
        prev.setOnClickListener(v -> {
            pageText.setText(String.format("%d",Integer.parseInt(pageText.getText().toString())-1));
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
        });
        next.setOnClickListener(v -> {
            pageText.setText(String.format("%d",Integer.parseInt(pageText.getText().toString())+1));
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
        });
        go.setOnClickListener(v -> {
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
            Toast.makeText(StoreOrderActivity.this, "Going to page: "+pageText.getText(),Toast.LENGTH_LONG).show();
        });
    }
    public void populateListView(int page){
        Response.Listener<String> listener = response -> {
            try {
                orders.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                orders = gson.fromJson(String.valueOf(jsonArray), type);
                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, orders);
                orderList.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(StoreOrderActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
            }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(StoreOrderActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            StringRequest orderReq = RequestFactory.getOrder(LoginActivity.getLoggedAccount().id,page,10,
                    listener,errorListener);
            RequestQueue queues = Volley.newRequestQueue(StoreOrderActivity.this);
            queues.add(orderReq);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(StoreOrderActivity.this, "Please input all the necessary fields!",Toast.LENGTH_LONG).show();
        }
    }
}