package com.mss.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mss.chatapp.R;

public class LogIn extends AppCompatActivity {

    TextInputEditText email,password;
    TextView register;
    AppCompatButton logIn;

    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        logIn = findViewById(R.id.logIn);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(LogIn.this);
        progressDialog.setMessage("Please Wait...");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,Register.class));
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = email.getText().toString().trim();
                String password_str = password.getText().toString().trim();

                progressDialog.show();

                if(email_str.isEmpty()){
                    showError(email,"Enter Email");
                } else if(password_str.isEmpty()){
                    showError(password,"Enter Password");
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email_str,password_str).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                startActivity(new Intent(LogIn.this,MainActivity.class));
                                Toast.makeText(LogIn.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                            } else {
                                String error = task.getException().getLocalizedMessage();
                                aleartDialogError(error,LogIn.this);
                            }
                        }
                    });
                }


            }
        });


    }

    public void aleartDialogError(String error, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(error);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showError(TextInputEditText inputbox, String msg) {
        inputbox.setError(msg);
        inputbox.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(LogIn.this,MainActivity.class));
            finish();
        }

    }
}