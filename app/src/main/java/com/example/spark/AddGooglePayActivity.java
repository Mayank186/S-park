package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddGooglePayActivity extends AppCompatActivity {

    public TextView textViewButtonID;
    public EditText editTextAddID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_google_pay);
        Objects.requireNonNull(getSupportActionBar()).hide();


        editTextAddID = findViewById(R.id.editTextAddGoogleID);
        textViewButtonID = findViewById(R.id.textViewButtonAddGoogleID);

        textViewButtonID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGoogleID();
            }
        });




    }

    private void addGoogleID() {
        final String AddID = editTextAddID.getText().toString().trim();
        if (TextUtils.isEmpty(AddID)) {
            editTextAddID.setError("ID required");
            return;
        }
        AddGoogleIDOfUSer addGoogleIDOfUSer = new AddGoogleIDOfUSer(
            AddID
        );
        FirebaseDatabase.getInstance().getReference("AccountDetails")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("googleid")
                .setValue(addGoogleIDOfUSer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddGooglePayActivity.this,"ID Stored",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), MapActivity.class));
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("a12", "BACK");
        Intent i = new Intent(AddGooglePayActivity.this, MapActivity.class);
        startActivity(i);
    }
}
