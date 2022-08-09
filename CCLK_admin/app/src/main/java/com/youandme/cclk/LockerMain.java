package com.youandme.cclk;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LockerMain extends AppCompatActivity {
    RelativeLayout[] b = new RelativeLayout[9];

    private final long finishtimeed = 1000;
    private long presstime = 0;

    TextView info;
    Save_Var saveVar = Save_Var.getInstance();
    void M_onClick(RelativeLayout b, final int num) {

        final boolean[] check = {false};
        b.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                switch (num) {
                    case 0:
                        saveVar.memo_now = "memo1";
                        saveVar.aClass = Locker1.class;
                        intent = new Intent(LockerMain.this, Locker1.class);
                        startActivity(intent);
                        break;
                    case 1:
                        saveVar.memo_now = "memo2";
                        saveVar.aClass = Locker2.class;
                        intent = new Intent(LockerMain.this, Locker2.class);
                        startActivity(intent);
                        break;
                    case 2:
                        saveVar.memo_now = "memo3";
                        saveVar.aClass = Locker3.class;
                        intent = new Intent(LockerMain.this, Locker3.class);
                        startActivity(intent);
                        break;
                    case 3:
                        saveVar.memo_now = "memo4";
                        saveVar.aClass = Locker4.class;
                        intent = new Intent(LockerMain.this, Locker4.class);
                        startActivity(intent);
                        break;
                    case 4:
                        saveVar.memo_now = "memo5";
                        saveVar.aClass = Locker5.class;
                        intent = new Intent(LockerMain.this, Locker5.class);
                        startActivity(intent);
                        break;
                    case 5:
                        saveVar.memo_now = "memo6";
                        saveVar.aClass = Locker6.class;
                        intent = new Intent(LockerMain.this, Locker6.class);
                        startActivity(intent);
                        break;
                    case 6:
                        saveVar.memo_now = "memo7";
                        saveVar.aClass = Locker7.class;
                        intent = new Intent(LockerMain.this, Locker7.class);
                        startActivity(intent);
                        break;
                    case 7:
                        saveVar.memo_now = "memo8";
                        saveVar.aClass = Locker8.class;
                        intent = new Intent(LockerMain.this, Locker8.class);
                        startActivity(intent);
                        break;
                    case 8:
                        saveVar.memo_now = "memo9";
                        saveVar.aClass = Locker9.class;
                        intent = new Intent(LockerMain.this, Locker9.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    void M_onTouch(RelativeLayout b) {

        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setBackgroundResource(R.drawable.rectangle_design2_1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.setBackgroundResource(R.drawable.rectangle_design2);
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - presstime;

        if (0 <= intervalTime && finishtimeed >= intervalTime) {
            finish();
        } else {
            presstime = tempTime;
            Toast.makeText(getApplicationContext(), "한번 더 누르시면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locker_main);

        info = (TextView) findViewById(R.id.info);

        b[0] = (RelativeLayout) findViewById(R.id.button1);
        b[1] = (RelativeLayout) findViewById(R.id.button2);
        b[2] = (RelativeLayout) findViewById(R.id.button3);
        b[3] = (RelativeLayout) findViewById(R.id.button4);
        b[4] = (RelativeLayout) findViewById(R.id.button5);
        b[5] = (RelativeLayout) findViewById(R.id.button6);
        b[6] = (RelativeLayout) findViewById(R.id.button7);
        b[7] = (RelativeLayout) findViewById(R.id.button8);
        b[8] = (RelativeLayout) findViewById(R.id.button9);


        for (int i = 0; i < 9; i++) {
            M_onClick(b[i], i);
            M_onTouch(b[i]);
        }
    }
}
