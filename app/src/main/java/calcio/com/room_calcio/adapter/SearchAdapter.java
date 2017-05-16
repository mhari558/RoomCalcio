package calcio.com.room_calcio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import calcio.com.room_calcio.R;
import calcio.com.room_calcio.models.RecordsModel;


/**
 * Created by hari on 10/6/16.
 */
public class SearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<RecordsModel> records;

    public SearchAdapter(Context context, ArrayList<RecordsModel> records) {

        this.context = context;
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int i) {
        return records.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if(convertView ==  null ){

            view =  inflater.inflate(R.layout.custom_searchview,null);
        }else{
            view = convertView;
        }

        TextView  dateView  = (TextView) view.findViewById(R.id.dateView);
        TextView  shareAmount  = (TextView) view.findViewById(R.id.shareAmount);

        RecordsModel model = records.get(i);

       // Log.e("=========",model.getShareAmount());
     //   if(!model.getShareAmount().equalsIgnoreCase("")) {
            dateView.setText(model.getDate());
            shareAmount.setText(model.getShareAmount());
     //   }
        return view;
    }
}
