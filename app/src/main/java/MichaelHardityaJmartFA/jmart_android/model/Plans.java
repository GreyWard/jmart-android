package MichaelHardityaJmartFA.jmart_android.model;

/**
 * Plans class, store the shipment plan method using byte type as representation
 */
public class Plans {
    public static byte INSTANT = (byte)0;
    public static byte SAME_DAY = (byte)1;
    public static byte NEXT_DAY = (byte)2;
    public static byte REGULER = (byte)4;
    public static byte KARGO = (byte)8;

    /**
     * Convert bits of byte to their representation of the shipment plan in string
     * @param bits the representative byte
     * @return String of shipment plan
     */
    public static String check(byte bits){
        String back;
        switch (bits){
            case 0:
                back = "INSTANT";
                break;
            case 1:
                back = "SAME DAY";
                break;
            case 2:
                back = "NEXT DAY";
                break;
            case 4:
                back = "REGULER";
                break;
            case 8:
                back = "KARGO";
                break;
            default:
                back = "Not Defined";
                break;
        }
        return back;
    }
}
