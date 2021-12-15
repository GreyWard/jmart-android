package MichaelHardityaJmartFA.jmart_android.model;

import java.util.Date;

/**
 * Invoice class, stores invoice information such as identifiers (buyer, product, complaint, and invoice), rating, date, and status.
 */
public abstract class Invoice extends Serializable {
    enum Rating {
        NONE,
        BAD,
        NEUTRAL,
        GOOD
    }

    public enum Status {
        WAITING_CONFIRMATION,
        CANCELLED,
        ON_PROGRESS,
        ON_DELIVERY,
        DELIVERED,
        COMPLAINT,
        FINISHED,
        FAILED
    }

    public Date date;
    public int buyerId;
    public int productId;
    public int complaintId;
    public Rating rating;
    public Status status;
}
