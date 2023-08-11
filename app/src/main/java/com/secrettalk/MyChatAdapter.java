package com.secrettalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.CustomViewHolder>  {

    private OnItemClick mCallback;
    private ArrayList<MyChatReViewItem> arrayList;
    private Context context;



    public MyChatAdapter(ArrayList<MyChatReViewItem> arrayList, Context context, OnItemClick listener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mCallback = listener;
    }

    public void increment(String name, int a, int position){
        mCallback.onClick(name, a, position);
    }



    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.chatName.setText(arrayList.get(position).getChatName());
        holder.outBtn.setTag(holder.getAdapterPosition());
        holder.outBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                increment(arrayList.get(position).getChatName(), 0, position);
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                increment(arrayList.get(position).getChatName(), 1, position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView chatName;
        Button outBtn;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chatName = itemView.findViewById(R.id.chatName);
            this.outBtn = itemView.findViewById(R.id.outBtn);
        }


    }
}
