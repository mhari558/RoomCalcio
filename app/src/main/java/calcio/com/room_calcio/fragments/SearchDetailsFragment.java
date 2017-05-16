package calcio.com.room_calcio.fragments;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import calcio.com.room_calcio.R;
import calcio.com.room_calcio.adapter.SearchAdapter;
import calcio.com.room_calcio.database.DbHelper;
import calcio.com.room_calcio.models.RecordsModel;


/**
 * Created by hari on 9/6/16.
 */
public class SearchDetailsFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    DbHelper dbHelper;
    private int rows, columns;
    private ArrayList<String> columnsData;
    private View view;
    private ArrayList<RecordsModel> records;
    private ListView listView;
    private Spinner spinner;
    private TextView name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_search,container,false);

        listView =  (ListView) view.findViewById(R.id.listView);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        name = ((TextView)view.findViewById(R.id.name));

        dbHelper = new DbHelper(getActivity());
        columnsData = dbHelper.getAllColumns("");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, columnsData);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        ((TextView) view).setTextColor(Color.WHITE);
        ((TextView) view).setTextSize(30);
        ((TextView) view).setTypeface(Typeface.DEFAULT_BOLD);
        String item = adapterView.getItemAtPosition(i).toString();
        name.setText(item);
        createAndUpdateListview(item);
        // Showing selected spinner item
        Toast.makeText(getActivity(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    private void createAndUpdateListview(String name) {

        records = dbHelper.getAllRecords(name);
        SearchAdapter searchAdapter= new SearchAdapter(getActivity(),records);
        listView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
