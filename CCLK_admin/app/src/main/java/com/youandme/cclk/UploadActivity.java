package com.youandme.cclk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    LinearLayout select_gallery;
    EditText words_edit, product_name_edit;
    Button apply_button;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);

        words_edit = (EditText) findViewById(R.id.words_edit);
        product_name_edit = (EditText) findViewById(R.id.product_name_edit);
        apply_button = (Button) findViewById(R.id.apply_button);
        select_gallery = (LinearLayout) findViewById(R.id.select_gallery);
        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSelect(view);
            }
        });
        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(words_edit.getText() != null && product_name_edit.getText() != null){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> data = new HashMap<>();
                    data.put("product_name", product_name_edit.getText().toString());
                    data.put("words", words_edit.getText().toString());
                    db.collection("lockerData").document(Save_Var.getInstance().now).update(data);
                    finish();
                    Toast.makeText(getApplicationContext(), "적용되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickSelect(View view) {
        Intent intent= new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){

                    imgUri = data.getData();

                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

                    String filename = Save_Var.getInstance().now + ".png";

                    StorageReference imgRef = firebaseStorage.getReference("uploads/" + filename);

                    UploadTask uploadTask = imgRef.putFile(imgUri);

                }
                break;
        }
    }

}
