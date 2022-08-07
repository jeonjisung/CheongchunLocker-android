package com.youandme.cclk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Locker6 extends AppCompatActivity {
    ImageView productImg;
    Button editBtn;
    TextView product_name, words;

    private ArrayList<Item> memoItems = null;
    private Adapter memoAdapter = null;

    private String username = null;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    TextView dateView;
    RecyclerView recyclerView;
    Button regbtn;

    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

    Save_Var saveVar = Save_Var.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_6);

        productImg = (ImageView) findViewById(R.id.product_img);
        product_name = (TextView) findViewById(R.id.product_name);
        words = (TextView) findViewById(R.id.words);

        dateView = (TextView)findViewById(R.id.memodate);

        recyclerView = (RecyclerView)findViewById(R.id.memolist);

        regbtn = (Button)findViewById(R.id.memobtn);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        init();
        initView();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("lockerData")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getReference().getId().equals("locker6")){
                                    if(document.get("product_name") != null)
                                        product_name.setText(document.get("product_name").toString());
                                    if(document.get("words") != null)
                                        words.setText(document.get("words").toString());
                                }
                            }
                        } else {
                            //실패
                        }
                    }
                });

        FirebaseStorage storage = FirebaseStorage.getInstance(); // FirebaseStorage 인스턴스 생성
        StorageReference storageRef = storage.getReference();// 스토리지 공간을 참조해서 이미지를 가져옴
        storageRef.child("uploads/"+"locker6.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(Locker6.this).load(uri).into(productImg); // Glide를 사용하여 이미지 로드
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("MainActivity", "실패");
            }
        });
    }

    private void init()
    {
        memoItems = new ArrayList<>();
    }

    private void initView()
    {
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.memobtn:
                        regMemo();
                        break;
                }
            }
        });

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
        memoAdapter = new Adapter(memoItems, this);
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

            item.setUser(name);
            item.setEmail(email);
            item.setDate(getTime());
            item.setMemocontents(contentsedit.getText().toString());
        }

//        memoItems.add(item);
//        memoAdapter.notifyDataSetChanged();
//
        databaseReference.child(saveVar.memo_now).push().setValue(item);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
        imm.hideSoftInputFromWindow(contentsedit.getWindowToken(), 0);
        contentsedit.setText(null);
    }

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        String getTime = mFormat.format(mDate);

        return getTime;
    }

    @Override
    protected void onStart(){
        super.onStart();
        addChildEvent();
    }
    private void addChildEvent() {
        databaseReference.child(saveVar.memo_now).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("namjinha", "addChildEvent - onChildAdded:"+ dataSnapshot.getValue());
                Item item = dataSnapshot.getValue(Item.class);

                memoItems.add(item);
                memoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
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
