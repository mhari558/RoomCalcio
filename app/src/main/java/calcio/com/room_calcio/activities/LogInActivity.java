package calcio.com.room_calcio.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import calcio.com.room_calcio.Main2Activity;
import calcio.com.room_calcio.R;
import calcio.com.room_calcio.utils.Const;
import calcio.com.room_calcio.utils.Utils;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by hari on 27/7/16.
 */
@ContentView(R.layout.activity_log_in)
public class LogInActivity extends RoboActivity implements View.OnClickListener, View.OnKeyListener {

    @InjectView(R.id.loginSubmit)
    private Button mBtnLoginSubmit;
    @InjectView(R.id.loginForgotpassword)
    private TextView mTxtForgotPassword;
    @InjectView(R.id.loginEmail)
    private EditText mEdtEmail;
    @InjectView(R.id.loginPassword)
    private EditText mEdtPassword;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp( this, Const.APPLICATION_ID, Const.APPLICATION_SECRET_KEY, Const.VERSION);
        //initUi();
        listeners();
    }

    private void listeners() {

        mBtnLoginSubmit.setOnClickListener(this);
        mTxtForgotPassword.setOnClickListener(this);
        mEdtPassword.setOnKeyListener(this);
    }

    private void initUi() {


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mBtnLoginSubmit = (Button) findViewById(R.id.loginSubmit);
        mEdtEmail = (EditText) findViewById(R.id.loginEmail);
        mEdtPassword = (EditText) findViewById(R.id.loginPassword);
        mTxtForgotPassword = (TextView) findViewById(R.id.loginForgotpassword);
    }

    @Override
    public void onClick(View view) {

        if (view == mBtnLoginSubmit) {

            Log.e("INNNN","INNNNNNNNNNNNNNN");
            Utils.showProgressBar(this,"Please wait...");
            signInRequest();

        } else if (view == mTxtForgotPassword) {

            forgotPassword();

        }
    }

    private void signInRequest() {
        if (validate()) {
           Backendless.UserService.login(mEdtEmail.getText().toString(), mEdtPassword.getText().toString(), new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser backendlessUser) {

                    Utils.createSession(LogInActivity.this,mEdtEmail.getText().toString(),mEdtPassword.getText().toString());
                    // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    Utils.hideProgressBar();
                    Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    Utils.hideProgressBar();
                   // Toast.makeText(getApplicationContext(),backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();
                    if(Integer.parseInt(backendlessFault.getCode()) == 3003) {
                        Toast.makeText(getApplicationContext(),backendlessFault.getMessage()+""+getResources().getString(R.string.not_existed), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),backendlessFault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void forgotPassword() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.forgotTitle);
        final EditText mEdtEmailId = new EditText(this);
        builder.setView(mEdtEmailId);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Utils.showProgressBar(LogInActivity.this,"Please wait...");
                passwordRequest(mEdtEmailId.getText().toString());
                dialogInterface.dismiss();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        }).create().show();

    }

    private void passwordRequest(String email) {
        Backendless.UserService.restorePassword( email.trim().toString(), new AsyncCallback<Void>()
        {
            public void handleResponse( Void response )
            {

                Utils.hideProgressBar();
                // Backendless has completed the operation - an email has been sent to the user
                Toast.makeText(LogInActivity.this,"An email has been sent to the user", Toast.LENGTH_SHORT).show();
            }

            public void handleFault( BackendlessFault fault )
            {
                // password revovery failed, to get the error code call fault.getCode()
                Toast.makeText(LogInActivity.this,"Failed"+fault.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private boolean validate() {

        if (!Utils.isValidEmail(mEdtEmail.getText().toString())) {
            Utils.hideProgressBar();
          //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_phone), Toast.LENGTH_SHORT).show();
            mEdtEmail.requestFocus();
            return false;
        } else if (!Utils.isValidPassword(mEdtPassword.getText().toString())) {
            Utils.hideProgressBar();
          //  Toast.makeText(getApplicationContext(), getResources().getString(R.string.valid_password), Toast.LENGTH_SHORT).show();
            mEdtPassword.requestFocus();
            return false;
        } else {

            return true;
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_ENTER:

                    signInRequest();

                    return true;
                default:
                    break;
            }
        }

        return false;
    }
}
