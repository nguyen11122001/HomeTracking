package com.example.demoapp;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.demoapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity
{
    private Button btnChoose, btnUpload,btnSelfie,btnCheck, btnAdd,btnTrain;
    String currentPhotoPath;
    private Users user ;
    static final int REQUEST_IMAGE_CAPTURE = 22;
    private ImageView imageView;
    private EditText fileName;
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    ArrayList<Users> listUser = new ArrayList<Users>();
    private static ArrayList<String> listName = new ArrayList<String>();

    private boolean imgExist,imgFile,canPush=false,isEnough=false,userExist=false;
    private String perName;
    private int countofimages;
    private Uri filePath,filePath2;
    private TextView txt_count,txtName;
    Bitmap captureImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    private final int PICK_IMAGE_REQUEST = 71;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("vuong2@gmail.com","Vuong2709@")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            //     binding.txtStatus.setText(task.getException().toString());
                            Toast.makeText(UploadActivity.this,"Login failed",Toast.LENGTH_LONG).show();

                        }
                    }
                });

        countofimages=0;
        imgExist=false;
        imgFile=false;
        setContentView(R.layout.activity_upload);
        btnChoose = findViewById(R.id.btnChoose1);
        btnUpload = findViewById(R.id.btnUpload1);
        btnSelfie = findViewById(R.id.btnSelfie);
        btnCheck  = findViewById(R.id.btnCheck);
        btnTrain  = findViewById(R.id.btn_train);
        txtName   = findViewById(R.id.txtFolder);
        imageView = findViewById(R.id.imgView1);
        txt_count = findViewById(R.id.txtCount);
        fileName  = findViewById(R.id.edtFolder);
        spinner = (Spinner)findViewById(R.id.spinner);

        mDatabase = FirebaseDatabase.getInstance().getReference("UsersData/iH0qIYQfyzWzX5kQQRftJ1y422o2");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user = document.toObject(Users.class);
                                user.setId(document.getId());
                                listUser.add(user);
                                listName.add(user.getName());
                                Log.d("TAG", " Name: " + user.getName());
                                Log.d("TAG", " " + document.getData());

                            }


                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
        adapter = new ArrayAdapter<String>(UploadActivity.this,
                android.R.layout.simple_spinner_item,listName);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                train();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perName = fileName.getText().toString();
                userExist=checkFolder(perName);
                if(userExist){
                    txtName.setText(perName);
                    checkQuantity();
                }
            }
        });


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userExist){
                    chooseImage();
                }
                else{
                    errorNoti();
                }

            }
        });

        btnSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userExist){
                    dispatchTakePictureIntent();
                }
                else{
                    errorNoti();
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        checkQuantity();
    }
    public void train() {
//        mDatabase.set
//        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("TAG", "Error getting documents: key"+ mDatabase.getKey());
//        Log.d("TAG", "Error getting documents: key 222"+ user.getUid());
        mDatabase.child("trainData").child("train").setValue(true);
        new MaterialAlertDialogBuilder(this)
                .setTitle("Notification")
                .setMessage("It training!!")
                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private Boolean checkFolder(String name){
        if (listName==null){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Please waiting for load user!!")
                    .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
            return false;
        }

        for (String name2:listName) {
            System.out.println(listName);
            Log.d("tad","adadad");
            if(name.equals(name2)) return true;
        }
        errorNoti();
        return false;
    }
    private void errorNoti(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Error")
                .setMessage("Don't have user!!! Please re-enter.")
                .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
    private void checkQuantity(){
        StorageReference okref = storageReference.child("dataSet/").child(perName+"/");
        okref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                countofimages =  listResult.getItems().size();//will give you number of files present in your firebase storage folder
                txt_count.setText("Quantity: "+countofimages);
                if(countofimages>=40){
                    canPush=false;
                    isEnough=true;
                }else{
                    isEnough=false;
//                    canPush=true;
                }
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG1111: ", "return ");
        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null ) {
            Log.d("TAG222222: ", "return ");
            galleryAddPic();
            setPic();
            imgExist=true;
            imgFile=false;
        }else{
            imgExist=false;
            Log.d("errrrrrrrrrrrrrrr ", "return ");

        }

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                captureImage = bitmap;
                imgExist=true;
                imgFile=true;

            }
            catch (IOException e)
            {
                imgExist=false;
                return;
            }
        }
        canPush=true;
    }

    private void uploadImage() {
        if(!userExist ){
            errorNoti();
            return;
        }
        if(!imgExist){
            new MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Please choice some image!!")
                    .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
            return;
        }
        if(canPush){
            if((filePath != null) && imgFile){
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("dataSet/").child(txtName.getText()+"/"+ UUID.randomUUID().toString()+".jpg");
                ref.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                checkQuantity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });

            }
            else{
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference ref = storageReference.child("dataSet/").child(txtName.getText()+"/"+ UUID.randomUUID().toString()+".jpg");
                ref.putFile(filePath2)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                checkQuantity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            }
                        });

            }
            canPush=false;
        }
        else{
            if(isEnough){
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Done")
                        .setMessage("Images are enough!!")
                        .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }else{
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Image was push!")
                        .setMessage("Please choice new image!!")
                        .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
        }
    }

    // create file to save image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = getExternalFilesDir("Pictures2");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //save image by uri
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileProvider32",
                        photoFile);
                Log.d("tatata:",photoFile.toString());
                Log.d("tatata3333333333333333:",photoURI.toString());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                filePath2=photoURI;
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //add image to media
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    //set image by bitmap to image view
    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

}