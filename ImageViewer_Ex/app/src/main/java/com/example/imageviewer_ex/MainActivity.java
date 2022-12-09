package com.example.imageviewer_ex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // 전역변수 설정
    Button btnPrev, btnNext;
    myPictureView myPicture;

    // 이미지 파일의 순번으로 사용
    int curNum = 1;

    // SD카드에서 읽어올 이미지 파일의 배열
    File[] imageFiles;

    // SD카드에서 읽어올 파일명
    String imageFname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("간단 이미지 뷰어");

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnNext = (Button) findViewById(R.id.btnNext);
        myPicture = (myPictureView) findViewById(R.id.myPictureView1);

        // SD카드에서 파일을 읽어 listFiles()메서드를 통해 '배열'을 만듦
        imageFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();
        // 파일의 이름을 추출 후
        imageFname = imageFiles[curNum].toString();
        // myPicture 클래스의 imagePath에 전달
        myPicture.imagePath = imageFname;

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(curNum <= 1){
                    Toast.makeText(getApplicationContext(), "첫번째 그림입니다", Toast.LENGTH_SHORT).show();
                } else{
                    // 이전그림 메서드이므로 -1 설정
                    curNum--;
                    imageFname = imageFiles[curNum].toString();
                    myPicture.imagePath = imageFname;
                    // myPicture 클래스의 onDraw() 호출
                    myPicture.invalidate();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 배열이 0부터 시작하므로 '그림의 개수 - 1' 설정
                if(curNum >= imageFiles.length - 1){
                    Toast.makeText(getApplicationContext(), "마지막 그림입니다", Toast.LENGTH_SHORT).show();
                }else{
                    curNum++;
                }
            }
        });
    }
}