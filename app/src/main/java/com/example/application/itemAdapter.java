package com.example.application;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder>{
    private ArrayList<MyListt> listts;
    public itemAdapter(ArrayList<MyListt>  listts ){
        this.listts=listts;
    }
    private AppCompatActivity app;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_song_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull itemAdapter.ViewHolder holder, int position) {
        final ArrayList<MyListt> myListt= listts;
        holder.textViewWriter.setText(myListt.get(position).getSongWriterName());
        holder.textViewTitle.setText(myListt.get(position).getSongTitle());
        holder.imageView.setImageResource(myListt.get(position).getIconId());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent (context,songPage.class);
                intent.putExtra("List",  myListt);
                intent.putExtra("position",holder.getAbsoluteAdapterPosition());
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return listts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public ImageButton sendButton;
        public ImageButton deleteButton;
        public TextView textViewWriter;
        public TextView textViewTitle;
        public CardView constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView=(ImageView)itemView.findViewById(R.id.icon);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.songTitle);
            this.textViewWriter= (TextView) itemView.findViewById(R.id.songWriter);
            this.sendButton = (ImageButton) itemView.findViewById(R.id.sendButton);
            this.deleteButton = (ImageButton)itemView.findViewById(R.id.fileDeleteButton);
            constraintLayout=(CardView) itemView.findViewById(R.id.cardView);
            sendButton.setOnClickListener(this );
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view == sendButton){
                sendFile(view,getLayoutPosition());
            } else if (view == deleteButton ) {
                deleteFile(view,getLayoutPosition());
            }
        }
    }

    private void deleteFile(View view,int position){
        ContentResolver resolver=view.getContext().getApplicationContext().getContentResolver();
         ;
        String Path=listts.get(position).getPath();

        File file=new File(Path);
        if (file.exists()){
            boolean deleted = file.delete();
            if(deleted){
                Toast.makeText(view.getContext(),"olduuuu",Toast.LENGTH_SHORT).show();
            }
        }


    }
    private void sendFile(View view,int position){
        Context context = view.getContext();
        Intent intent = new Intent(Intent.ACTION_SEND);
        File file= new File(listts.get(position).getPath());
        if(file.exists()) {
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(listts.get(position).getPath()));
            intent.putExtra(Intent.EXTRA_SUBJECT,"Sharing File...");
            intent.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
            context.startActivity(Intent.createChooser(intent, "Share File"));
        }
    }
}
