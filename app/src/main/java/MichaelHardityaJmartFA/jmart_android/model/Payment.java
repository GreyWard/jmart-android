package MichaelHardityaJmartFA.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

public class Payment extends Invoice{
    public ArrayList<Record> history= new ArrayList<Record>();
    public Shipment shipment;
    public int productCount;
    public String productName;

    @Override
    public String toString() {
        return productName + " " + productCount + " " + status + " " + date.toString();
    }

    public class Record {
        public Date date;
        public String message;
        public Invoice.Status status;

        @Override
        public String toString() {
            return  " "+ status + " " + date;
        }
    }
}
