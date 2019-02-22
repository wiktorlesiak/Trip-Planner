package com.trip.planner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {
    private EditText editName, editAge, editWeight, editHeight;
    private Button add;

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editHeight = (EditText) findViewById(R.id.editHeight);
        editWeight = (EditText) findViewById(R.id.editWeight);
        progressDialog = new ProgressDialog(this);

        add = (Button) findViewById(R.id.add);


        mAuth = FirebaseAuth.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.add:
                        addName();
                        break;
                }
                progressDialog.setMessage("Registering User...");
                progressDialog.show();
            }
        });

    }


    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            //handle already login user
        }
    }
    private void addName(){
        final String name = editName.getText().toString().trim();

        final String age = editAge.getText().toString().trim();
        int agetoint = Integer.parseInt(age);

        final String weight = editWeight.getText().toString().trim();
        int weightoint = Integer.parseInt(weight);

        final String height = editHeight.getText().toString().trim();
        int heighttoint = Integer.parseInt(height);


        User user = new User(
                name,
                agetoint,
                weightoint,
                heighttoint
        );

        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Register Successful!", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    startActivity(new Intent(UserActivity.this, HomeActivity.class));
                }else{
                    progressDialog.cancel();
                    Toast.makeText(UserActivity.this, "Error", Toast.LENGTH_LONG).show();

                }
            }
        });

    }



    }

