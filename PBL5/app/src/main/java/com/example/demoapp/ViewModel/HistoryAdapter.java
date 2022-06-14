package com.example.demoapp.ViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demoapp.Model.History;
import com.example.demoapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private ArrayList<History> data;
    private Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;
        private final TextView time;
        private final ImageView isAccept;

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.iv_avatar);
            name = (TextView) view.findViewById(R.id.tv_name);
            time =  (TextView) view.findViewById(R.id.tv_time);
            isAccept = (ImageView) view.findViewById(R.id.iv_accept);
        }

        public ImageView getImage() {
            return image;
        }

        public TextView getName() {
            return name;
        }

        public TextView getTime() {
            return time;
        }
        public ImageView getIsAccept() {
            return isAccept;
        }
    }
    public HistoryAdapter(ArrayList<History> dataSet, Context context) {
        this.data = dataSet;
        this.context = context;
    }

    public void setData(ArrayList<History> newData){
        this.data = newData;
        notifyDataSetChanged();
    }

    public ArrayList<History> getData() {
        return data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context)
                .inflate(R.layout.history_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText(data.get(position).name);
        viewHolder.getTime().setText(data.get(position).time);
        viewHolder.getIsAccept().setImageResource((data.get(position).isAccepted)?(R.drawable.ic_baseline_check_24):(R.drawable.ic_baseline_close_24));
        Picasso.get()
                .load(data.get(position).url)
                .fit()
                .centerInside()
                .into(viewHolder.getImage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.size();
    }
}
