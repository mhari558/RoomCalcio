package calcio.com.room_calcio.models;

/**
 * Created by hari on 10/6/16.
 */
public class RecordsModel {
    private String totalAmount;

    public String getShareAmount() {
        return shareAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setShareAmount(String shareAmount) {
        this.shareAmount = shareAmount;
    }

    private String shareAmount;
    private String date;

}