package com.example.timepicker_ex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ChoiceFormat;

public class MainActivity extends AppCompatActivity {

    // 변수들을 전역변수로 설정
    Chronometer chrono;
    Button btnStart, btnEnd;
    RadioButton rdoCal, rdoTime;
    CalendarView calView;
    TimePicker tPicker;
    TextView tvYear, tvMonth, tvDay, tvHour, tvMinute;

    // 캘린더 뷰에서 선택할 연, 월, 일에 해당하는 변수
    int selectYear, selectMonth, selectDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("시간 예약");

        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);

        chrono = (Chronometer) findViewById(R.id.chronometer1);

        rdoCal = (RadioButton) findViewById(R.id.rdoCal);
        rdoTime = (RadioButton) findViewById(R.id.rdoTime);

        tPicker = (TimePicker) findViewById(R.id.timePicker1);
        calView = (CalendarView) findViewById(R.id.calendarView1);

        tvYear = (TextView) findViewById(R.id.tvYear);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        tvDay = (TextView) findViewById(R.id.tvDay);
        tvHour = (TextView) findViewById(R.id.tvHour);
        tvMinute = (TextView) findViewById(R.id.tvMinute);

        // 타임피커와 캘린더뷰가 보이지 않게 설정
        tPicker.setVisibility(View.INVISIBLE);
        calView.setVisibility(View.INVISIBLE);

        /*
        기능 1.
        라디오버튼 클릭시 캘린더뷰와 타임피커 중 하나만 보이도록
        '클릭 이벤트 리스너'
        */
        rdoCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPicker.setVisibility(View.INVISIBLE);
                calView.setVisibility(View.VISIBLE);
            }
        });
        rdoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tPicker.setVisibility(View.VISIBLE);
                calView.setVisibility(View.INVISIBLE);
            }
        });

        /*
        기능 2.
        1) 예약 시작을 누르면 크로노미터가 작동/정지하고, (누를때마다 0으로 초기화)
        2) 타이머 시작 시 글자 빨간색, 정지 시 파란색 설정
         */
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                // 일반적으로 리얼타임으로 시간이 가게끔 설정 (setBase, elapsedRealTime)
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();
                chrono.setTextColor(Color.RED);
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chrono.stop();
                chrono.setTextColor(Color.BLUE);
            }
        });

        // 3) 캘린더뷰를 클릭하고 연, 월, 일을 선택하면 전역변수에 대입되고
        // 4) 그 대입된 전역변수로 '예약완료' 클릭 시 맨 아래 텍스트뷰 채우기
        calView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                selectYear = year;
                selectMonth = month + 1; // 월은 0부터 시작하므로 +1로 초기화 후 더해주기
                selectDay = dayOfMonth;
            }
        });

        // 캘린더뷰에서 선택한 것 >> 텍스트뷰에 적용
        tvYear.setText(Integer.toString(selectYear));
        tvMonth.setText(Integer.toString(selectMonth));
        tvDay.setText(Integer.toString(selectDay));

        // 타임피커에서 설정한 것 >> 텍스트뷰에 적용
        tvHour.setText(Integer.toString(tPicker.getCurrentHour()));
        tvMinute.setText(Integer.toString(tPicker.getCurrentMinute()));
    }
}