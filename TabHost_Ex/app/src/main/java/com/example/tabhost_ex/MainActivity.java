package com.example.tabhost_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

// 탭액티비티 사용시 경고발생을 방지하기 위한 코드 (없어도 문제X)
// @SuppressWarnings("deprecation")

// ★ 상속받는 클래스는 AppCompatActivity가 아닌 TabActivity여야함
public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        // ★ 탭호스트 사용시 정석적인 코드

        // 탭호스트의 태그와 표시될 문구 설정
        TabHost.TabSpec tabSpecSong = tabHost.newTabSpec("SONG").setIndicator("음악별");
        // 이 위젯을 아이디를 통해 뷰와 연결
        tabSpecSong.setContent(R.id.tabSong);
        // tabHost에 연결한 위젯을 추가
        tabHost.addTab(tabSpecSong);

        // 탭호스트의 태그와 표시될 문구 설정
    TabHost.TabSpec tabSpecArtist = tabHost.newTabSpec("ARTIST").setIndicator("가수별");
        // 이 위젯을 아이디를 통해 뷰와 연결
        tabSpecArtist.setContent(R.id.tabArtist);
        // tabHost에 연결한 위젯을 추가
        tabHost.addTab(tabSpecArtist);

        // 탭호스트의 태그와 표시될 문구 설정
    TabHost.TabSpec tabSpecAlbum = tabHost.newTabSpec("ALBUM").setIndicator("앨범별");
        // 이 위젯을 아이디를 통해 뷰와 연결
        tabSpecAlbum.setContent(R.id.tabAlbum);
        // tabHost에 연결한 위젯을 추가
        tabHost.addTab(tabSpecAlbum);

        // ★ 현재 탭을 설정
        tabHost.setCurrentTab(0);

    }
}