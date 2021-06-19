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

public class EditImage extends AppCompatActivity {
    Button button_editImage_finish;
    EditText editText_editImage_title, editText_content_write_url;
    String image_title;
    String image_position;
    SharedPreferences sharedPreferences;
    String readData;
    private ArrayAdapter adapter;

    ArrayList<Contents> contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);
//        editText_content_write_url = (EditText) findViewById(R.id.editText_content_write_url);
        button_editImage_finish = findViewById(R.id.button_editImage_finish);     //수정 완료 버튼
        editText_editImage_title = findViewById(R.id.editText_editImage_title);   //제목 수정 적는 칸
        final ImageAdapter adapter = new ImageAdapter();

        //보내온 인텐트 데이터 가져오기
        final Intent intent = getIntent();
        image_title = intent.getStringExtra("image_title");
        image_position=intent.getStringExtra("image_position");

        //수정완료 버튼
        button_editImage_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
                String readData = sharedPreferences.getString("image_item", "");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                JSONObject jsonObject = new JSONObject();

                //제목 수정한거 비디오 타이틀에 넣기
                try {
                    JSONArray jsonArray = new JSONArray(readData);
                    //수정 텍스트 창의 데이터를 읽어와 String으로 변환하고 Object에 넣어준다.
                    jsonObject.put("image_title", editText_editImage_title.getText().toString());

                    //선택한 비디오 포지션을 변수 position에 넣기
                    JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(image_position));

                    //선택한 아이템의 포지션에 속해있는 url_video 데이터를 jsonObject의 url_video에 넣기. 이렇게 한 이유는 url은 바꾸지 않으려기 때문이다.
                    jsonObject.put("url_image",position.getString("url_image") );

                    //수정한 jsonObject를 수정하려는 아이템에 넣기
                    jsonArray.put(Integer.parseInt(image_position), jsonObject);

                    //갱신 , 어댑터에서 RecyclerView에 반영하도록 한다.
                    editor.putString("image_item", jsonArray.toString());
                    editor.apply();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(EditImage.this, FunnyStory.class);
                //보내는 Activity 시작
                Toast.makeText(EditImage.this, "내용이 수정되었습니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }
    //버튼 클릭메소드 > FunnyVideo로 화면 전환 인텐트
    public void clickMethod_image_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyStory.class);
        startActivity(intent);
    }
}

