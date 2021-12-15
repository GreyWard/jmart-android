package MichaelHardityaJmartFA.jmart_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;

import MichaelHardityaJmartFA.jmart_android.model.Invoice;
import MichaelHardityaJmartFA.jmart_android.model.Payment;
import MichaelHardityaJmartFA.jmart_android.model.Plans;
import MichaelHardityaJmartFA.jmart_android.request.PaymentCancelRequest;
import MichaelHardityaJmartFA.jmart_android.request.RequestFactory;

/**
 * Invoice History activity, shows orders and payments history
 */
public class InvoiceHistoryActivity extends AppCompatActivity {
    private ListView invoicesList;
    private  ListView statusList;
    private ProgressBar progress;
    private ArrayAdapter<Payment> invoiceAdapter;
    private ArrayAdapter<String> statusAdapter;
    private static ArrayList<Payment> payments = new ArrayList<>();
    private static final ArrayList<String> paymentStatus = new ArrayList<>();
    private static final Gson gson = new Gson();
    private int selectedPaymentId;
    private TextView labelStatus1;
    private TextView labelStatus2;
    private TextView labelStatus3;
    private TextView labelStatus4;
    private TextView labelStatus5;
    private TextView invoiceId;
    private TextView productId;
    private TextView productCount;
    private TextView shipmentPlan;
    private TextView destination;
    private TextView message;
    private CardView invoiceDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);
        invoicesList = findViewById(R.id.invoice_listview);
        statusList = findViewById(R.id.status_listview);
        progress = findViewById(R.id.invoice_progressbar);
        invoiceId = findViewById(R.id.invoice_id);
        productId = findViewById(R.id.invoice_product_id);
        productCount = findViewById(R.id.invoice_product_count);
        shipmentPlan = findViewById(R.id.invoice_product_shipmentplan);
        destination = findViewById(R.id.invoice_destination);
        message = findViewById(R.id.invoice_status_message);
        invoiceDetail = findViewById(R.id.invoice_status_detail);
        labelStatus1 = findViewById(R.id.label_status_confirmation);
        labelStatus2 = findViewById(R.id.label_status_on_progress);
        labelStatus3 = findViewById(R.id.label_status_on_delivery);
        labelStatus4 = findViewById(R.id.label_status_delivered);
        labelStatus5 = findViewById(R.id.label_status_finished);
        Button back = findViewById(R.id.button_back_to_invoice_list);
        Button cancel = findViewById(R.id.button_cancel_invoice);
        Button receipt = findViewById(R.id.button_receipt_activity);
        TextView labelBlank = findViewById(R.id.label_blank_invoice);
        statusAdapter = new ArrayAdapter<>(getApplicationContext(),R.layout.listview,paymentStatus);
        resetDetails();
        Response.Listener<String> listener = response -> {
            try {
                payments.clear();
                JSONArray jsonArray = new JSONArray(response);
                Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                payments = gson.fromJson(String.valueOf(jsonArray), type);
                invoiceAdapter = new ArrayAdapter<Payment>(getApplicationContext(),R.layout.listview, payments);
                invoicesList.setAdapter(invoiceAdapter);
                Toast.makeText(InvoiceHistoryActivity.this, "Payment History loaded", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(InvoiceHistoryActivity.this, "Filter failed", Toast.LENGTH_LONG).show();
            }
        };
        try{
            Response.ErrorListener errorListener = error -> Toast.makeText(InvoiceHistoryActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
            StringRequest prodReq = RequestFactory.getPayment(LoginActivity.getLoggedAccount().id,0,20,listener,errorListener);
            RequestQueue queues = Volley.newRequestQueue(InvoiceHistoryActivity.this);
            queues.add(prodReq);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(InvoiceHistoryActivity.this, "Please input all the necessary fields!",Toast.LENGTH_LONG).show();
        }
        if(payments.size() == 0){
            labelBlank.setVisibility(View.VISIBLE);
        }
        invoicesList.setOnItemClickListener((parent, view, position, id) -> {
            invoiceDetail.setVisibility(View.VISIBLE);
            selectedPaymentId = payments.get(position).id;
            invoiceId.setText(String.valueOf(selectedPaymentId));
            productId.setText(String.valueOf(payments.get(position).productId));
            productCount.setText(String.valueOf(payments.get(position).productCount));
            shipmentPlan.setText(Plans.check(payments.get(position).shipment.plan));
            destination.setText(payments.get(position).shipment.address);
            message.setText(payments.get(position).history.get(payments.get(position).history.size()-1).message);
            progressSet(payments.get(position).status);
            if (payments.get(position).status == Invoice.Status.WAITING_CONFIRMATION){
                cancel.setClickable(true);
                cancel.setVisibility(View.VISIBLE);
            }else{
                cancel.setClickable(false);
                cancel.setVisibility(View.GONE);
            }
            populateStatusList(payments.get(position).history);
        });
        back.setOnClickListener(v -> {
            invoiceDetail.setVisibility(View.GONE);
            resetDetails();
        });
        cancel.setOnClickListener(v -> cancelPayment());

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
                labelStatus2.setTextColor(Color.GRAY);
                labelStatus3.setTextColor(Color.GRAY);
                labelStatus4.setTextColor(Color.GRAY);
                labelStatus5.setTextColor(Color.GRAY);
            case ON_PROGRESS:
                progress.setProgress(25);
                labelStatus1.setTextColor(Color.GRAY);
                labelStatus2.setTextColor(getResources().getColor(R.color.black));
                labelStatus3.setTextColor(Color.GRAY);
                labelStatus4.setTextColor(Color.GRAY);
                labelStatus5.setTextColor(Color.GRAY);
                break;
            case ON_DELIVERY:
                progress.setProgress(50);
                labelStatus1.setTextColor(Color.GRAY);
                labelStatus2.setTextColor(Color.GRAY);
                labelStatus3.setTextColor(getResources().getColor(R.color.black));
                labelStatus4.setTextColor(Color.GRAY);
                labelStatus5.setTextColor(Color.GRAY);
                break;
            case DELIVERED:
                progress.setProgress(75);
                labelStatus1.setTextColor(Color.GRAY);
                labelStatus2.setTextColor(Color.GRAY);
                labelStatus3.setTextColor(Color.GRAY);
                labelStatus4.setTextColor(getResources().getColor(R.color.black));
                labelStatus5.setTextColor(Color.GRAY);
                break;
            case FINISHED:
                progress.setProgress(100);
                labelStatus1.setTextColor(Color.GRAY);
                labelStatus2.setTextColor(Color.GRAY);
                labelStatus3.setTextColor(Color.GRAY);
                labelStatus4.setTextColor(Color.GRAY);
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

    /**
     * Reset the textviews in invoice detail activity
     */
    public void resetDetails(){
        invoiceId.setText("");
        productId.setText("0");
        productCount.setText("0");
        shipmentPlan.setText("");
        destination.setText("");
        message.setText("");
    }

    /**
     * Cancelling payment method, used to call PaymentCancelRequest
     */
    public void cancelPayment(){
        Response.Listener<String> listener = response -> {
            Toast.makeText(InvoiceHistoryActivity.this, "Pemesanan Berhasil di Cancel!", Toast.LENGTH_LONG).show();
            finish();
        };
        Response.ErrorListener errorListener = error -> Toast.makeText(InvoiceHistoryActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
        PaymentCancelRequest cancelReq = new PaymentCancelRequest(selectedPaymentId, listener, errorListener);
        RequestQueue queues = Volley.newRequestQueue(InvoiceHistoryActivity.this);
        queues.add(cancelReq);
    }

    /**
     * Populate status listview, updates the status listview of the payment
     * @param history arraylist history of the payment
     */
    public void populateStatusList(ArrayList<Payment.Record> history){
        paymentStatus.clear();
        for (int i = 0; i < history.size(); i++){
            paymentStatus.add(history.get(i).status.toString()+"    "+ DateFormat.getDateTimeInstance().format(history.get(i).date));
        }
        statusList.setAdapter(statusAdapter);
    }
}