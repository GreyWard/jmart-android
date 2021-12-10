package MichaelHardityaJmartFA.jmart_android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import MichaelHardityaJmartFA.jmart_android.model.Plans;
import MichaelHardityaJmartFA.jmart_android.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Product selected = MainActivity.getSelectedProduct();
        //now we proccess the selected data
        TextView productName = findViewById(R.id.product_name);
        TextView productCategory = findViewById(R.id.product_category);
        TextView productCondition = findViewById(R.id.product_condition);
        TextView productDiscount = findViewById(R.id.product_discount);
        TextView productPrice = findViewById(R.id.product_price);
        TextView productPlans = findViewById(R.id.product_shipment_plans);
        TextView productWeight = findViewById(R.id.product_weight);
        productName.setText(selected.name);
        productCategory.setText(selected.category.toString());
        if (selected.conditionUsed){
            productCondition.setText("Used");
        }else{
            productCondition.setText("New");
        }
        productDiscount.setText((int) selected.discount);
        productPrice.setText((int) selected.price);
        productPlans.setText(Plans.check(selected.shipmentPlans));
        productWeight.setText(selected.weight);
        Button back = findViewById(R.id.button_product_view_back);
        back.setOnClickListener(v -> finish());
        Button buy = findViewById(R.id.button_buy);
        //go buy the product
    }
}