package com.example.spark;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Objects;
import java.util.regex.Pattern;

public class signUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private FirebaseAuth.AuthStateListener authStateListener;
    private ImageView buttonRegister;
    //private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextname;
    //private EditText editTextName;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Objects.requireNonNull(getSupportActionBar()).hide();

        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister =  findViewById(R.id.buttonRegister);
        editTextname = findViewById(R.id.editTextname);
        editTextEmail = findViewById(R.id.editTextEmail);
        //
        //editTextName = findViewById(R.id.editName);
        //
        editTextPassword = findViewById(R.id.textPassword);
        textViewSignin = findViewById(R.id.textViewSignin);
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.spinner1);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.role, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(signUp.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.role));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(signUp.this,"role is not selected",Toast.LENGTH_SHORT).show();
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String name = editTextname.getText().toString().trim();
        final String Account_Detail = text;
        final String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editTextname.setError("Name required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email required");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextname.setError("Enter valid email id");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter valid password");
            return;
        }
        //Toast.makeText(signUp.this, "error connection", Toast.LENGTH_SHORT).show();
        progressDialog.setMessage("Registering User ...");
        progressDialog.show();
        if (Account_Detail.equals("User")) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        firebaseAccountDetails fad = new firebaseAccountDetails(
                                Account_Detail, name, email
                        );
                        FirebaseDatabase.getInstance().getReference("AccountDetails")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(fad).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(signUp.this, MapActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(signUp.this, "User Not Registered...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        finish();
                        firebaseAccountDetails fad = new firebaseAccountDetails(
                                Account_Detail, name, email
                        );
                        FirebaseDatabase.getInstance().getReference("data")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(fad).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(signUp.this, OwnerActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(signUp.this, "Owner Not Registered...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
        if(v == textViewSignin){
            startActivity(new Intent(signUp.this,Login.class));
        }
    }


}

