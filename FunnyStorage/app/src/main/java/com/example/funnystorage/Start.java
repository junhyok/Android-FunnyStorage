package com.example.funnystorage;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {
    private ImageView imageView_start_smile;
    SoundPool soundPool;
    SoundManager soundManager;
    Button button;
    boolean play;
    int playSoundId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);    //메인 스타트 레이아웃
        ImageView imageView_email_send = (ImageView) findViewById(R.id.imageView_email_send);
        imageView_email_send.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageView_email_send = new Intent(Intent.ACTION_SEND);
                imageView_email_send.setType("plain/text");
                String[] address = {"junhyok153@naver.com"};
                imageView_email_send.putExtra(Intent.EXTRA_EMAIL, address);
                imageView_email_send.putExtra(Intent.EXTRA_SUBJECT, "제목을 적어주세요");
                imageView_email_send.putExtra(Intent.EXTRA_TEXT, "문의/건의 사항을 보내주세요");
                startActivity(imageView_email_send);
            }
        });

        imageView_start_smile = (ImageView) findViewById((R.id.imageView_start_smile));
        //imageView_start_smile.setImageDrawable(getResources().getDrawable(R.drawable.smile));



        //롤리팝 이상 버전일 경우
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        } else {
            //롤리팝 이하 버전일 경우
            // new SoundPool(1번,2번,3번)
            // 1번 - 음악 파일 갯수
            // 2번 - 스트림 타입
            // 3번 - 음질
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        soundManager = new SoundManager(this, soundPool);
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                soundManager.addSound(0, R.raw.baby_smile_sound);       //음악 파일
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!play) {
                    playSoundId = soundManager.playSound(0);
                    play = true;
                } else {
                    soundManager.pauseSound(0);
                    play = false;
                }
            }
        });
        soundThread.start();

        Button imageButton = (Button) findViewById(R.id.button_start);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FunnyVideo로 화면전환
                Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);
                startActivity(intent);
            }
        });
    }
}


/*

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // 1. 툴바 사용 설정
    setSupportActionBar(toolbar)

    // 2. 툴바 왼쪽 버튼 설정
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)  // 왼쪽 버튼 사용 여부 true
    supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)  // 왼쪽 버튼 아이콘 설정
    supportActionBar!!.setDisplayShowTitleEnabled(false)    // 타이틀 안보이게 하기
}

    // 3.툴바 메뉴 버튼을 설정
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)       // main_menu 메뉴를 toolbar 메뉴 버튼으로 설정
        return true
        }

// 4.툴바 메뉴 버튼이 클릭 됐을 때 콜백
        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // 클릭된 메뉴 아이템의 아이디 마다 when 구절로 클릭시 동작을 설정한다.
        when(item!!.itemId){
        android.R.id.home->{ // 메뉴 버튼
        Snackbar.make(toolbar,"Menu pressed",Snackbar.LENGTH_SHORT).show()
        }
        R.id.menu_search->{ // 검색 버튼
        Snackbar.make(toolbar,"Search menu pressed",Snackbar.LENGTH_SHORT).show()
        }
        R.id.menu_account->{ // 계정 버튼
        Snackbar.make(toolbar,"Account menu pressed",Snackbar.LENGTH_SHORT).show()
        }
        R.id.menu_logout->{ // 로그아웃 버튼
        Snackbar.make(toolbar,"Logout menu pressed",Snackbar.LENGTH_SHORT).show()
        }
        }
        return super.onOptionsItemSelected(item)
        }}
*/
