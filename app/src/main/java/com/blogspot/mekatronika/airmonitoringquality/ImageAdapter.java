package com.blogspot.mekatronika.airmonitoringquality;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context1;
    private List<Upload> uploads1;
    private OnItemClickListener clickListener;

    public ImageAdapter(Context context, List<Upload> uploads) {
        context1 = context;
        uploads1 = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context1).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = uploads1.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.with(context1)
                .load(uploadCurrent.getImageUrl())


                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return uploads1.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_name);
            imageView = itemView.findViewById(R.id.imageview_show);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

            @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");
        delete.setOnMenuItemClickListener(this);
    }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(position);
                }
            }
        }


            @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (clickListener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                switch (item.getItemId()) {

                    case 1:
                        clickListener.onDeleteClick(position);
                        return true;
                }
            }
        }
        return false;
    }
}





    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }



}
