package com.example.paintprogram_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // 전역변수 = 메뉴선택이 선인지 원인지 구분하기 위함, final 변수를 설정할때는 대문자로 적어주는것이 좋음
    final static int LINE = 1, CIRCLE = 2;
    static int curShape = LINE; // 기본값은 선으로 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(new MyGraphicView(this));

        setTitle("간단 그림판");
    }

    private static class MyGraphicView extends View {

        // 시작점과 끝점 좌표를 저장하기위한 멤버변수 만들기
        int startX = -1, startY = -1, stopX = -1, stopY = -1;

        public MyGraphicView(Context context) {
            super(context);
        }

        // 화면터치가 발생했을 경우 처리하는 메서드
        @Override
        public boolean onTouchEvent(MotionEvent event){
            switch(event.getAction()){
                // 화면이 눌렸을때 처리 >> 선의 시작점 or 원의 중심점 위치를 기억
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    break;

                // 화면이 눌리고 이동한 다음 떼어졌을때의 처리 >> case문 특성상 순서대로 진행되는 것을 이용
                case MotionEvent.ACTION_MOVE:
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();

                    // invalidate를 호출하면 화면이 무효화되고, onDraw()메서드를 자동실행
                    this.invalidate();
                    break;
            }
            return true;
        }

        // onDraw를 오버라이드해서 호출하는 방법은 protected를 입력한 후 자동완성으로부터 찾아 메서드 완성
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);

            // curShape가 (메뉴선택에서)
            switch(curShape){
                // '선'이 선택되었으면 drawLine()
                case LINE:
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    break;
                case CIRCLE:
                    // 시작과 끝점의 거리를 계산 후 radius에 대입
                    int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
                    canvas.drawCircle(startX, startY, radius, paint);
                    break;
            }
        }

    }

    // 옵션 메뉴 만들기
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "선 그리기");
        menu.add(0, 2, 0, "원 그리기");

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        // switch문을 활용하여 item의 아이템 아이디를 가져와서 case마다 일치하는 값이 있으면 처리
        switch(item.getItemId()){
            case 1:
                curShape = LINE;
                return true;
            case 2:
                curShape = CIRCLE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}