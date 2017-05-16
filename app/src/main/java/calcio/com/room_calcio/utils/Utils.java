package calcio.com.room_calcio.utils;

/**
 * @author kiran on 2/9/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;

import calcio.com.room_calcio.R;


/**
 * All Progress Dialogues, Alert Dialogues should be maintain here
 */
public class Utils {
    public static ProgressDialog mProgressDialog;
    private static SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "RoomCalcioPref";
    private static final String USERID = "userID";
    private static final String PASSWORD = "password";
    private static final String IS_LOGIN = "IS_LOGGED_IN";
    public static ProgressDialog showProgressBar(Context context, String msg) {
        hideProgressBar();

        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mProgressDialog;
    }

    public static ProgressDialog showProgressBar(Context context, String msg, boolean flag) {
        hideProgressBar();

        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(context, R.style.MyTheme);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
                mProgressDialog.setMessage(msg);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(flag);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mProgressDialog;
    }

    public static void hideProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mProgressDialog = null;
    }
    public static boolean isValidEmail(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }
    public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }
    public static boolean isValidPassword(CharSequence password) {
        if (password.toString().length() > 0) {
            return true;
        }
        return false;
    }

    public static String getStringFromSharedPreferences(Context context, String key){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key,"");
    }
    public static void putStringIntoSharedPreferences(Context context, String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static boolean isUserLoggedIn(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return  sharedPreferences.getBoolean(IS_LOGIN,false);
    }
    public static void createSession(Context context, String userID, String password){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERID,userID);
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(PASSWORD,password);
        editor.apply();
    }
}
