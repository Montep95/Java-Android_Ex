package com.example.sdfileread_ex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 변수 선언
        Button btnRead;
        final EditText edtSD;

        // 변수와 개체 매칭
        btnRead = (Button) findViewById(R.id.btnRead);
        edtSD = (EditText) findViewById(R.id.edtSD);

        // 액세스 허용 창 출력
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    // 파일 입력 스트림 생성 시 절대경로를 지정
                    FileInputStream inFs = new FileInputStream("/storage/emulated/0/sd_test.txt");

                    // txt크기 지정을 inFs에서 읽어들일 수 있는 만큼 지정 (동적 크기)
                    byte[] txt = new byte[inFs.available()];

                    inFs.read(txt);
                    edtSD.setText(new String(txt));
                    inFs.close();

                } catch (IOException e){}
            }
        });
    }
}