package com.example.realstatemanagers;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class ListPossessionRecyclerAdapter extends ListAdapter<Possession, ListPossessionRecyclerAdapter.ViewHolder> {

    Listener listener;

    public ListPossessionRecyclerAdapter(Listener listener){
        super(new ItemCallback());
        this.listener = listener;
        }

    public interface Listener {
        void onItemClick(Possession possesion);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPossessionRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView type_bien;
        TextView val_bien;
        TextView adr;
        ImageView mImageView;
        ConstraintLayout posse;
        Listener mListener;
        boolean isTablet;

        public ViewHolder(View view, Listener listener) {
            super(view);
            type_bien = view.findViewById(R.id.type_bien);
            val_bien = view.findViewById(R.id.valeur);
            adr = view.findViewById(R.id.town);
            mImageView = view.findViewById(R.id.small_img);
            posse = view.findViewById(R.id.posse);
            mListener = listener;
            isTablet = MainApplication.getApplication().getResources().getBoolean(R.bool.isTablet);
        }

        public void bind(Possession possession) {

            type_bien.setText(possession.getType_bien());
            val_bien.setText(possession.getVal_bien());
            adr.setText(possession.getAdr());

            Uri don = Uri.parse(possession.getPhto());

            Glide.with(mImageView.getContext())
                    .load(Uri.parse(possession.getPhto()))
                    .into(mImageView);

            if(isTablet) {
                mListener.onItemClick(possession);
            }else{
                posse.setOnClickListener(view -> mListener.onItemClick(possession));

            }
        }
    }

    private static class ItemCallback extends DiffUtil.ItemCallback<Possession> {
        @Override
        public boolean areItemsTheSame(@NonNull Possession oldItem, @NonNull Possession newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Possession oldItem, @NonNull Possession newItem) {
            return true;
        }
    }
}
