package com.example.miniphotoshop_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

// 기획
// 확대, 축소, 회전, 밝게 하기, 어둡게 하기, 회색 영상 (GrayScale) 등 6개 기능
public class MainActivity extends AppCompatActivity {

    ImageButton ibZoomin, ibZoomout, ibrotate, ibbrighter, ibdarkly, ibgray;
    MyGraphicView graphicView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("미니 포토샵");

        FrameLayout pictureLayout = (FrameLayout) findViewById(R.id.pictureLayout);
        graphicView = (MyGraphicView) new MyGraphicView(this);
        pictureLayout.addView(graphicView);

        // 앱 아이콘 설정
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.photoshop_icon);

        // clickIcons() 메서드 호출
        clickIcons();
    }

    // 사이즈 조절 값을 위한 변수 선언
    static float scaleX = 1, scaleY = 1;
    // 회전에 사용될 변수
    static float angle = 0;
    // 색상 배수로 사용될 변수
    static float color = 1;
    // 채도에 사용될 변수
    static float satur = 1;

    // 아이콘 클릭시 OnClick 메서드 구현
    private void clickIcons(){

        // 1. 줌 인 버튼
        ibZoomin = (ImageButton) findViewById(R.id.ibZoomin);
        ibZoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX + 0.2f;
                scaleY = scaleY + 0.2f;
                // 확대를 위해서는 onDraw()메서드를 다시 호출해야하는데, 이를 invalidate 메서드가 대신 호출
                graphicView.invalidate();
            }
        });

        // 2. 줌 아웃 버튼
        ibZoomout = (ImageButton) findViewById(R.id.ibZoomout);
        ibZoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scaleX = scaleX - 0.2f;
                scaleY = scaleY - 0.2f;
                graphicView.invalidate();
            }
        });

        // 3. 회전 버튼
        ibrotate = (ImageButton) findViewById(R.id.ibrotate);
        ibrotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angle = angle + 20;
                graphicView.invalidate();
            }
        });

        // 4. 밝게 버튼
        ibbrighter = (ImageButton) findViewById(R.id.ibbrighter);
        ibbrighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                color = color + 0.2f;
                graphicView.invalidate();
            }
        });

        // 5. 어둡게 버튼
        ibdarkly = (ImageButton) findViewById(R.id.ibdarkly);
        ibdarkly.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                color = color - 0.2f;
                graphicView.invalidate();
            }
        });

        // 6. 회색조 버튼 >> 채도 값이 0이되면 회색조, 0~1이면 채도가 낮게, 1이상이면 채도가 높게 출력
        ibgray = (ImageButton) findViewById(R.id.ibgray);
        ibgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(satur == 0) satur = 1;
                else satur = 0;
                graphicView.invalidate();
            }
        });
    }

    private static class MyGraphicView extends View {

        public MyGraphicView(Context context) {
            super(context);
        }

        // 화면에 이미지 출력(그리기) 메서드 구현
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 1, 2. 화면(뷰)의 중앙을 구하는 코드
            int cenX = this.getWidth() / 2;
            int cenY = this.getHeight() / 2;
            canvas.scale(scaleX, scaleY, cenX, cenY);

            // 3. 회전 코드
            canvas.rotate(angle, cenX, cenY);

            // 4, 5. 밝게하기 코드
            Paint paint = new Paint();
            // RGB값을 전역변수로 설정
            float[] array = {color   , 0     , 0     , 0     , 0 ,
                    0       , color , 0     , 0     , 0 ,
                    0       , 0     , color , 0     , 0 ,
                    0       , 0     , 0     , 1     , 0  };
            ColorMatrix cm = new ColorMatrix(array);

            // 6. 어둡게하기 코드 >> 채도를 바꾸는 setSaturation은 그 앞 설정된 컬러 매트릭스 값이 무시된다는 것
            //                  >> 즉 회색영상으로 바꾼후로는 밝게하기, 어둡게하기 아이콘이 동작하지 않음
            if (satur == 0) cm.setSaturation(satur);

            paint.setColorFilter(new ColorMatrixColorFilter(cm));

            Bitmap picture = BitmapFactory.decodeResource(getResources(), R.drawable.dog_2);

            // 이미지 중앙 정렬 코드
            int picX = (this.getWidth() - picture.getWidth()) / 2;
            int picY = (this.getHeight() - picture.getHeight()) / 2;
            canvas.drawBitmap(picture, picX, picY, paint);

            picture.recycle();
        }
    }
}