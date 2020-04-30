package com.example.payment_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.*;
import java.util.*;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationManager;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;

public class reminder extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mRef;
    FirebaseUser user;
    ListView liv;
    ArrayList<String> list;
    ArrayAdapter<String> aad;
    adapter fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        Button signout=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        liv=(ListView)findViewById(R.id.listv);
        list=new ArrayList<>();
        fuser=new adapter();
        aad=new ArrayAdapter<String>(this,R.layout.reminder_info,R.id.txtviw,list);
        lineup();
        notification();
        signout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Toast.makeText(reminder.this, "Signed out", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(reminder.this, test.class));
            }
        });

    }

    private void lineup() {
        database = FirebaseDatabase.getInstance();
        user=mAuth.getCurrentUser();
         String uid=user.getUid().toString();
        mRef = database.getReference().child(uid);

        mRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot datasnapshot) {
                for(DataSnapshot ds: datasnapshot.getChildren()) {
                    fuser = ds.getValue(adapter.class);
                    list.add("\nName :"+fuser.getName().toString()+"\n"+"For :"+fuser.getDescription().toString()+"\n"+"Date :"+fuser.getDate()+"\n".toString());
                }
                liv.setAdapter(aad);
                liv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                        Toast.makeText(reminder.this,"U clicked:"+position,Toast.LENGTH_LONG).show();
                    }
                });

            }

            public void onCancelled(DatabaseError dataerr) {
                Toast.makeText(reminder.this, "Value not displayed", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void notification(){
        Button btNotification=findViewById(R.id.Notification);
        btNotification.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message="Policy number 654321789";
                NotificationCompat.Builder builder=new NotificationCompat.Builder(
                        reminder.this
                ).setSmallIcon(R.drawable.notify).setContentTitle("Premium payment").setContentText(message).setAutoCancel(true);

                Intent intent= new Intent(reminder.this,notify.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message",message);
                PendingIntent penIn=PendingIntent.getActivity(reminder.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(penIn);
                NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                nm.notify(0,builder.build());
            }
        });
    }

    public void pop(View view){
        Intent intent = new Intent(this, popup.class);
        startActivity(intent);
    }



    

}
 /*String TAG="user";
        int a= getIntent().getIntExtra("uid",0);
        String u=Integer.toString(a);
        if( a != 0) {
            Log.i(TAG,u);
        }
        TextView t=findViewById(R.id.user);
        t.setText(u);
*/

