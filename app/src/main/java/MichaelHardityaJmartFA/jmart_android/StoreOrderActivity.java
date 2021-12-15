package MichaelHardityaJmartFA.jmart_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import MichaelHardityaJmartFA.jmart_android.model.Invoice;
import MichaelHardityaJmartFA.jmart_android.model.Payment;
import MichaelHardityaJmartFA.jmart_android.model.Plans;
import MichaelHardityaJmartFA.jmart_android.request.AcceptRequest;
import MichaelHardityaJmartFA.jmart_android.request.PaymentCancelRequest;
import MichaelHardityaJmartFA.jmart_android.request.RequestFactory;
import MichaelHardityaJmartFA.jmart_android.request.SubmitRequest;

/**
 * Store order activity, shows store order lists and track its order, only store owner can access this activity
 */
public class StoreOrderActivity extends AppCompatActivity {
    private ListView orderList;
    private ListView historyList;
    private ArrayAdapter<Payment> orderAdapter;
    private ArrayList<Payment> orders;
    private static final Gson gson = new Gson();
    private Payment selectedItem;
    ProgressBar progress;
    private TextView labelStatus1;
    private TextView labelStatus2;
    private TextView labelStatus3;
    private TextView labelStatus4;
    private TextView labelStatus5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        EditText pageText = findViewById(R.id.edit_text_page_order);
        Button prev = findViewById(R.id.button_order_prev);
        Button next = findViewById(R.id.button_order_next);
        Button go = findViewById(R.id.button_order_go);
        orderList = findViewById(R.id.order_listview);
        CardView orderCard = findViewById(R.id.order_card);
        CardView orderDetail = findViewById(R.id.order_detail_card);
        Button back = findViewById(R.id.button_back_to_order_list);
        Button cancel = findViewById(R.id.button_cancel_order);
        Button accept = findViewById(R.id.button_accept);
        Button submit = findViewById(R.id.button_submit);
        historyList = findViewById(R.id.order_status_listview);
        TextView orderId = findViewById(R.id.order_id);
        TextView productId = findViewById(R.id.order_product_id);
        TextView quantity = findViewById(R.id.order_product_count);
        TextView plan = findViewById(R.id.order_product_shipmentplan);
        TextView address = findViewById(R.id.order_destination);
        labelStatus1 = findViewById(R.id.label_status_confirmation_order);
        labelStatus2 = findViewById(R.id.label_status_on_progress_order);
        labelStatus3 = findViewById(R.id.label_status_on_delivery_order);
        labelStatus4 = findViewById(R.id.label_status_delivered_order);
        labelStatus5 = findViewById(R.id.label_status_finished_order);
        TextView status = findViewById(R.id.order_status_message);
        progress = findViewById(R.id.order_progressbar);
        Response.Listener<String> listener = response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                orders = gson.fromJson(String.valueOf(jsonArray), type);
                orderAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, orders);
                orderList.setAdapter(orderAdapter);
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
            populateOrderListView(page);
        });
        next.setOnClickListener(v -> {
            pageText.setText(String.format("%d",Integer.parseInt(pageText.getText().toString())+1));
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateOrderListView(page);
        });
        go.setOnClickListener(v -> {
            int page = Integer.parseInt(pageText.getText().toString())-1;
            populateOrderListView(page);
            Toast.makeText(StoreOrderActivity.this, "Going to page: "+pageText.getText(),Toast.LENGTH_LONG).show();
        });
        orderList.setOnItemClickListener((parent, view, position, id) -> {
            orderCard.setVisibility(View.GONE);
            orderDetail.setVisibility(View.VISIBLE);
            populateHistoryListView(orders.get(position));
            selectedItem = orders.get(position);
            if (selectedItem.history.get(selectedItem.history.size()-1).status == Invoice.Status.WAITING_CONFIRMATION){
                cancel.setVisibility(View.VISIBLE);
                accept.setVisibility(View.VISIBLE);
                submit.setVisibility(View.GONE);
            }else if (selectedItem.history.get(selectedItem.history.size()-1).status == Invoice.Status.ON_PROGRESS){
                cancel.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
            }else{
                cancel.setVisibility(View.GONE);
                accept.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
            }
            orderId.setText(String.valueOf(selectedItem.id));
            productId.setText(String.valueOf(selectedItem.productId));
            quantity.setText(String.valueOf(selectedItem.productCount));
            plan.setText((Plans.check(selectedItem.shipment.plan)));
            address.setText(selectedItem.shipment.address);
            status.setText(selectedItem.history.get(selectedItem.history.size()-1).message);
            progressSet(selectedItem.history.get(selectedItem.history.size()-1).status);
        });
        //the orderDetail card
        back.setOnClickListener(v -> {
            orderCard.setVisibility(View.VISIBLE);
            orderDetail.setVisibility(View.GONE);
        });
        accept.setOnClickListener(v -> {
            acceptOrder(selectedItem.id);
            finish();
        });
        cancel.setOnClickListener(v -> {
            cancelOrder(selectedItem.id);
            finish();
        });
        submit.setOnClickListener(v -> {
            String receipt = "Receipt{id=" + selectedItem.id + ",productCount=" + selectedItem.productCount +
                    ",buyerId=" + selectedItem.buyerId + ",shipmentPlan=" + selectedItem.shipment.plan
                    + selectedItem.shipment.address;
            submitOrder(selectedItem.id,receipt);
            finish();
        });
    }

    /**
     * Accepting order method, used to send AcceptRequest
     * @param id payment identifier number
     */
    public void acceptOrder(int id){
        Response.Listener<String> listener = response -> {
            Toast.makeText(StoreOrderActivity.this, "Pemesanan Berhasil diterima!", Toast.LENGTH_LONG).show();
            finish();
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(StoreOrderActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        AcceptRequest acceptReq = new AcceptRequest(id, listener, errorListener);
        RequestQueue queues = Volley.newRequestQueue(StoreOrderActivity.this);
        queues.add(acceptReq);
    }

    /**
     * Submitting order method, used to send SubmitRequest
     * @param id payment identifier number
     * @param receipt receipt of the order
     */
    public void submitOrder(int id, String receipt){
        Response.Listener<String> listener = response -> {
            Toast.makeText(StoreOrderActivity.this, "Pemesanan Berhasil submit! Tunggu kurirnya ya!", Toast.LENGTH_LONG).show();
            finish();
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(StoreOrderActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        SubmitRequest submitReq = new SubmitRequest(id, receipt, listener, errorListener);
        RequestQueue queues = Volley.newRequestQueue(StoreOrderActivity.this);
        queues.add(submitReq);
    }

    /**
     * Cancelling order method, used to send PaymentCancelRequest
     * @param id payment identifier number
     */
    public void cancelOrder(int id){
        Response.Listener<String> listener = response -> {
            Toast.makeText(StoreOrderActivity.this, "Pemesanan Berhasil di Cancel!", Toast.LENGTH_LONG).show();
            finish();
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(StoreOrderActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        PaymentCancelRequest cancelReq = new PaymentCancelRequest(id, listener, errorListener);
        RequestQueue queues = Volley.newRequestQueue(StoreOrderActivity.this);
        queues.add(cancelReq);
    }

    /**
     * Populate history listview method, updates the history listview by taking history data from the payment data
     * @param data the payment data
     */
    public void populateHistoryListView(Payment data){
        ArrayList<String> history = new ArrayList<>();
        for (Payment.Record rec : data.history){
            history.add(rec.toString());
        }
        ArrayAdapter<String> historyAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, history);
        historyList.setAdapter(historyAdapter);
    }

    /**
     * Populate order listview method, updates the order listview by converting JSONArray response
     * @param page the page we want to see
     */
    public void populateOrderListView(int page){
        Response.Listener<String> listener = response -> {
            try {
                orders.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                orders = gson.fromJson(String.valueOf(jsonArray), type);
                orderAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, orders);
                orderList.setAdapter(orderAdapter);
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

    /**
     * Progressbar update method, check the status payment and give a graphical visualization
     * @param lastLog the last status log
     */
    public void progressSet(Invoice.Status lastLog){
        switch (lastLog) {
            case WAITING_CONFIRMATION:
                progress.setProgress(0);
                labelStatus1.setTextColor(getResources().getColor(R.color.black));
                labelStatus2.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus3.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus4.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus5.setTextColor(getResources().getColor(R.color.cardview_dark_background));
            case ON_PROGRESS:
                progress.setProgress(25);
                labelStatus1.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus2.setTextColor(getResources().getColor(R.color.black));
                labelStatus3.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus4.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus5.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                break;
            case ON_DELIVERY:
                progress.setProgress(50);
                labelStatus1.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus2.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus3.setTextColor(getResources().getColor(R.color.black));
                labelStatus4.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus5.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                break;
            case DELIVERED:
                progress.setProgress(75);
                labelStatus1.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus2.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus3.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus4.setTextColor(getResources().getColor(R.color.black));
                labelStatus5.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                break;
            case FINISHED:
                progress.setProgress(100);
                labelStatus1.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus2.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus3.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus4.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus5.setTextColor(getResources().getColor(R.color.black));
                break;
            default:
                progress.setProgress(0);
                labelStatus1.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus2.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus3.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus4.setTextColor(getResources().getColor(R.color.cardview_dark_background));
                labelStatus5.setTextColor(getResources().getColor(R.color.cardview_dark_background));
        }
    }
}