package calcio.com.room_calcio.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import calcio.com.room_calcio.R;


/**
 * Created by hari on 9/6/16.
 */
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_search);
        /*TotalDetailsFragment searchDetailsFragment =  new TotalDetailsFragment();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.linearLayout,searchDetailsFragment);
        transaction.commit();
*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =  new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.menuitmes,menu);
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

          /*  TotalDetailsFragment searchDetailsFragment =  new TotalDetailsFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.linearLayout,searchDetailsFragment);
            transaction.commit();
*/

            return true;
        }else if (id == R.id.nameWiseRecords) {

          /*  TotalDetailsFragment searchDetailsFragment =  new TotalDetailsFragment();
            android.app.FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.linearLayout,searchDetailsFragment);
            transaction.commit();*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
