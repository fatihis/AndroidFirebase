package com.mcbu.demoders;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText userName,userPass1,userPass2,userEmail;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.userName);
        userPass1 = findViewById(R.id.userPass1);
        userPass2 = findViewById(R.id.userPass2);
        userEmail = findViewById(R.id.userEmail);
        signUpButton = findViewById(R.id.button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kayıt olma aşaması
                final String mail = userEmail.getText().toString();
                String pass = userPass1.getText().toString();
                final String username = userName.getText().toString();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userId = task.getResult().getUser().getUid();

                            DocumentReference doc= FirebaseFirestore.getInstance().collection("Users").document(userId);
                            WriteBatch batch = FirebaseFirestore.getInstance().batch();
                            Map data = new HashMap<String,String>();
                            data.put("userName",username);
                            data.put("userEmail",mail);
                            batch.set(doc,data).commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("Auth result","Süreç başladı");
                                    if(task.isSuccessful()){
                                        Log.d("Auth result","Süreç tamamlandı.");
                                    Intent mainToSecond = new Intent(MainActivity.this,SecondActivity.class);
                                    startActivity(mainToSecond);
                                    MainActivity.this.finish();
                                    }
                                    else{
                                        Log.d("Auth result","Süreç tamamlanamadı");
                                    }
                                }
                            });

                        } else {
                            Log.d("Auth process","Hata var");
                        }
                    }
                });
            }
        });

    }
}
