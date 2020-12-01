package com.example.spark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class AccountActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    private EditText user_name,user_email,userphone;
    private static final int PICK_IMAGE_REQUEST = 234;
    private TextView changePassword,userDeactivate,dataChange;
    private ImageView userImage;
    private Button uploadImage;
    public String account,name,email,phone,url,setUpUrl;
    private static final int Imageback = 1;
    private StorageReference Folder;
    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase,mDatabase_url;
    private Uri filePath;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Objects.requireNonNull(getSupportActionBar()).hide();
//        //set up full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");

        user_name = (EditText) findViewById(R.id.text_username);
        user_email = (EditText) findViewById(R.id.text_useremail);
        userphone = (EditText) findViewById(R.id.text_userphone);
        //userImage is for taking the image for profile
        userImage = (ImageView) findViewById(R.id.imageProfile);
        // data change is for changing the info about user
        dataChange = (TextView) findViewById(R.id.buttonUpdate);
        uploadImage = (Button) findViewById(R.id.selectImage);
        changePassword = (TextView)(findViewById(R.id.text_userChangePassword));
        userDeactivate = (TextView)findViewById(R.id.text_userDeactivate);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("AccountDetails").child(Objects.requireNonNull(firebaseAuth.getUid()));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
//                setUpUrl = userProfile.getUrl();
//                Glide.with(AccountActivity.this).load(setUpUrl).into(userImage);
//                URL newurl = new URL(setUpUrl.toString());
//                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
//                userImage.setImageBitmap(mIcon_val);
                account = userProfile.getAccount();
                user_name.setText(userProfile.getName());
                user_email.setText(userProfile.getEmail());
                userphone.setText(userProfile.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AccountActivity.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

//        mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
//
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                UserProfile user = dataSnapshot.getValue(UserProfile.class);
//                url = user.getUrl();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {




//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
                showFileChooser();

            }
        });


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("xyz", filePath.toString());
                //checking if file is available
                if (filePath != null) {
                    //displaying progress dialog while image is uploading
                    final ProgressDialog progressDialog = new ProgressDialog(AccountActivity.this);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();

                    //getting the storage reference

                    Log.v("xyz","sref"+storageReference.toString());
                    Log.v("xyz", "fp"+filePath.toString());

                    StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

                    //adding the file to reference
                    sRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //dismissing the progress dialog
                                    progressDialog.dismiss();

                                    //displaying success toast
                                    Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                                    //creating the upload object to store uploaded image details
                                    String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                    Log.v("xyz","url2"+url);
                                    UserProfile upload = new UserProfile(account,name, email, phone, url);
                                    mDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS);
                                    //adding an upload to firebase database
                                    String uploadId = mDatabase.push().getKey();
                                    mDatabase.child(uploadId).setValue(upload);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    //displaying the upload progress
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });
                } else {
                    //display an error if no file is selected
                }
            }
        });


        userDeactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AccountActivity.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will results in completely removing your account from the system and you won't be able to access the app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AccountActivity.this,"Account deleted",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(AccountActivity.this,Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(AccountActivity.this, (CharSequence) task.getException(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        dataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = user_name.getText().toString();
                email= user_email.getText().toString();
                phone = userphone.getText().toString();



                UserProfile userProfile = new UserProfile(account,name, email, phone,url);
                databaseReference.setValue(userProfile);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountActivity.this,ActivityUserChangePassword.class);
                startActivity(intent);
            }
        });

    }

    private Object getFileExtension(Uri filePath) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(filePath));
    }

    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    public void addVehicle(View view) {
        Intent intent = new Intent(AccountActivity.this,Vehicle.class);
        startActivity(intent);
    }



    public void uploadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(intent,Imageback);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                userImage.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {


            filePath = data.getData();
            storageReference = Folder.child("Images").child(filePath.getLastPathSegment());

            try {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(filePath,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Log.v("abc", "STEP0");


        else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            filePath = data.getData();


            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(filePath,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            userImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

//
//        Log.v("abc", Integer.toString(requestCode));
//        Log.v("abc", Integer.toString(resultCode));
        else if(requestCode == 234 && resultCode == Activity.RESULT_OK){

            Log.v("abc", "STEP1");
            Uri ImageData = data.getData();
            final StorageReference storageReference = Folder.child("Images").child(ImageData.getLastPathSegment());

//            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Uri downloadUrl = taskSnapshot;
//                    newStudent.child("image").setValue(downloadUrl);
//                }
//            });


            storageReference.putFile(ImageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.v("abc", "STEP2");

//                    Toast.makeText(AccountActivity.this,"Uploded",Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.v("abc", "STEP3");

                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("image");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("imageurl",String.valueOf(uri));
                            databaseReference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AccountActivity.this,"Uploaded",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Log.v("a12", "BACK");
        Intent i = new Intent(AccountActivity.this, MapActivity.class);
        startActivity(i);
    }
}
