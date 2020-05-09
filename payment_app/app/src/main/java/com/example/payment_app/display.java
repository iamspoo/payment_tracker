package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class display extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseUser user;
    adapter detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final String key = getIntent().getStringExtra("key");
        database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        String uid=user.getUid().toString();
        mRef = database.getReference().child(uid).child(key);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                detail = datasnapshot.getValue(adapter.class);
                String repeat;
                String s=detail.getDmy();
                String str;
                if("No repetition".equals(s)) {
                    repeat = s;
                    //str="Name: "+detail.getName()+"\n"+"Description: "+detail.getDescription()+"\n"+"Start date: "+detail.getDate()+"\n"+"End Date: "+detail.getEdate()+"\n";
                }
                else{
                        repeat="Repeats every "+detail.getPeriod()+" "+s;
                }
                str="Description: "+detail.getDescription()+"\n"+"Start date: "+detail.getDate()+"\n"+"End Date: "+detail.getEdate()+"\n"+repeat+"\n";
                String name=detail.getName();
                TextView title=findViewById(R.id.title);
                TextView t=findViewById(R.id.desc);
                title.setText(name);
                t.setText(str);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(display.this, "Value not displayed", Toast.LENGTH_LONG).show();

            }

        });

    }
}
