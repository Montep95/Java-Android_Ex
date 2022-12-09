package com.example.filesystem_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWrite, btnRead;

        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead = (Button) findViewById(R.id.btnRead);

        // 파일을 쓰는 클릭 메서드 (파일 저장)
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 예외처리를 해주는데, 파일 입출력처럼 '입출력'을 다루는 부분에서는 IOException (Input, Output Exception)처리를 해주어야함
                try{
                    FileOutputStream outFs = openFileOutput("file_test.txt", Context.MODE_PRIVATE);
                    String str = "쿡북 안드로이드";
                    // String으로 제목을 받아와 outFs의 write메서드로 설정
                    outFs.write(str.getBytes());
                    // 처리를 해주었으면 close() 메서드로 닫아주어야함
                    outFs.close();
                    // 팝업문구를 생성 Toast의 makeText 메서드를 설정 후 그 메서드의 .show()메서드로 출력함
                    Toast.makeText(getApplicationContext(), "file_test.txt가 생성됨", Toast.LENGTH_SHORT).show();
                } catch(IOException e) { }
            }
        });

        // 파일을 읽는 클릭 메서드 (파일 불러오기)
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileInputStream inFs = openFileInput("file_test.txt");
                    byte[] txt = new byte[30]; // byte 30길이의 배열 txt를 생성함

                    // inFs로 읽어온 데이터를 txt 배열에 저장
                    inFs.read(txt);

                    // 읽어온 데이터가 저장된 txt를 문자열로 변환
                    String str = new String(txt);
                    Toast.makeText(getApplicationContext(), "file_test.txt가 생성됨", Toast.LENGTH_SHORT).show();

                    inFs.close();

                } catch(IOException e) {
                    // 불러올 파일이 없는 경우 IOException발생 후 Toast로 "파일 없음" 문구 출력
                    Toast.makeText(getApplicationContext(), "파일 없음", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}