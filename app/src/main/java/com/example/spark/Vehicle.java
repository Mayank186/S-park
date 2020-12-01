package com.example.spark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.Util;

import java.util.HashMap;
import java.util.Objects;

public class Vehicle extends AppCompatActivity {

    private EditText userCarNumber,userCarModel;
    private TextView submitVehicleDetails;
    private ImageView carImage;
    private static final int Imageback = 1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseStorage Folder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);
        Objects.requireNonNull(getSupportActionBar()).hide();
        //set up full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userCarNumber = (EditText) findViewById(R.id.textNumberPlate);
        userCarModel = (EditText) findViewById(R.id.textModel);
        submitVehicleDetails = (TextView) findViewById(R.id.buttonCarDetails);
        carImage = (ImageView) findViewById(R.id.imageViewCar);
        submitVehicleDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String carNumber=userCarNumber.getText().toString().trim();
                String carModel = userCarModel.getText().toString().trim();
                VehicleDetails vehicleDetails = new VehicleDetails(
                        carNumber,carModel
                );
                FirebaseDatabase.getInstance().getReference("AccountDetails")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("vehicle").setValue(vehicleDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Vehicle.this,"Vehicle details uploaded",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MapActivity.class));
                        }
                    }
                });
            }
        });




        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == Imageback && requestCode == RESULT_OK){
//            Uri ImageData = data.getData();
//            final StorageReference storageReference = Folder.child("image"+ImageData.getLastPathSegment());
//            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Toast.makeText(AccountActivity.this,"Uploded",Toast.LENGTH_SHORT).show();
//                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("image");
//                            HashMap<String, String> hashMap = new HashMap<>();
//                            hashMap.put("imageurl",String.valueOf(uri));
//                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(Vehicle.this,"Uploaded",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//                    });
//                }
//            });
//        }
//    }

