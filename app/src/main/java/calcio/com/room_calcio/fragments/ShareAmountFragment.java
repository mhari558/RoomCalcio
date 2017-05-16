package calcio.com.room_calcio.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import calcio.com.room_calcio.R;
import calcio.com.room_calcio.activities.SearchActivity;
import calcio.com.room_calcio.database.DbHelper;
import calcio.com.room_calcio.models.BeanModel;
import calcio.com.room_calcio.utils.Const;
import calcio.com.room_calcio.utils.Utils;


public class ShareAmountFragment extends Fragment implements View.OnClickListener {

    public DbHelper dbHelper;
    private EditText amount;
    private Button addMembers, chooseDate, submit;
    private ArrayList<String> mSelectedItems, users;
    private Calendar calendar;
    private DatePicker datePicker;
    private int year, month, day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);

        dbHelper = new DbHelper(getActivity());
        users = dbHelper.getAllColumns("main");
        amount = (EditText) view.findViewById(R.id.amount);
        addMembers = (Button) view.findViewById(R.id.addMembers);
        chooseDate = (Button) view.findViewById(R.id.chooseDate);
        submit = (Button) view.findViewById(R.id.submit);
        addMembers.setOnClickListener(this);
        chooseDate.setOnClickListener(this);
        submit.setOnClickListener(this);
        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHelper = new DbHelper(this);
        users = dbHelper.getAllColumns("main");
        amount = (EditText) findViewById(R.id.amount);
        addMembers = (Button) findViewById(R.id.addMembers);
        chooseDate = (Button) findViewById(R.id.chooseDate);
        submit = (Button) findViewById(R.id.submit);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        showData = (Button)findViewById(R.id.showData);

        addMembers.setOnClickListener(this);
        chooseDate.setOnClickListener(this);
        submit.setOnClickListener(this);
        fab.setOnClickListener(this);
        showData.setOnClickListener(this);
      *//* FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.layout.content_main,searchDetailsFragment);
        transaction.commit();*//*

       *//* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });*//*

    }*/

    @Override
    public void onClick(View view) {

        if (view == chooseDate) {

            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");

        } else if (view == submit) {

            if (validate()) {
                calculateAndSplitAmount();
            }
        } else if (view == addMembers) {
            popUpCreation(users);
        }

    }

    private boolean validate() {

        if (amount.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(getActivity(), "Please enter Amount", Toast.LENGTH_SHORT).show();
            return false;

        }/*else if(chooseDate.getText().toString().equalsIgnoreCase("")){

            Toast.makeText(getApplicationContext(),"Please choose Date",Toast.LENGTH_SHORT).show();
            return false;

        }*/ else {
            return true;
        }
    }

    private void popUpMenuCreation(FloatingActionButton btn) {

        PopupMenu menu = new PopupMenu(getActivity(), btn);
        MenuInflater inflater = new MenuInflater(getActivity());
        inflater.inflate(R.menu.menuitmes, menu.getMenu());
        menu.show();
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Toast.makeText(getActivity(), "You Clicked : " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    private void popUpCreation(final ArrayList<String> data) {

        mSelectedItems = new ArrayList();
        final CharSequence[] cs = data.toArray(new CharSequence[data.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Members");

        builder.setMultiChoiceItems(cs, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                if (isChecked) {
                    // If the user checked the item, add it to the selected items
                    mSelectedItems.add(String.valueOf(cs[which]));

                } else if (mSelectedItems.contains(String.valueOf(cs[which]))) {
                    // Else, if the item is already in the array, remove it
                    mSelectedItems.remove(String.valueOf(cs[which]));
                }
            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                StringBuilder message = new StringBuilder();
                for (int i = 0; i < mSelectedItems.size(); i++) {
                    message.append(mSelectedItems.get(i) + "\t\t");
                }
                addMembers.setText(message.toString());
                dialog.dismiss();
            }
        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    private void calculateAndSplitAmount() {

        int total = Integer.parseInt(amount.getText().toString());
        int membersCount = mSelectedItems.size();
        try {
            int shareAmount = total / membersCount;
            confirmDialog(total, shareAmount);
        } catch (ArithmeticException ex) {
            ex.printStackTrace();
        }

    }

    private void confirmDialog(final int total, final int shareAmount) {


        String str = addMembers.getText().toString() + "\n\n" + "Are you really sure to add! Please click 'OK' or 'Cancel'";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("You Selected following Members!");
        builder.setMessage(str);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



               /* if(success){
                    Toast.makeText(getActivity(),"Success! Your Data saved..",Toast.LENGTH_SHORT).show();
                    amount.setText("");
                    chooseDate.setText("Choose Date");
                    chooseDate.setTag("");
                    mSelectedItems.clear();
                    addMembers.setText("Select Members to Add");

                }else {
                    Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
                }*/

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Split amount========>" + shareAmount);
                String str = (String) chooseDate.getTag();
                StringBuilder builder1 = new StringBuilder();
                for (int k = 0; k < mSelectedItems.size(); k++) {

                    builder1.append(mSelectedItems.get(k) + "_");
                }
                HashMap contact = new HashMap();
                contact.put( "date", "Jack Daniels" );
                contact.put( "shareAmount", shareAmount);
                contact.put( "totalAmount", total);
              //  contact.put( "totalAmount", total);
                //contact.put( "title", "Favorites" );
                // BeanModel users = new BeanModel(str, shareAmount, total,"", mSelectedItems);
                BeanModel bean = new BeanModel();
                bean.setDate(str);
                bean.setShareAmount(shareAmount);
                bean.setTotalAmount(total);
                //bean.setSharedUsers(mSelectedItems);
                bean.setUsers("");

                // save object synchronously
               // Contact savedContact = Backendless.Persistence.save( contact );

                // save object synchronously
             //   BeanModel savedContact = Backendless.Persistence.save( bean );
                // save object synchronously
                Map savedContact = Backendless.Persistence.of( "Record" ).save( contact );

              Backendless.Persistence.of("Record").save(savedContact, new AsyncCallback<Map>() {
                  @Override
                  public void handleResponse(Map map) {

                      Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();

                  }

                  @Override
                  public void handleFault(BackendlessFault backendlessFault) {
                      Log.e("Errror",backendlessFault.getCode());
                      Toast.makeText(getActivity(),"Success"+backendlessFault.getMessage(),Toast.LENGTH_SHORT).show();
                  }
              });


            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = new MenuInflater(getActivity());
        inflater.inflate(R.menu.menuitmes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.getAllRecords) {

            Intent it = new Intent(getActivity(), SearchActivity.class);
            startActivity(it);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
