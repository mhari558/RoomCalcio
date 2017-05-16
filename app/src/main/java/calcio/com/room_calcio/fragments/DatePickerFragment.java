package calcio.com.room_calcio.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import calcio.com.room_calcio.R;


/**
 * Created by hari on 8/6/16.
 */

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
           // TextView tv1= (TextView) getActivity().findViewById(R.id.textview1);
            ((Button)getActivity().findViewById(R.id.chooseDate)).setText("Year: "+view.getYear()+" Month: "+view.getMonth()+" Day: "+view.getDayOfMonth());
            ((Button)getActivity().findViewById(R.id.chooseDate)).setTag(view.getDayOfMonth()+"-"+view.getMonth()+"-"+view.getYear());

        }

}
