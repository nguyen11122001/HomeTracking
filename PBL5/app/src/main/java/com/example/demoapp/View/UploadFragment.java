package com.example.demoapp.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demoapp.HomeActivity;
import com.example.demoapp.MainActivity;
import com.example.demoapp.R;
import com.example.demoapp.UploadActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class UploadFragment extends Fragment {


    UploadFragmentListener activityCallback;
    public interface UploadFragmentListener {
        public void onButtonClick();
    }

    Context thiscontext;
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UploadFragment() {
        // Required empty public constructor
    }

    public static UploadFragment newInstance(String param1, String param2) {
        UploadFragment fragment = new UploadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            activityCallback = (UploadFragmentListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " You must implement FirstFragmentListener");
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityCallback = (UploadFragmentListener) savedInstanceState;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_upload, container, false);
        thiscontext = container.getContext();



        btnChoose = (Button) view.findViewById(R.id.btnChoose);
        btnUpload = (Button) view.findViewById(R.id.btnUpload);
        imageView = (ImageView) view.findViewById(R.id.imgView);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activityCallback.onButtonClick();
                Intent intent = new Intent(thiscontext, UploadActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("Key_1", user.getUid());
//        intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}