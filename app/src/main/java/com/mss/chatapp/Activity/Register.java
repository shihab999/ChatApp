package com.mss.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mss.chatapp.Model.User;
import com.mss.chatapp.R;

public class Register extends AppCompatActivity {

    TextInputEditText name,mobileNum,email,password;
    TextView noAcclogIn;
    AppCompatButton signUp;
    String currentUser;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    LogIn log = new LogIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = findViewById(R.id.name);
        mobileNum = findViewById(R.id.mobileNum);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        noAcclogIn = findViewById(R.id.noAccLogIn);
        signUp = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Please Wait...");


        noAcclogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,LogIn.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_str = name.getText().toString().trim();
                String mobileNum_str = mobileNum.getText().toString().trim();
                String email_str = email.getText().toString().trim();
                String password_str = password.getText().toString().trim();

                if(name_str.isEmpty()){
                    log.aleartDialogError("Name field can't be empty",Register.this);
                }else if(mobileNum_str.isEmpty()){
                    log.aleartDialogError("Mobile number can't be empty",Register.this);
                }else if(mobileNum_str.length()!=11) {
                    log.aleartDialogError("Mobile number is not valid",Register.this);
                }else if(email_str.isEmpty()) {
                    log.aleartDialogError("Mobile number can't be empty",Register.this);
                }else if(password_str.isEmpty()) {
                    log.aleartDialogError("Mobile number can't be empty",Register.this);
                }else {
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email_str,password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){

                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                if (firebaseUser != null){
                                    currentUser = firebaseUser.getUid();
                                }

                                User user = new User(name_str,mobileNum_str,email_str,password_str);
                                databaseReference.child(currentUser).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(Register.this,"Successfull",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Register.this, LogIn.class));
                                            finish();
                                        }
                                    }
                                });


                            } else {
                                String error = task.getException().getLocalizedMessage();
                                log.aleartDialogError(error,Register.this);
                            }

                        }
                    });
                }

            }
        });


    }


//    private void aleartDialogError(String msg) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
//        builder.setTitle("Error");
//        builder.setMessage(msg);
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
    //  }
}