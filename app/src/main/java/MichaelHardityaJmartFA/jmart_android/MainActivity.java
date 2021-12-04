package MichaelHardityaJmartFA.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.model.Product;
import MichaelHardityaJmartFA.jmart_android.model.ProductCategory;
import MichaelHardityaJmartFA.jmart_android.request.ProductRequest;

public class MainActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static List<Product> products= new ArrayList<>();
    public static List<Product> getProducts(){
        return products;
    }
    //checking account
    Account logged = LoginActivity.getLoggedAccount();
    private TabLayout tabLayout;
    private int selectedTab;
    private CardView productCard;
    private CardView filterCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FilterCard
        EditText name = findViewById(R.id.editTextProductName);
        EditText minPrice = findViewById(R.id.editTextLowPrice);
        EditText maxPrice = findViewById(R.id.editTextHighPrice);
        CheckBox conditionUsed = findViewById(R.id.check_used);
        CheckBox conditionNew = findViewById(R.id.check_new);
        Button apply = findViewById(R.id.button_apply);
        Button clear = findViewById(R.id.button_clear);
        Spinner catSpinner = (Spinner) findViewById(R.id.category_spinner);

        productCard = findViewById(R.id.product_card);
        filterCard = findViewById(R.id.filter_card);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab=tabLayout.getSelectedTabPosition();
                if (selectedTab==1){
                    productCard.setVisibility(View.GONE);
                    filterCard.setVisibility(View.VISIBLE);
                }else{
                    filterCard.setVisibility(View.GONE);
                    productCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //ProductCard
        EditText pageText = findViewById(R.id.editTextPage);
        Button prev = findViewById(R.id.buttonPrev);
        Button next = findViewById(R.id.buttonNext);
        Button go = findViewById(R.id.buttonGo);

        ListView listView = (ListView) findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.listview, Collections.singletonList(products.toString()));
        listView.setAdapter(adapter);
        prev.setOnClickListener(v -> {
            pageText.setText(Integer.toString(Integer.parseInt(pageText.getText().toString())-1));
            String page = Integer.toString(Integer.parseInt(pageText.getText().toString())-1);
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        products=null;
                        Toast.makeText(MainActivity.this, "Filtered", Toast.LENGTH_LONG).show();
                        products.add(gson.fromJson(object.toString(), Product.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
                }
            };
            String firstCond = "0";
            String secCond = "0";
            if (conditionUsed.isChecked()){
                firstCond = "1";
            }
            if(conditionNew.isChecked()){
                secCond = "1";
            }
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            ProductRequest productReq = new ProductRequest();
            StringRequest prodReq = productReq.ProductRequest("0","10",firstCond, secCond,
                    name.getText().toString(),minPrice.getText().toString(),maxPrice.getText().toString(),catSpinner.getSelectedItem().toString(),
                    listener,null);
            RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
            queues.add(prodReq);
        });
        next.setOnClickListener(v -> {
            pageText.setText(Integer.toString(Integer.parseInt(pageText.getText().toString())+1));
            String page = Integer.toString(Integer.parseInt(pageText.getText().toString())-1);
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        products=null;
                        Toast.makeText(MainActivity.this, "Filtered", Toast.LENGTH_LONG).show();
                        products.add(gson.fromJson(object.toString(), Product.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
                }
            };
            String firstCond = "0";
            String secCond = "0";
            if (conditionUsed.isChecked()){
                firstCond = "1";
            }
            if(conditionNew.isChecked()){
                secCond = "1";
            }
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            ProductRequest productReq = new ProductRequest();
            StringRequest prodReq = productReq.ProductRequest("0","10",firstCond, secCond,
                    name.getText().toString(),minPrice.getText().toString(),maxPrice.getText().toString(),catSpinner.getSelectedItem().toString(),
                    listener,null);
            RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
            queues.add(prodReq);
        });
        go.setOnClickListener(v -> {
            String page = Integer.toString(Integer.parseInt(pageText.getText().toString())-1);
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        products=null;
                        Toast.makeText(MainActivity.this, "Filtered", Toast.LENGTH_LONG).show();
                        products.add(gson.fromJson(object.toString(), Product.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
                }
            };
            String firstCond = "0";
            String secCond = "0";
            if (conditionUsed.isChecked()){
                firstCond = "1";
            }
            if(conditionNew.isChecked()){
                secCond = "1";
            }
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            ProductRequest productReq = new ProductRequest();
            StringRequest prodReq = productReq.ProductRequest("0","10",firstCond, secCond,
                    name.getText().toString(),minPrice.getText().toString(),maxPrice.getText().toString(),catSpinner.getSelectedItem().toString(),
                    listener,null);
            RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
            queues.add(prodReq);
        });
        //FilterCard
        catSpinner.setAdapter(new ArrayAdapter<ProductCategory>(this, android.R.layout.simple_spinner_item,ProductCategory.values()));
        apply.setOnClickListener(v -> {
            Response.Listener<String> listener = response -> {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object != null) {
                        products=null;
                        Toast.makeText(MainActivity.this, "Filtered", Toast.LENGTH_LONG).show();
                        products.add(gson.fromJson(object.toString(), Product.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
                }
            };
            String firstCond = "0";
            String secCond = "0";
            if (conditionUsed.isChecked()){
                firstCond = "1";
            }
            if(conditionNew.isChecked()){
                secCond = "1";
            }
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            ProductRequest productReq = new ProductRequest();
            StringRequest prodReq = productReq.ProductRequest("0","10",firstCond, secCond,
                    name.getText().toString(),minPrice.getText().toString(),maxPrice.getText().toString(),catSpinner.getSelectedItem().toString(),
                    listener,null);
            RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
            queues.add(prodReq);
            pageText.setText("1");
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        if(logged.store !=null){
            menu.findItem(R.id.create_product).setVisible(true);
        } else{
            menu.findItem(R.id.create_product).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()){
            case R.id.search:
                Toast.makeText(this,"use the filter tab pls",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.create_product:
                Toast.makeText(this,"Creating Product",Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, CreateProductActivity.class);
                startActivity(intent);
                return true;
            case R.id.profile:
                Toast.makeText(this,"Opening Profile", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, AboutMeActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}