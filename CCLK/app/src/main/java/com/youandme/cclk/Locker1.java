package com.youandme.cclk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Locker1 extends AppCompatActivity implements View.OnClickListener, ViewListener {

    private ArrayList<Item> memoItems = null;
    private Adapter memoAdapter = null;

    private String username = null;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    ImageView productImg;
    TextView dateView;
    RecyclerView recyclerView;
    Button regbtn;

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_1);

        productImg = (ImageView) findViewById(R.id.product_img);
        dateView = (TextView)findViewById(R.id.memodate);

        recyclerView = (RecyclerView)findViewById(R.id.memolist);

        regbtn = (Button)findViewById(R.id.memobtn);

        init();
        initView();

        Glide.with(this)
                .load("https://picsum.photos/250/250")
                .override(250, 250)
                .into(productImg);

    }

    @Override
    public void onItemClick(int position, View view)
    {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.memobtn:
                regMemo();
                break;
        }
    }

    private void init()
    {
        memoItems = new ArrayList<>();
    }

    private void initView()
    {
        regbtn.setOnClickListener(this);

//        Button userbtn = (Button)findViewById(R.id.reguser);
//        userbtn.setOnClickListener(this);
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                regbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
//                    }
//                });
//            }
//        });
        memoAdapter = new Adapter(memoItems, this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(memoAdapter);
    }

    private void regMemo()
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        EditText contentsedit = (EditText) findViewById(R.id.edit_text);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.memolist);

        if(contentsedit.getText().toString().length() == 0 ||
                contentsedit.getText().toString().length() == 0)
        {
            Toast.makeText(this,
                    "메모 내용이 입력되지 않았습니다. 입력 후 다시 시작해주세요.",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Item item = new Item();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            item.setUser(email);
            item.setDate(item.getTime());
            item.setMemocontents(contentsedit.getText().toString());
        }

//        memoItems.add(item);
//        memoAdapter.notifyDataSetChanged();
//
        databaseReference.child("memo").push().setValue(item);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        imm.hideSoftInputFromWindow(contentsedit.getWindowToken(), 0);
        contentsedit.setText(null);
    }

    @Override
    protected void onStart(){
        super.onStart();
        addChildEvent();
    }
    private void addChildEvent() {
        databaseReference.child("memo").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("namjinha", "addChildEvent - onChildAdded:"+ dataSnapshot.getValue());
                Item item = dataSnapshot.getValue(Item.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("namjinha", "addChildEvent - onChildChanged" + s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
