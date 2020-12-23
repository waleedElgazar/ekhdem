package com.example.ekhdem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ekhdem.model.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone=findViewById(R.id.phoneText);
        password=findViewById(R.id.passwordText);

    }

    public void loginCheck(View view){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference table_user=database.getReference("user");
        ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait...");
        dialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get data from firebase
                dialog.dismiss();
                if(snapshot.child(phone.getText().toString()).exists()){
                    user user=snapshot.child(phone.getText().toString()).getValue(com.example.ekhdem.model.user.class);
                    if (user.getPassword().equals(password.getText().toString())){
                        Toast.makeText(MainActivity.this , "login succes" , Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this,"this phone number hasn't been found",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void signUp(View v){
        Intent intent=new Intent(MainActivity.this,SignUp.class);
        startActivity(intent);
    }

}