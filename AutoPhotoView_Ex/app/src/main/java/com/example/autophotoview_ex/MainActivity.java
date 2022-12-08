package com.example.autophotoview_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPrev, btnNext;
        Button btnAutoStart, btnAutoStop;
        final ViewFlipper vFlipper;

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);

        btnAutoStart = (Button) findViewById(R.id.btnAutoStart);
        btnAutoStop = (Button) findViewById(R.id.btnAutoStop);

        vFlipper = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // 이전화면 클릭 시 이전화면으로 넘어가는 뷰플리퍼의 메서드
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.showPrevious();
            }
        });

        // 다음화면 클릭 시 다음화면으로 넘어가는 뷰플리퍼의 메서드
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.showNext();
            }
        });

        // startFlipping은 화면 넘김 시작 메서드
        // setFlipInterval은 화면 넘김의 간격 = 2000(2초) >> 밀리초 기준
        btnAutoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.startFlipping();
                vFlipper.setFlipInterval(2000);
            }
        });

        // stopFlipping은 화면 넘김 정지 메서드이다.
        btnAutoStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vFlipper.stopFlipping();
            }
        });

    }
}