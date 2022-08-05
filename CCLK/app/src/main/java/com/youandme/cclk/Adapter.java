package com.youandme.cclk;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Dictionary;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context = null;
    private ArrayList<Item> item = null;

    private FirebaseAuth auth;

    public Adapter(ArrayList<Item> items, Context context) {
        this.item = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(item.get(i).getUser());
        viewHolder.dateView.setText(item.get(i).getDate());
        viewHolder.contentsView.setText(item.get(i).getMemocontents());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        auth = FirebaseAuth.getInstance();

//            if (databaseReference.child(databaseReference.getKey()).child("user").getValue() == email){
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("댓글을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        databaseReference.child("memo").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (snapshot.child(dataSnapshot.getKey()).child("date").getValue() == item.get(i).getDate()
                                            && snapshot.child(dataSnapshot.getKey()).child("user").getValue().equals(auth.getCurrentUser().getEmail())
                                    ) {
                                        databaseReference.child("memo").child(dataSnapshot.getKey()).removeValue();
                                        item.remove(i);
                                        notifyItemRemoved(i);
                                        notifyItemRangeChanged(i, item.size());
                                        break;
                                    } else {
                                        Toast.makeText(context.getApplicationContext(), "본인만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
//        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setMessage("댓글을 삭제하시겠습니까?");
//                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int ii) {
//
//                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//                        DatabaseReference databaseReference = firebaseDatabase.getReference();
//                        databaseReference.child("memo").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                                    if(snapshot.child(dataSnapshot.getKey()).child("date").getValue() == item.get(i).getDate()){
//                                        databaseReference.child("memo").child(dataSnapshot.getKey()).removeValue();
//                                        notifyItemRemoved(i);
//                                        break;
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//                });
//
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                builder.show();
//                return false;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView = null;
        public TextView dateView = null;
        public TextView contentsView = null;

        public ViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.memouseremail);
            dateView = (TextView) view.findViewById(R.id.memodate);
            contentsView = (TextView) view.findViewById(R.id.memocontents);
        }

    }

}
