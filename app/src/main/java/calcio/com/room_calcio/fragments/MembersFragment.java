package calcio.com.room_calcio.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import calcio.com.room_calcio.Main2Activity;
import calcio.com.room_calcio.R;

/**
 * Created by hari on 21/6/16.
 */
public class MembersFragment extends Fragment {

    private Button addMember,removeMember;
    private ListView membersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_members,container,false);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                String name = dataSnapshot.child("Members").getValue(String.class);
              //  String rsvp = dataSnapshot.child("rsvp").getValue(String.class);
                Toast.makeText(getActivity(),""+name,Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
       // ((Main2Activity)getActivity()).database.getReference().addValueEventListener(postListener);

        membersList = (ListView) view.findViewById(R.id.listView2);
        addMember = (Button) view.findViewById(R.id.addMember);
        removeMember = (Button) view.findViewById(R.id.removeMember);
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createDialog();

            }
        });

        return view;
    }

    private void createDialog() {


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Select Members");

        final EditText editText =  new EditText(getActivity());

        builder.setView(editText).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String memberName = editText.getText().toString();
             //   ((NavigationActivity)getActivity()).database.getReference().child("Members").push().setValue(memberName);
                dialogInterface.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        }).create().show();
    }
}
