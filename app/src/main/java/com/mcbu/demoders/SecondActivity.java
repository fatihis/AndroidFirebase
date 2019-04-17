package com.mcbu.demoders;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SecondActivity extends AppCompatActivity {
    TextView userNameTV, userEmailTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        userEmailTV = findViewById(R.id.userNameTextView);
        userNameTV = findViewById(R.id.userTextView);
        getUserInfo();
    }
    public void getUserInfo(){
        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if(task.isSuccessful()){
                    Toast.makeText(SecondActivity.this,"OLDU",Toast.LENGTH_SHORT).show();

                    Log.d("Process","OLAYAZDI");
                    String userName = task.getResult().getString("userName");
                    String userEmail = task.getResult().getString("userEmail");
                    userNameTV.setText(userName);
                    userEmailTV.setText(userEmail);
                }

                if(!task.isSuccessful()){
                    Log.d("Process","OLMAYAYAZDI");
                    Log.d("Error",task.getException().getLocalizedMessage());
                    Toast.makeText(SecondActivity.this,"OLMADI",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
