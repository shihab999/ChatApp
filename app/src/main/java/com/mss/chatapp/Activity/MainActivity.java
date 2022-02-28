package com.mss.chatapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mss.chatapp.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(MainActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.profile:
                Toast.makeText(MainActivity.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(MainActivity.this, "Setting clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                logOut();

        }
        return super.onOptionsItemSelected(item);
    }

    private void logOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LogIn.class));
        finish();
    }
}