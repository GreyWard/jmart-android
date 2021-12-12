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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import MichaelHardityaJmartFA.jmart_android.model.Account;
import MichaelHardityaJmartFA.jmart_android.model.Product;
import MichaelHardityaJmartFA.jmart_android.model.ProductCategory;
import MichaelHardityaJmartFA.jmart_android.request.LoginRequest;
import MichaelHardityaJmartFA.jmart_android.request.RequestFactory;

public class MainActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static ArrayList<Product> products= new ArrayList<>();
    private static final ArrayList<String> productNames = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private EditText name;
    private EditText minPrice;
    private EditText maxPrice;
    private CheckBox conditionUsed;
    private CheckBox conditionNew;
    private Spinner catSpinner;
    private static final String PAGE_FORMAT="%d";
    public static int selectedPosition;
    public static List<Product> getProducts(){return products; }
    public static Product getSelectedProduct(){return products.get(selectedPosition);}
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
        name = findViewById(R.id.editTextProductName);
        minPrice = findViewById(R.id.editTextLowPrice);
        maxPrice = findViewById(R.id.editTextHighPrice);
        conditionUsed = findViewById(R.id.check_used);
        conditionNew = findViewById(R.id.check_new);
        Button apply = findViewById(R.id.button_apply);
        Button clear = findViewById(R.id.button_clear);
        catSpinner = findViewById(R.id.category_spinner);
        productCard = findViewById(R.id.product_card);
        filterCard = findViewById(R.id.filter_card);
        tabLayout = findViewById(R.id.tabLayout);
        resetFilter();
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
        listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, productNames);
        populateListView(0);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            startActivity(intent);
        });
        prev.setOnClickListener(v -> {
            pageText.setText(String.format(PAGE_FORMAT,Integer.parseInt(pageText.getText().toString())-1));
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
        });
        next.setOnClickListener(v -> {
            pageText.setText(String.format(PAGE_FORMAT,Integer.parseInt(pageText.getText().toString())+1));
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
        });
        go.setOnClickListener(v -> {
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateListView(page);
            Toast.makeText(MainActivity.this, "Going to page: "+pageText.getText(),Toast.LENGTH_LONG).show();
        });
        //FilterCard
        catSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ProductCategory.values()));
        apply.setOnClickListener(v -> {
            populateListView(0);
            Toast.makeText(MainActivity.this, "Filtered!",Toast.LENGTH_LONG).show();
            pageText.setText("1");
        });
        clear.setOnClickListener(v -> {
            resetFilter();
            populateListView(0);
            Toast.makeText(MainActivity.this, "Cleared!",Toast.LENGTH_LONG).show();
        });
    }
    public void resetFilter(){
        name.setText("");
        minPrice.setText("0");
        maxPrice.setText("0");
        conditionNew.setChecked(false);
        conditionUsed.setChecked(false);
    }
    public void populateListView(int page){
        Response.Listener<String> listener = response -> {
            try {
                products.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                products = gson.fromJson(String.valueOf(jsonArray), type);
                takeName(products);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
            }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            StringRequest prodReq = RequestFactory.getProduct(page,10,conditionUsed.isChecked(), conditionNew.isChecked(),
                    name.getText().toString(),minPrice.getText().toString(),maxPrice.getText().toString(),catSpinner.getSelectedItem().toString(),
                    listener,errorListener);
            RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
            queues.add(prodReq);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Please input all the necessary fields!",Toast.LENGTH_LONG).show();
        }
    }
    public void takeName(ArrayList<Product> item){
        productNames.clear();
        for (int i = 0; i < item.size(); i++){
            productNames.add(item.get(i).name);
        }
        listView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        menu.findItem(R.id.create_product).setVisible(logged.store != null);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if(item.getItemId() == R.id.search) {
            Toast.makeText(this, "use the filter tab pls", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (item.getItemId() == R.id.create_product) {
            Toast.makeText(this,"Creating Product",Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.profile){
            Toast.makeText(this,"Opening Profile", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, AboutMeActivity.class);
            startActivity(intent);
            return true;
        }
        else{
            throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Response.Listener<String> listener = response -> {
            try {
                JSONObject object = new JSONObject(response);
                if (object != null) {
                    LoginActivity.resetLoggedAccount(gson.fromJson(object.toString(), Account.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Re-Login failed, please Login again!", Toast.LENGTH_LONG).show();
            }
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        LoginRequest loginReq = new LoginRequest(LoginActivity.email, LoginActivity.pass, listener, errorListener);
        RequestQueue queues = Volley.newRequestQueue(MainActivity.this);
        queues.add(loginReq);
    }
}
