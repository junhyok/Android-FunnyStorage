package com.example.funnystorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public  class Edit extends AppCompatActivity {
    Button button_edit_finish;
    EditText editText_edit_title, editText_content_write_url;
    String video_title,image_title;
    String video_position,image_position;
    SharedPreferences sharedPreferences;
    String readData;
    private ArrayAdapter adapter;


    ArrayList<Contents> contents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        editText_content_write_url = (EditText) findViewById(R.id.editText_content_write_url);
        button_edit_finish = findViewById(R.id.button_edit_finish);     //수정 완료 버튼
        editText_edit_title = findViewById(R.id.editText_edit_title);   //제목 수정 적는 칸
        final MainAdapter adapter = new MainAdapter();

        //보내온 인텐트 데이터 가져오기
        final Intent intent = getIntent();
        video_title = intent.getStringExtra("video_title");
        video_position = intent.getStringExtra("video_position");

        //수정완료 버튼
        button_edit_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
                String readData = sharedPreferences.getString("video_item", "");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                JSONObject jsonObject = new JSONObject();

                //제목 수정한거 비디오 타이틀에 넣기
                try {
                    JSONArray jsonArray = new JSONArray(readData);
                    //수정 텍스트 창의 데이터를 읽어와 String으로 변환하고 Object에 넣어준다.
                    jsonObject.put("video_title", editText_edit_title.getText().toString());

                    //선택한 비디오 포지션을 변수 position에 넣기
                    JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(video_position));

                    //선택한 아이템의 포지션에 속해있는 url_video 데이터를 jsonObject의 url_video에 넣기. 이렇게 한 이유는 url은 바꾸지 않으려기 때문이다.
                    jsonObject.put("url_video",position.getString("url_video") );

                    //수정한 jsonObject를 수정하려는 아이템에 넣기
                    jsonArray.put(Integer.parseInt(video_position), jsonObject);

                    //갱신 , 어댑터에서 RecyclerView에 반영하도록 한다.
                    editor.putString("video_item", jsonArray.toString());
                    editor.apply();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                e.printStackTrace();
            }
                Intent intent = new Intent(Edit.this, FunnyVideo.class);
                //보내는 Activity 시작
                Toast.makeText(Edit.this, "내용이 수정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }
    //버튼 클릭메소드 > FunnyVideo로 화면 전환 인텐트
    public void clickMethod_video_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);
        startActivity(intent);
    }
}

/*SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
                readData = sharedPreferences.getString("video_item", "");

                count=adapter.getCount();
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(readData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.remove(position);

                SharedPreferences.Editor dataEditor = sharedPreferences.edit();

                dataEditor.putString("video_item", jsonArray.toString());
                dataEditor.apply();


                adapter.notifyDataSetChanged();

*/

/*@Override
            public void modifyData(View v, int position){
                pos=position;

            }*//*


            @Override
            public void onItemClick(View v,int position) {


                //비디오 제목 editText의 정보를 받아와서 String으로 바꿈.
                edit_title_video= editText_edit_title.getText().toString();
               */
/*
 int position= viewHolder.getAdapterPosition();

                // 숫자로 변환 > Integer.parseInt
                //입력된 데이터 받기 - EditText의 text를 저장
                SharedPreferences sharedPreferences = getSharedPreferences("video_item",MODE_PRIVATE);
                String readData = sharedPreferences.getString("video_item", "");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                JSONArray jsonArray = null;
                JSONObject jsonObject = null;

                try {
                    jsonObject = new JSONObject(String.valueOf(MainAdapter.position()));
                    jsonArray = new JSONArray(readData);
                    jsonObject.put("video_title", edit_title_video);
                    jsonArray.put(position,jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.putString("video_item", jsonArray.toString());
                editor.apply();

                //보내는 Activity 시작
               //setResult(1, i);
                Toast.makeText(Edit.this, "내용이 수정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });

    }
    public void clickMethod_video_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);
        startActivity(intent);
    }
*/

/*//video_title 데이터를 인텐트를 사용하여 video_title 변수에다가 가져온다.
                video_title = intent.getStringExtra("video_title");

                ArrayList<Object> contents = new ArrayList<>();
                contents.add(contents);
*/

     /*   //어댑터 아이템 클릭리스너
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
            }
        });*/