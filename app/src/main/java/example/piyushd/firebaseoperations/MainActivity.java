package example.piyushd.firebaseoperations;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   public Button Insert, updtae, delete,show;
    public EditText Name,Roll,email;

    public DatabaseReference mDatabase;
    public FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Insert= (Button)findViewById(R.id.insert);
        updtae= (Button)findViewById(R.id.update);
        delete = (Button)findViewById(R.id.delete);
        show= (Button)findViewById(R.id.showdata);

        Name = (EditText)findViewById(R.id.Name) ;
        Roll= (EditText)findViewById((R.id.Roll)) ;
        email= (EditText)findViewById((R.id.email)) ;

        final Map<String, String> datamap = new HashMap<String, String>();







Insert.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String name = Name.getText().toString();
        String roll = Roll.getText().toString();
        String Email = email.getText().toString();

        datamap.put("Name",name);
        datamap.put("Roll", roll);
        datamap.put("Email",Email);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Students").child(name);

        mDatabase.setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_LONG).show();
            }
        });

    }
});

    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String name = Name.getText().toString();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
            mDatabase.child(name).setValue(null);
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();


        }
    });

    updtae.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String em = email.getText().toString();
            String ro = Roll.getText().toString();
            String na = Name.getText().toString();

            datamap.put("Email",em);
            datamap.put("Roll",ro);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Students").child(na);
         mDatabase.child("Email").setValue(em);
            mDatabase.child("Roll").setValue(ro);

        }
    });


    show.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final String name = Name.getText().toString();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
            //mDatabase.child(name);

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String val = (String) dataSnapshot.child(name).child("Email").getValue();
                    email.setText(val);

                    String ro = (String) dataSnapshot.child(name).child("Roll").getValue();
                    Roll.setText(ro);

                    Toast.makeText(getApplicationContext(),"Data Found",Toast.LENGTH_LONG).show();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                        Toast.makeText(getApplicationContext(),"Error in Fetching",Toast.LENGTH_LONG).show();
                }
            });
        }
    });




    }
}
