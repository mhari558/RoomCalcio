package calcio.com.room_calcio.models;

/**
 * Created by hari on 17/6/16.
 */
public class DateBean {

    String dateID;
    String date;

    public String getDateID() {
        return dateID;
    }

    public void setDateID(String dateID) {
        this.dateID = dateID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DateBean(String dateID, String date) {
        this.dateID = dateID;
        this.date = date;
    }
}
