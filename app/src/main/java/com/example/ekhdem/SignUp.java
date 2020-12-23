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

public class SignUp extends AppCompatActivity {
    EditText phone,password,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        password=findViewById(R.id.passwordText);
        name=findViewById(R.id.nameText);
        phone=findViewById(R.id.phoneText);
    }

    public void createAccount(View view){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference table_user=database.getReference("user");
        ProgressDialog dialog=new ProgressDialog(SignUp.this);
        dialog.setMessage("please wait...");
        dialog.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // check if already found or not
                if (snapshot.child(phone.getText().toString()).exists()){
                    dialog.dismiss();
                    Toast.makeText(SignUp.this, "this phone number already Sign up", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    user  user1=new user(name.getText().toString(),password.getText().toString());
                    table_user.child(phone.getText().toString()).setValue(user1);
                    Toast.makeText(SignUp.this, "you have register succesffuly", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void login(View view){
        Intent intent=new Intent(SignUp.this,MainActivity.class);
        startActivity(intent);
    }
}