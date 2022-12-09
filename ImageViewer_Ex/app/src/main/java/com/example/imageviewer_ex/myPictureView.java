package com.example.imageviewer_ex;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

// 그림파일을 보여주는 용도의 위젯을 새롭게 만듦 - 커스텀 클래스(위젯)
public class myPictureView extends View {

    // 이미지 경로를 전역변수로 설정
    String imagePath = null;

    public myPictureView(Context context, @Nullable AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 경로가 지정되어있는 한
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            canvas.drawBitmap(bitmap, 0, 0, null);
            bitmap.recycle();
        }
    }
}
