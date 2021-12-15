package MichaelHardityaJmartFA.jmart_android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Payment class, stores payment information including invoice, history, shipment, product count and name
 */
public class Payment extends Invoice{
    public ArrayList<Record> history= new ArrayList<Record>();
    public Shipment shipment;
    public int productCount;
    public String productName;

    /**
     * Get payment informations (product name, count, status, and date) separated by space
     * @return String of the information
     */
    @Override
    public String toString() {
        return productName + " " + productCount + " " + status + " " + date.toString();
    }

    /**
     * Record class, stores date, message, and status
     */
    public class Record {
        public Date date;
        public String message;
        public Invoice.Status status;

        /**
         * Get Record informations(status and date) seperated by space
         * @return String of the information
         */
        @Override
        public String toString() {
            return  " "+ status + " " + date;
        }
    }
}
