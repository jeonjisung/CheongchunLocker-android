package com.youandme.cclk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    private Context context = null;
    private ArrayList<Item> item = null;
    private ViewListener viewListener = null;

    public Adapter(ArrayList<Item> items, Context context, ViewListener listener)
    {
        this.item = items;
        this.context = context;
        this.viewListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(item.get(i).getUser());
        viewHolder.dateView.setText(item.get(i).getDate());
        viewHolder.contentsView.setText(item.get(i).getMemocontents());
    }

    @Override
    public int getItemCount()
    {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView titleView = null;
        public TextView dateView = null;
        public TextView contentsView = null;

        public ViewHolder(View view) {
            super(view);
            titleView = (TextView)view.findViewById(R.id.memouseremail);
            dateView = (TextView)view.findViewById(R.id.memodate);
            contentsView = (TextView)view.findViewById(R.id.memocontents);

//            view.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    item.remove(getAdapterPosition());
//                    notifyItemRemoved(getAdapterPosition());
//                    notifyItemRangeChanged(getAdapterPosition(), item.size());
//                    return false;
//                }
//            });
        }

        @Override
        public void onClick(View view)
        {
            viewListener.onItemClick(getAdapterPosition(), view);
        }
    }

}
