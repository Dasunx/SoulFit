package com.dasun.soulfit;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class ProfileActivity extends AppCompatActivity{
    SharedPreferences sp;
    TextView name;
    EditText profileName;
    EditText proEmail;
    EditText phone;
    EditText fb,twt;
    Button save;
    Button uploadProPic;
    Button signoutuser;
    ImageView settings;
    ImageView profilePhoto;
    LinearLayout weight;
    LinearLayout facebook;
    LinearLayout namesec;
    LinearLayout twitterSec;
    LinearLayout phoneSec;
    LinearLayout emailSec;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    ProfileDetail newPro;
    static int key =1;
    TextView primaryMail;
    static String uri1;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    LinearLayout workout;
    LinearLayout callorie;

    //firebase objects
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userx = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("profileDetails").child(userx.getUid());


        sp= getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int detailsAdded;

        storageReference = FirebaseStorage.getInstance().getReference();

        SharedPreferences.Editor spEdit= sp.edit();
        spEdit.putInt("mode",1);
        spEdit.commit();

        name = findViewById(R.id.profile_name);

        profileName = findViewById(R.id.Editname);
        proEmail = findViewById(R.id.secEmail);
        phone = findViewById(R.id.phonenumber);
        fb = findViewById(R.id.fburl);
        twt = findViewById(R.id.profileTwitter);
        weight = findViewById(R.id.profile_details);
        save = findViewById(R.id.SaveProfile);
        uploadProPic=findViewById(R.id.verifyEmail);
        settings = findViewById(R.id.btn_settings);
        facebook = findViewById(R.id.profilefacebook);
        namesec = findViewById(R.id.nameSection);
        emailSec = findViewById(R.id.emailSection);
        phoneSec = findViewById(R.id.phoneSection);
        twitterSec = findViewById(R.id.twitterSection);
        primaryMail = findViewById(R.id.profile_desc);
        profilePhoto = findViewById(R.id.profilePic);
        primaryMail.setText(userx.getEmail().toString());
        signoutuser = findViewById(R.id.signOutR);
        workout = findViewById(R.id.workoutSec);
        callorie = findViewById(R.id.callorieSec);
        newPro = new ProfileDetail();



        if(key==1){
            detailsAdded=1;
        }else {
            detailsAdded=0;
        }

        if(detailsAdded==1){
            save.setVisibility(View.GONE);
            uploadProPic.setVisibility(View.GONE);
            profileName.setClickable(false);
            proEmail.setClickable(false);
            phone.setClickable(false);
            fb.setClickable(false);
            profileName.setFocusable(false);
            proEmail.setFocusable(false);
            phone.setFocusable(false);
            fb.setFocusable(false);

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ProfileDetail oldPro = dataSnapshot.getValue(ProfileDetail.class);
                    if(oldPro!=null){
                        String dN=oldPro.getDisplayName();
                        String Email=oldPro.getSecondaryEmail();
                        String phn=oldPro.getPhone();
                        String fbu=oldPro.getFb();
                        String twitter = oldPro.getTwitter();
                        String ImageUrl = oldPro.getProPic();

                        if(dN !=null){
                            loadWithGlide();
                            profileName.setText(dN);
                            name.setText(dN);
                        }
                        if(Email!=null){
                            proEmail.setText(Email);
                        }
                        if(phn!=null){
                            phone.setText(phn);
                        }if(twitter!=null){
                            twt.setText(twitter);
                        }
                        if(fbu!=null){
                            fb.setText(fbu);
                        }
                    }
                    checkNull();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    checkNull();
                }
            });
        }
        signoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        profilePhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickImg();
               return true;
            }
        });
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortClickImg();

            }
        });

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutTO();
            }
        });

        callorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callorieTo();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadProPic.setVisibility(View.GONE);

                weight.setVisibility(View.VISIBLE);
                newPro.setUid(userx.getUid().toString().trim());
                newPro.setDisplayName(profileName.getText().toString().trim());
                newPro.setSecondaryEmail(proEmail.getText().toString().trim());
                newPro.setPhone(phone.getText().toString().trim());
                newPro.setFb(fb.getText().toString().trim());
                newPro.setTwitter(twt.getText().toString().trim());
                mDatabase.setValue(newPro);

                SharedPreferences.Editor spEdit= sp.edit();
                spEdit.putInt("details",1);
                spEdit.putInt("mode",1);
                spEdit.commit();
                save.setVisibility(View.GONE);
                save.setVisibility(View.GONE);
                profileName.setClickable(false);
                proEmail.setClickable(false);
                phone.setClickable(false);
                fb.setClickable(false);
                profileName.setFocusable(false);
                proEmail.setFocusable(false);
                phone.setFocusable(false);
                fb.setFocusable(false);
                profileName.setFocusableInTouchMode(false);
                proEmail.setFocusableInTouchMode(false);
                phone.setFocusableInTouchMode(false);
                fb.setFocusableInTouchMode(false);
                twt.setClickable(false);
                twt.setFocusable(false);
                twt.setFocusableInTouchMode(false);
                twt.setOnTouchListener(null);
                proEmail.setOnTouchListener(null);
                phone.setOnTouchListener(null);
                fb.setOnTouchListener(null);


                twt.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                proEmail.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                phone.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                fb.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                checkNull();
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                save.setVisibility(View.VISIBLE);
                weight.setVisibility(View.GONE);

                profileName.setClickable(true);
                proEmail.setClickable(true);
                phone.setClickable(true);
                fb.setClickable(true);
                profileName.setFocusable(true);
                proEmail.setFocusable(true);
                phone.setFocusable(true);
                fb.setFocusable(true);
                profileName.setFocusableInTouchMode(true);
                proEmail.setFocusableInTouchMode(true);
                phone.setFocusableInTouchMode(true);
                fb.setFocusableInTouchMode(true);
                twt.setClickable(true);
                twt.setFocusable(true);
                twt.setFocusableInTouchMode(true);

                twt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delete,0);
                proEmail.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delete,0);
                phone.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delete,0);
                fb.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_delete,0);

                facebook.setVisibility(View.VISIBLE);
                twitterSec.setVisibility(View.VISIBLE);
                emailSec.setVisibility(View.VISIBLE);
                phoneSec.setVisibility(View.VISIBLE);
                SharedPreferences.Editor spEdit= sp.edit();
                spEdit.putInt("mode",0);
                spEdit.commit();

                int mode= sp.getInt("mode",0);
                if(mode==0){
                    twt.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;
                            if(mode==0){
                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (twt.getRight() - twt.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                        removeChild("twitter");
                                        return true;
                                    }
                                }
                            }

                            return false;
                        }
                    });
                    proEmail.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if(mode==0){
                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (proEmail.getRight() - proEmail.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                        removeChild("secondaryEmail");
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                    });
                    phone.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if(mode==0){
                                if(event.getAction() == MotionEvent.ACTION_UP) {
                                    if(event.getRawX() >= (phone.getRight() - phone.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                        removeChild("phone");
                                        return true;
                                    }
                                }
                            }
                            return false;
                        }
                    });
                    fb.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if(event.getAction() == MotionEvent.ACTION_UP) {
                                if(event.getRawX() >= (fb.getRight() - fb.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                    removeChild("fb");
                                    return true;
                                }
                            }
                            return false;
                        }
                    });
                }


            }
        });
        uploadProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFile();
            }
        });
        loadWithGlide();
        loadWithGlide();
    }

    private void callorieTo() {
        Intent m = new Intent(this,addFood.class);
        startActivity(m);
    }

    private void workoutTO() {
        Intent m = new Intent(this,WorkoutsActivity.class);
        startActivity(m);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        uploadProPic.setVisibility(View.VISIBLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilePhoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void loadWithGlide() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+mAuth.getCurrentUser().getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                uri1 = uri.toString();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        if(uri1==null){
            uri1="https://firebasestorage.googleapis.com/v0/b/soulfit-f30c3.appspot.com/o/images%2Fdefault.jpg?alt=media&token=b979478a-6f9e-430a-84f6-a739594618af";
        }
        // [START storage_load_with_glide]
        // Reference to an image file in Cloud Storage
        Toast.makeText(this,uri1,Toast.LENGTH_LONG).show();

        // ImageView in your Activity
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this /* context */)
                .load(uri1)
                .apply(RequestOptions.circleCropTransform()).into(profilePhoto);
        // [END storage_load_with_glide]
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            Toast.makeText(this,filePath.toString(),Toast.LENGTH_LONG).show();
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child("images/" + mAuth.getCurrentUser().getUid() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();


                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "Profile Photo Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            mDatabase.child("profilePic").setValue(uri1);
                            uploadProPic.setVisibility(View.GONE);

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
        }
    }


    private void shortClickImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // add a list
        String[] options = {"Upload Photo"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    uploadImage();
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadImage() {
        showFileChooser();
        uploadFile();
        loadWithGlide();
        loadWithGlide();
    }

    private void longClickImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        // add a list
        String[] options = {"Update Photo", "Remove Photo"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: uploadImage();
                            break;
                    case 1:
                        deleteProPic();
                        break;
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteProPic() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

// Create a reference to the file to delete
        StorageReference desertRef = storageRef.child("images/"+mAuth.getCurrentUser().getUid()+".jpg");

// Delete the file
        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                removeChild("profilePic");
                loadWithGlide();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });
    }



    public void signOut() {
        mAuth.signOut();
        Intent myn = new Intent(this,Login.class);
        startActivity(myn);
    }

    private void removeChild(String name){
        mDatabase.child(name).setValue("");

        Toast.makeText(getApplicationContext(),"Sucsess "+ name +" Deleted",Toast.LENGTH_LONG).show();
    }

    private void checkNull(){
        int mode= sp.getInt("mode",0);
        if(proEmail.getText().toString().trim().isEmpty()){
            if (mode==1){
            emailSec.setVisibility(View.GONE);}
        }
        if(phone.getText().toString().trim().isEmpty()){
            if (mode==1){
                phoneSec.setVisibility(View.GONE);
            }
        }
        if(fb.getText().toString().trim().isEmpty()){
            if (mode==1){
            facebook.setVisibility(View.GONE);}
        }
        if(twt.getText().toString().trim().isEmpty()){
            if(mode==1){
            twitterSec.setVisibility(View.GONE);}
        }
    }

}
