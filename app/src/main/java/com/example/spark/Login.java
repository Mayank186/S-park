package com.example.spark;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Login extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private EditText Email;
    private EditText Password;
    private ImageView Login;
    private TextView RegisterUser,forgotPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private UserProfileChangeRequest profileUpdates;
    private Uri uri;
    public String email,password;
    public int accept,accept_1;
    private Uri.Builder builder;
    private SharedPreferences sharedPreferences;
    private final String URI = "https://api.qrserver.com/v1/create-qr-code/?size=150x150";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up notitle
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        sharedPreferences
                = getSharedPreferences("MySharedPref",
                MODE_PRIVATE);
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        progressDialog = new ProgressDialog(this);
        Email = findViewById(R.id.editText);
        Password = findViewById(R.id.editText2);
        RegisterUser = findViewById(R.id.textView2);
        Login = findViewById(R.id.imageView4);
        forgotPassword = findViewById(R.id.textForgotPassword);
        builder = new Uri.Builder();
        profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(builder.build())
                .build();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null) {
                    FirebaseDatabase.getInstance().getReference("AccountDetails")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("account")){
                                String account = dataSnapshot.child("account").getValue(String.class);
                                if(account.equals("User")){
                                    startActivity(new Intent(getApplicationContext(), MapActivity.class));
                                }
                                else {
                                    //FirebaseDatabase.getInstance().getReference("data").child(FirebaseAuth.getInstance().getCurrentUser()).addValueEventListener()
                                    startActivity(new Intent(getApplicationContext(), OwnerActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    if(firebaseAuth.getCurrentUser().getPhotoUrl()==null){
//                        uri = Uri.parse(URI);
//                        builder = uri.buildUpon();
//                        builder.appendQueryParameter("data",firebaseAuth.getCurrentUser().getUid());
//                        firebaseAuth.getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setPhotoUri(builder.build()).build());
//                    }

                }
            }
        };

        firebaseAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        firebaseAuth.getCurrentUser();
        RegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signUp.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ActivityForgotPassword.class);
                startActivity(intent);
            }
        });

        String em = sharedPreferences.getString("email", "");
        String pw = sharedPreferences.getString("password", "");
        if(!em.equals("") && !pw.equals("")){
            loginUser();

        }

    }

    private void loginUser(){


        String em = sharedPreferences.getString("email", "");
        String pw = sharedPreferences.getString("password", "");
        Log.v("xxx","EMAIL"+ em);
        Log.v("xxx","PASSWORD"+pw);

        if(em.equals("") || pw.equals("")){



// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error

            email = Email.getText().toString().trim();
            password = Password.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Email.setError("Enter valid email address");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Password.setError("Enter valid password");
                return;
            }
        }
        else{
            email = em;
            password = pw;
        }

        progressDialog.setMessage("Authenticating User ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("AccountDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            addValueEventListener(new ValueEventListener() {
                      @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.hasChild("account")){
                        String account = dataSnapshot.child("account").getValue(String.class);
                           //Log.d("","account user");
                         if(account.equals("User")){

                             accept =1;
                             //Log.d("","account, user");
                             Toast.makeText(Login.this,"User Authenticated...",Toast.LENGTH_SHORT).show();
                             progressDialog.dismiss();

                             SharedPreferences.Editor myEdit
                                     = sharedPreferences.edit();

                             myEdit.putString(
                                     "email",email);
                             myEdit.putString(
                                     "password",password);

                             myEdit.commit();


                             Intent intent = new Intent(Login.this, MapActivity.class);
                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                             //Log.v(TAG,"User id " + firebaseAuth.getCurrentUser().getUid());
                             Log.v(TAG,"User id " + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                             startActivity(intent);
                         }

                       }
                      }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    FirebaseDatabase.getInstance().getReference("data").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("account")) {
                                String account1 = dataSnapshot.child("account").getValue(String.class);
                                //Log.d("","account, owner");
                                if(account1.equals("Owner")) {
                                    Toast.makeText(Login.this, "Owner Authenticated...", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();


                                    SharedPreferences.Editor myEdit
                                            = sharedPreferences.edit();

                                    myEdit.putString(
                                            "email",email);
                                    myEdit.putString(
                                            "password",password);

                                    myEdit.commit();
                                    Intent intent = new Intent(Login.this, OwnerActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    Log.v(TAG, "User id " + firebaseAuth.getCurrentUser().getUid());
                                    Log.v(TAG, "User id " + Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                                    startActivity(intent);
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(Login.this,"User Not Authenticated...",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }

}


