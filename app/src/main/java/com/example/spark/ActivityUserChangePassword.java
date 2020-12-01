package com.example.spark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ActivityUserChangePassword extends AppCompatActivity {

    public EditText currentPass,newPass,confirmNewPass;
    private Button passwordChange;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    public String currentPassword,newPassword,confirmNewPassword;
    private FirebaseUser firebaseUser;


    public void temp1 (android.view.View v){
        currentPassword = currentPass.getText().toString();
        newPassword = newPass.getText().toString();
        confirmNewPassword = confirmNewPass.getText().toString();
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String email = user.getEmail();
        AuthCredential authCredential = EmailAuthProvider.getCredential(email, currentPassword);
        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(newPassword.equals(confirmNewPassword)){
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ActivityUserChangePassword.this,"Password Changed",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityUserChangePassword.this,MapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ActivityUserChangePassword.this,"Both password is different",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_password);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        passwordChange = (Button) findViewById(R.id.buttonUpdatePassword);
        currentPass = (EditText) findViewById(R.id.editCurrentPassword);
        newPass = (EditText) findViewById(R.id.editNewPassword);
        confirmNewPass = (EditText) findViewById(R.id.editConfirmNewPassword);

        if(TextUtils.isEmpty(currentPassword)){
            currentPass.setError("Current password is required");
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            newPass.setError("New password is required");
            return;
        }
        if(TextUtils.isEmpty(confirmNewPassword)){
            confirmNewPass.setError("New confirm password is required");
            return;
        }

    }

}
