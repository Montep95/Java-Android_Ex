package com.example.simplediary_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    // 전역변수로 설정
    DatePicker dp;
    EditText edtDiary;
    Button btnWrite;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("간단 일기장");

        dp = (DatePicker) findViewById(R.id.datePicker1);
        edtDiary = (EditText) findViewById(R.id.edtDiary);
        btnWrite = (Button) findViewById(R.id.btnWrite);

        // 캘린더 클래스를 이용해 현재 날짜의 연, 월, 일을 구한 후 >> 데이트피커를 초기화
        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        // 데이트피커 변수인 dp를 초기화 (init 메서드)
        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // 파일 저장 형식(이름) >> "연_월_일.txt"
                fileName = Integer.toString(year) + "_" +
                            Integer.toString(monthOfYear + 1) + "_" +
                            Integer.toString(dayOfMonth) + ".txt";

                String str = readDiary(fileName);

                // 에디트텍스트의 메서드 setText()를 활용해 file을 읽어들여 에디트텍스트 변수에 저장
                edtDiary.setText(str);
                // 초기에 disable 해놓았던 Button을 활성화시킴 >> onDateChanged 메서드가 실행되었을때이므로 데이트피커를 조정하면 활성화됨
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    // 에디트텍스트의 텍스트내용을 getText()메서드를 통해 가져오고 toString으로 문자열 변환 후 문자열 str에 대입
                    String str = edtDiary.getText().toString();

                    outFs.write(str.getBytes());
                    outFs.close();

                    // 팝업메시지는 fileName뒤로 "이 저장됨" 문자열을 붙여 출력
                    Toast.makeText(getApplicationContext(), fileName+" 이 저장됨", Toast.LENGTH_SHORT).show();

                } catch (IOException e){ }
            }
        });
    }

    String readDiary(String fName){
        // 문자열(diaryStr) 초기화
        String diaryStr = null;

        FileInputStream inFs;
        try{
            inFs = openFileInput(fName);
            byte[] txt = new byte[500]; // 일기장의 쓰기 가능 범위 지정
            inFs.read(txt);
            inFs.close();

            // trim()메서드로 txt파일의 앞뒤 공백 제거 후 대입
            diaryStr = (new String(txt)).trim();
            btnWrite.setText("수정하기");
        } catch(IOException e){ // 파일이 없을 경우 오류발생 예외처리
            // 일기 내용이 없는 상태이므로, 화면에는 일기없음이 표시되게하고,
            //                          버튼의 텍스트는 '새로 저장'으로 변경
            edtDiary.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }

        return diaryStr;
    }
}