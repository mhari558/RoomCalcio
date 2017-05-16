package calcio.com.room_calcio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import calcio.com.room_calcio.activities.CommonActivity;
import calcio.com.room_calcio.activities.LogInActivity;
import calcio.com.room_calcio.utils.Const;
import calcio.com.room_calcio.utils.Utils;

public class SplashActivity extends CommonActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("DATA",""+Utils.isUserLoggedIn(SplashActivity.this)+"<==>"+Utils.getStringFromSharedPreferences(SplashActivity.this, Const.USERID));
                if (Utils.isUserLoggedIn(SplashActivity.this)) {
                    launchHome();
                } else {
                    launchLogin();
                }
            }
        }, 2000);
    }

    private void launchHome() {
        Intent intent = new Intent(SplashActivity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    private void launchLogin() {
        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
