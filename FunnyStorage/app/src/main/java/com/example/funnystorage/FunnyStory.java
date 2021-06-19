package com.example.funnystorage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FunnyStory extends AppCompatActivity {
    private TextView textView_story_title;
    RecyclerView recyclerView;
    ImageAdapter adapter;
    SharedPreferences sharedPreferences;
    ArrayList<ContentsStory> contents;
    Context context;
    String readData,url_image_id;
    private TextView textView_image_title;
    private ImageView imageView_story_image;
    EditText editText_content_title, editText_edit_title;
    TextView banner_tip ;
    private static Handler handler ;
    int num = 0;

     @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_story);

        contents = new ArrayList<>();
        editText_edit_title = findViewById(R.id.editText_edit_title);
        editText_content_title = findViewById(R.id.editText_content_title);
        textView_story_title = (TextView) findViewById(R.id.item_image_title);
        //imageView_story_image = findViewById(R.id.item_image_image); //item 클래스 이미지 값

        recyclerView = findViewById(R.id.recyclerview_story);   //FunnyVideo 클래스 리싸이클러뷰 창

        //RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //FunnyVideo로 화면전환
        Button imageButton = (Button) findViewById(R.id.button_move_video);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);
                startActivity(intent);
            }
        });

        Button moveButton_write_video = (Button) findViewById(R.id.button_move_write_story);
        //내용 추가 버튼 눌렀을 때 WirteStory로 화면전환
        moveButton_write_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteVideo.class);
                startActivity(intent);
            }
        });

       /* //텍스트뷰 제목 인텐트 받아서 데이터 올리기
        Intent intent = getIntent();
        String image_title = intent.getStringExtra("image_title");
        if(image_title !=null) {
            textView_story_title.setText(image_title);
        }*/

        Thread tipThread=new Thread(new Runnable(){
            @Override
            public void run(){
                String tip_1 ="Tip.움짤과 이미지를 저장하는 공간입니다";
                String tip_2 ="Tip.이미지URL 가져오실 때는 자료가 올려진 위치로 가서 가져오셔야 합니다";
                String tip_3 ="Tip.공유하기를 통해 카카오톡으로 자료를 공유할 수 있습니다";
                String tip_4 ="Tip.게시글을 편집하시려면 제목을 꾹 눌러주세요";
                String tip_5 ="Tip.이미지를 더 크게 보시려면 해당 이미지를 눌러주세요";
                banner_tip = findViewById(R.id.banner_tip) ;

                while (true){
                    if(num == 0) {
                        banner_tip.setText(tip_1);
                        num++;
                    }
                    else if(num==1) {
                        banner_tip.setText(tip_2);
                        num++;
                    }
                    else if(num==2) {
                        banner_tip.setText(tip_3);
                        num++;
                    }
                    else if(num==3) {
                        banner_tip.setText(tip_4);
                        num++;
                    }
                    else if(num==4) {
                        banner_tip.setText(tip_5);
                        num=0;
                    }
                    try {
                        Thread.sleep(5000);     //5초
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        tipThread.start();




        // SharedPreferences 생성
        sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);

        //video_item 폴더에서 "video_item"로 저장되어 있는 리스트 항목을 읽는다.
        readData = sharedPreferences.getString("image_item", "");
        Log.d("Tag","readData 검사");
        //SharedPreferences 에 "video_item"에 저장되어 있는 데이터를 모두 읽어온다.
        //데이터 형식은 Array
        try {
            JSONArray jsonArray =  new JSONArray(readData);
            //SharedPreferences 에서 불러온 데이터를 JSONArray 형식으로 읽는다.
            for (int i = 0; i < jsonArray.length(); i++) {
                //jsonArray 에 포함되어 있는 리스트의 갯수를 파악하여 객체형태로 변경한다.
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                contents.add(new ContentsStory(
                        jsonObject.getString("image_title"),
                        jsonObject.getString("url_image")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //로그 사용법 Log.d("video_title", jsonObject.getString("video_title"));

        //RecyclerView의 Adapter 세팅
        adapter = new ImageAdapter(contents, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

      /*  //WriteStory에서 제목 받아오기
        Intent intent = getIntent() ;
        TextView textViewTitle = (TextView) findViewById(R.id.textView_story_title1) ;
        String name = intent.getStringExtra("story_title") ;
        if (name != null)
            textViewTitle.setText(name) ;*/

    }
    //EnterStory로 화면전환
    public void clickMethod_story(View view) {

        Intent intent = new Intent(getApplicationContext(), EnterStory.class);

        startActivity(intent);
    }
}
    /*
    //toolbar 관련
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_add:
                Intent intent = new Intent(getApplicationContext(),WriteStory.class);
                startActivity(intent);
                //비디오 작성창 선택
                break;
            case R.id.content_delete:
                //내용 삭제
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/



