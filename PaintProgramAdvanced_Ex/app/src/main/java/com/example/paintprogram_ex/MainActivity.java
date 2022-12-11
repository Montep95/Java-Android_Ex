package com.example.paintprogram_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 전역변수 = 메뉴선택이 선인지 원인지 구분하기 위함, final 변수를 설정할때는 대문자로 적어주는것이 좋음
    final static int LINE = 1, CIRCLE = 2, RECT = 3;
    final static int RED = 4, GREEN = 5, BLUE = 6;
    static int color= RED;
    static int curShape = LINE; // 기본값은 선으로 설정

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setContentView(new MyGraphicView(this));

        setTitle("간단 그림판 (업데이트)");
    }

    // 옵션 메뉴 만들기
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // 메뉴추가의 인자는 = 그룹ID / 메뉴ID / 순서 / 원하는 텍스트
        menu.add(0, 1, 0, "선 그리기");
        menu.add(0, 2, 0, "원 그리기");
        menu.add(0, 3, 0, "사각형 그리기");

        SubMenu subMenu = menu.addSubMenu("색상 변경 >>");
        subMenu.add(0, 4, 0, "빨강");
        subMenu.add(0, 5, 0, "초록");
        subMenu.add(0, 6, 0, "파랑");

        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        // switch문을 활용하여 item의 아이템 아이디를 가져와서 case마다 일치하는 값이 있으면 처리
        switch(item.getItemId()){
            case LINE:
                curShape = LINE;
                return true;
            case CIRCLE:
                curShape = CIRCLE;
                return true;
            case RECT:
                curShape = RECT;
                return true;
            case RED:
                color = RED;
                return true;
            case GREEN:
                color = GREEN;
                return true;
            case BLUE:
                color = BLUE;
                return true;
        }
        // return super.onOptionsItemSelected(item);
        return false;
    }

    private static class MyGraphicView extends View {
        // 동적으로 그린 도형들이 소멸되지 않도록 지정
        ArrayList<MyShape> ShapeArrayList = new ArrayList<>();
        MyShape currentShape;

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
                //case MotionEvent.ACTION_MOVE:
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
            // paint.setColor(Color.RED);

            // 동적 저장
            currentShape = new MyShape(curShape, startX, startY, stopX, stopY, paint);
            ShapeArrayList.add(currentShape);
            for (MyShape cshape : ShapeArrayList)
                draw_shape(cshape, canvas);
            if (currentShape != null)
                draw_shape(currentShape, canvas);
        }

        public void draw_shape(MyShape myshape, Canvas canvas){


            // curShape가 (메뉴선택에서)
            switch(myshape.shape_type){
                // '선'이 선택되었으면 drawLine()
                case LINE:
                    canvas.drawLine(myshape.startX, myshape.startY, myshape.stopX, myshape.stopY, myshape.paint);
                    break;
                case CIRCLE:
                    // 시작과 끝점의 거리를 계산 후 radius에 대입
                    int radius = (int) Math.sqrt(Math.pow(myshape.stopX - myshape.startX, 2) + Math.pow(myshape.stopY - myshape.startY, 2));
                    canvas.drawCircle(myshape.startX, myshape.startY, radius, myshape.paint);
                    break;
                case RECT:
                    Rect rect = new Rect(myshape.startX, myshape.startY, myshape.stopX, myshape.stopY);
                    canvas.drawRect(rect, myshape.paint);
                    break;
            }
        }

        // 그려진 도형들의 정보를 갖고있는 클래스
        private static class MyShape{
            int shape_type, startX, startY, stopX, stopY;
            Paint paint;

            public MyShape(int shape_type, int startX, int startY, int stopX, int stopY, Paint paint){
                this.shape_type = shape_type;
                this.startX = startX;
                this.startY = startY;
                this.stopX = stopX;
                this.stopY = stopY;
                this.paint = paint;

                switch(color){
                    case RED:
                        paint.setColor(Color.RED);
                        break;
                    case GREEN:
                        paint.setColor(Color.GREEN);
                        break;
                    case BLUE:
                        paint.setColor(Color.BLUE);
                        break;
                }
            }


        }

    }
}