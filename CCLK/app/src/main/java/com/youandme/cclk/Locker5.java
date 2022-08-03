package com.youandme.cclk;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Locker5 extends AppCompatActivity {
    ImageView productImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_5);
        productImg = (ImageView) findViewById(R.id.product_img);

        Glide.with(this)
                .load("https://picsum.photos/250/250")
                .override(250, 250)
                .into(productImg);
    }
}
