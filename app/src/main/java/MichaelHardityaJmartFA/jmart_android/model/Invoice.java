package MichaelHardityaJmartFA.jmart_android.model;

import java.util.Date;

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
