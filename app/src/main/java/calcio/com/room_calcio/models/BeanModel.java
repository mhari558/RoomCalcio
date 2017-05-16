package calcio.com.room_calcio.models;

import java.util.ArrayList;

/**
 * Created by hari on 16/6/16.
 */
public class BeanModel {
    private String objectId;
        private String date;
    private int shareAmount;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    private int totalAmount;
    private String users;
    private ArrayList<String> sharedUsers;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(int shareAmount) {
        this.shareAmount = shareAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public ArrayList<String> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(ArrayList<String> sharedUsers) {
        this.sharedUsers = sharedUsers;
    }

   /* public BeanModel(String date, int shareAmount, int totalAmount, String users, ArrayList<String> sharedUsers) {
            this.date = date;
            this.shareAmount = shareAmount;
            this.totalAmount = totalAmount;
            this.users = users;
            this.sharedUsers = sharedUsers;

        }*/
}


