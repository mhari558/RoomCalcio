package calcio.com.room_calcio.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import calcio.com.room_calcio.R;
import calcio.com.room_calcio.database.DbHelper;


/**
 * Created by hari on 9/6/16.
 */
public class TotalDetailsFragment extends Fragment {


    DbHelper dbHelper;
    private int rows, columns;
    private ArrayList<String> columnsData;
    private View view;
    private Cursor cursor;
    private TableLayout tableLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_totaldetails, container, false);

        tableLayout =  (TableLayout) view.findViewById(R.id.tableLayout);
        dbHelper = new DbHelper(getActivity());
        cursor = dbHelper.getAllRecords();
        int count = dbHelper.getIndividualColumnCount("meera");

        columnsData = dbHelper.getAllColumns("fragment");

        rows = cursor.getCount();
        columns = cursor.getColumnCount();
        cursor.moveToFirst();
        createColumnsRow();
        createTableRowsAndColumnData();
        addingTotalRow();

        return view;
    }

    private void createTableRowsAndColumnData() {
        // outer for loop
        for (int i = 0; i < rows; i++) {

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            View inLine = new View(getActivity());
            inLine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            inLine.setBackgroundColor(Color.rgb(255, 0, 0));

            //  ((TableLayout) view.findViewById(R.id.tableLayout)).addView(line1);
            // inner for loop
            for (int j = 0; j < columns; j++) {

                TextView tv = new TextView(getActivity());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                // tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);
                tv.setText(cursor.getString(j));
                View verticleLine = new View(getActivity());
                verticleLine.setLayoutParams(new TableRow.LayoutParams(1,
                        TableRow.LayoutParams.MATCH_PARENT));
                verticleLine.setBackgroundColor(Color.rgb(255, 0, 0));
                row.addView(verticleLine);
                row.addView(tv);

            }
            cursor.moveToNext();
            tableLayout.addView(inLine);
            tableLayout.addView(row);


        }

    }

    private void createColumnsRow() {

        TableRow columnRow = new TableRow(getActivity());
        columnRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        columnRow.setBackgroundColor(Color.DKGRAY);
        View line = new View(getActivity());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(Color.rgb(255, 0, 0));

        for (int j = 0; j < columnsData.size(); j++) {

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // tv.setBackgroundResource(R.drawable.cell_shape);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(10, 10, 10, 10);
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);
            //tv.setLayout
            tv.setText(columnsData.get(j));

            View verticleLine = new View(getActivity());
            verticleLine.setLayoutParams(new TableRow.LayoutParams(1,
                    TableRow.LayoutParams.MATCH_PARENT));
            verticleLine.setBackgroundColor(Color.rgb(255, 0, 0));
            columnRow.addView(verticleLine);
            columnRow.addView(tv);
        }

        tableLayout.addView(columnRow);
        tableLayout.addView(line);
    }

    private void addingTotalRow() {

        TableRow row1 = new TableRow(getActivity());
        row1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        row1.setBackgroundColor(Color.DKGRAY);

        View line = new View(getActivity());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(Color.rgb(255, 0, 0));

        for (int j = 0; j < columnsData.size(); j++) {

            TextView tv = new TextView(getActivity());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // tv.setBackgroundResource(R.drawable.cell_shape);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(10, 10, 10, 10);
            tv.setTextColor(Color.WHITE);
            tv.setTypeface(null, Typeface.BOLD);

            if (j >= 2) {
                tv.setText(String.valueOf(dbHelper.getIndividualColumnCount(columnsData.get(j))));
            } else if (j == 1) {

                tv.setText("Grand Total:\t");
            }
            View verticleLine = new View(getActivity());
            verticleLine.setLayoutParams(new TableRow.LayoutParams(1,
                    TableRow.LayoutParams.MATCH_PARENT));
            verticleLine.setBackgroundColor(Color.rgb(255, 0, 0));
            row1.addView(verticleLine);
            row1.addView(tv);
        }

        tableLayout.addView(line);
        //  }
        //((TableLayout) view.findViewById(R.id.tableLayout)).addView(line);
        tableLayout.addView(row1);
        //((TableLayout) view.findViewById(R.id.tableLayout)).addView(line);
        cursor.close();
    }

}
