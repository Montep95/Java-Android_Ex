package com.example.basicgraphic_ex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

public class MyGraphicView extends View {

    // 그래픽의 기본 Java코드
    public MyGraphicView(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        // 페인트 선언
        Paint paint = new Paint();

        // 안티앨리어싱 설정
        paint.setAntiAlias(true);

        // 1. 일반 선 그리기 예제
        paint.setColor(Color.GREEN);
        // (20,20) 좌표부터 (600,20) 좌표까지 선을 그림
        canvas.drawLine(20, 20 , 600, 20, paint);

        // 2. 외곽선 두께 설정 예제
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        canvas.drawLine(20, 60, 600, 20, paint);

        // 3. 속이 찬 사각형 그리기 예제, FILL(채우기 스타일),
        paint.setColor(Color.RED);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        Rect rect1 = new Rect(20, 100, 20 + 200, 100 + 200);
        canvas.drawRect(rect1, paint);

        // 4. 빈 사각형 그리기 예제
        paint.setStyle(Paint.Style.STROKE);
        Rect rect2 = new Rect(260, 100, 260 + 200, 100 + 200);
        canvas.drawRect(rect2, paint);

        // 5. 모서리가 둥근 사각형 그리기 예제
        RectF rect3 = new RectF(500, 100, 500 + 200, 100 + 200);
        canvas.drawRoundRect(rect3, 40, 40, paint);

        // 6. 일반 원 그리기
        canvas.drawCircle(120, 440, 100, paint);

        // 7. 구불선 그리기
        paint.setStrokeWidth(10);
        Path path1 = new Path();
        path1.moveTo(20, 580);
        path1.lineTo(20 + 100, 580 + 100);
        path1.lineTo(20 + 200, 580);
        path1.lineTo(20 + 300, 580 + 100);
        path1.lineTo(20 + 400, 580);
        canvas.drawPath(path1, paint);

        paint.setStrokeWidth(0);
        paint.setTextSize(60);
        canvas.drawText("안드로이드", 20, 780, paint);
    }
}
