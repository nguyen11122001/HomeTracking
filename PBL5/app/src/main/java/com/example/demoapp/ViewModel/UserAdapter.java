package com.example.demoapp.ViewModel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Model.History;
import com.example.demoapp.Model.Users;
import com.example.demoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private ArrayList<Users> data;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView nameDel;
        private final TextView phone;
        public LinearLayout lnMain;
        public LinearLayout lnDel;
        private Button btnDel;
        FirebaseFirestore db;
        FirebaseStorage storage ;
        StorageReference storageRef ;
        private String id;
        private Users user;


        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            phone =  (TextView) view.findViewById(R.id.tv_phone);
            nameDel = (TextView)  view.findViewById(R.id.tv_nameDel);
            lnMain = view.findViewById(R.id.ln_main);
            lnDel = view.findViewById(R.id.ln_del);
            btnDel = view.findViewById(R.id.btn_del);
            db = FirebaseFirestore.getInstance();
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storage = FirebaseStorage.getInstance();
                    storageRef = storage.getReference();

                    ArrayList<String> listFile = new ArrayList<>();
                    StorageReference listRef = storage.getReference().child("dataSet/"+user.getName());

                    listRef.listAll()
                            .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                                @Override
                                public void onSuccess(ListResult listResult) {
                                    for (StorageReference prefix : listResult.getPrefixes()) {
                                        // All the prefixes under listRef.
                                        // You may call listAll() recursively on them.
                                    }

                                    for (StorageReference item : listResult.getItems()) {
                                        // All the items under listRef.
                                        Log.d("TAG", "DocumentSnapshot successfully deleted! "+item.getName());
                                        listFile.add(item.getName());

                                    }
                                    delete(listFile);
                                    Log.d("TAG", "DocumentSnapshot successfully deleted! "+listFile);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Uh-oh, an error occurred!
                                }
                            });
                    Log.d("TAG", "DocumentSnapshot successfully deleted! "+listFile);





                    db.collection("Users").document(id)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully deleted! "+id);
                                    data.remove(user);
                                    setData2();
                                    if (lnMain.getVisibility() == View.VISIBLE){
                                        lnMain.setVisibility(View.INVISIBLE);
                                        lnDel.setVisibility(View.VISIBLE);
                                    }
                                    else
                                    {
                                        lnMain.setVisibility(View.VISIBLE);
                                        lnDel.setVisibility(View.INVISIBLE);
                                    }

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("TAG", "Error deleting document", e);
                                }
                            });

                }
            });
//            view.
            view.setOnTouchListener(new OnSwipeTouchListener(view.getContext())
            {
                public void viewDetail()
                {
                    if (lnMain.getVisibility() == View.VISIBLE){
                        lnMain.setVisibility(View.INVISIBLE);
                        lnDel.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        lnMain.setVisibility(View.VISIBLE);
                        lnDel.setVisibility(View.INVISIBLE);
                    }
                }

                public void onClick(){
//                    DogBreed dogBreed =dogBreeds.get(getAdapterPosition());
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("dogBreed",dogBreed);
//                    Navigation.findNavController(view).navigate(R.id.detailFragment, bundle);
                }

                public void onSwipeRight()
                {
                    viewDetail();
                }
                public void onSwipeLeft()
                {
                    viewDetail();
                }
            });
        }
        public void delete(ArrayList<String> list){
            for (String name: list) {
                StorageReference desertRef = storageRef.child("dataSet/"+user.getName()+"/"+name);
                // Delete the file
                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // File deleted successfully
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });

            }
        }
        public TextView getName() {
            return name;
        }
        public TextView getNameDel() {
            return nameDel;
        }
        public TextView getPhone() {
            return phone;
        }

    }
    public UserAdapter(ArrayList<Users> dataSet, Context context) {
        this.data = dataSet;
        this.context = context;

    }
    public void setData2(){
        notifyDataSetChanged();
    }
    public void setData(ArrayList<Users> newData){
        this.data = newData;
        notifyDataSetChanged();
    }

    public ArrayList<Users> getData() {
        return data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.user_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.getName().setText(data.get(position).name);
        viewHolder.getNameDel().setText(data.get(position).name);
        viewHolder.getPhone().setText(data.get(position).phone);
        viewHolder.id=data.get(position).id;
        viewHolder.user=data.get(position);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
