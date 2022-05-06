package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class playlistAdapter extends RecyclerView.Adapter<playlistAdapter.ViewHolder> {
    private ArrayList<MyListt> listts;
    private String playlistName;
    public playlistAdapter(ArrayList<MyListt> listts,String playlistName) {
        this.listts = listts;
        this.playlistName= playlistName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.playlist_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull playlistAdapter.ViewHolder holder, int position) {
        final ArrayList<MyListt> myListt= listts;
        holder.textViewPlaylistName.setText("Merhaba");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent (context,songPage.class);
                intent.putExtra("PlayList",  myListt);
                intent.putExtra("PlayListPosition",holder.getAbsoluteAdapterPosition());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView playlistIcon;
        public ImageButton deleteButton;
        public ImageButton addButton;
        public TextView textViewPlaylistName;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.playlistIcon=(ImageView)itemView.findViewById(R.id.playlistIcon);
            this.textViewPlaylistName= (TextView) itemView.findViewById(R.id.playlistName);
            this.deleteButton= (ImageButton) itemView.findViewById(R.id.deleteButton);
            cardView=(CardView) itemView.findViewById(R.id.playlistCardView);
        }
    }
}
