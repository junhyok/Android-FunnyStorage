package com.example.funnystorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WriteVideo extends AppCompatActivity {
    private EditText editText_write_content_title, editText_content_write_url;
    private Button button_write_finish;
    private RadioButton radioButton_video, radioButton_image;
    private RadioGroup radioContentSelect;
    private View textView_content_url;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_video);
        editText_write_content_title = (EditText) findViewById(R.id.editText_content_title);
        editText_content_write_url = (EditText) findViewById(R.id.editText_content_write_url);
        textView_content_url = findViewById(R.id.textView_content_url);
        radioButton_image = findViewById(R.id.radioButton_image);
        radioButton_video = findViewById(R.id.radioButton_video);
        radioContentSelect = findViewById(R.id.radioContentSelect);
        button_write_finish = findViewById(R.id.button_write_finish);


        initIntent();

        radioContentSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {

                //작성창 동영상 버튼 눌렀을 때
                if (i == R.id.radioButton_video) {
                    editText_content_write_url.setVisibility(View.VISIBLE);
                    textView_content_url.setVisibility(View.VISIBLE);
                    // editText_video_image_url.setVisibility(View.INVISIBLE);
                    // textView_video_thumbnail.setVisibility(View.VISIBLE);
                    button_write_finish.setVisibility(View.VISIBLE);
                    button_write_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //조건은 key값의 데이터가 null값일 때 돌아간다  , key값의 데이터가 null이 아닐때 돌아간다로 2개 만들기
                            //shared > 어레이 > 오브젝트
                            String readData;
                            SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
                            readData = sharedPreferences.getString("video_item", "");
                            if (readData.isEmpty()) {

                                // SharedPreferences 생성
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("video_title", editText_write_content_title.getText().toString());
                                    jsonObject.put("url_video", editText_content_write_url.getText().toString());
                                    jsonArray.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.putString("video_item", jsonArray.toString());
                                editor.apply();
                            } else if(!readData.isEmpty()){

                                // SharedPreferences 생성
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONArray jsonArray = null;
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonArray = new JSONArray(readData);
                                    jsonObject.put("video_title", editText_write_content_title.getText().toString());
                                    jsonObject.put("url_video", editText_content_write_url.getText().toString());
                                   /* String id = jsonObject.getString("url_video");
                                    String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";  //유튜브 썸네일 불러오는 방법
                                    Glide.with(context).load(url).into();*/
                                    jsonArray.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.putString("video_item", jsonArray.toString());
                                editor.apply();
                            }
                            //화면전환 (intent 객체 생성)
                            Intent intent = new Intent(WriteVideo.this, FunnyVideo.class);

                            if (editText_write_content_title.getText().toString().equals("")) {
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                                dialogBuilder.setMessage("게시물을 잘 넣었는지 확인해 주세요");

                                //다이얼로그 확인 버튼
                                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.show();
                            } else {
                                //보내는 Activity 시작
                                startActivity(intent);
                                //데이터 추가 확인 토스트 띄우기
                                Toast.makeText(WriteVideo.this, "목록에 추가되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }

                ////확장자가 비디오인지 이미지인지 확인 > 아래 예시는 비디오일때
                /*if (extension.equals("mp4") || extension.equals("MP4") || extension.equals("MOV") || extension.equals("mov") || extension.equals("AVI") || extension.equals("avi") ||
                        extension.equals("MKV") || extension.equals("mkv") || extension.equals("WMV") || extension.equals("wmv") || extension.equals("TS") || extension.equals("ts") ||
                        extension.equals("TP") || extension.equals("tp") || extension.equals("FLV") || extension.equals("flv") || extension.equals("3GP") || extension.equals("3gp") ||
                        extension.equals("MPG") || extension.equals("mpg") || extension.equals("MPEG") || extension.equals("mpeg") || extension.equals("MPE") || extension.equals("mpe") ||
                        extension.equals("ASF") || extension.equals("asf") || extension.equals("ASX") || extension.equals("asx") || extension.equals("DAT") || extension.equals("dat") ||
                        extension.equals("RM") || extension.equals("rm")) {
                    return true;
                } else {
                    return false;
                }
*/

                  /*
                                //입력된 데이터 받기 - WriteVide클래스의 EditText를 video_title에다 저장
                                String video_title = editText_write_content_title.getText().toString();
                                String video_url = editText_content_write_url.getText().toString();

                                //Intent를 통하여 전달하는 방식 - EnterVideo로 전달할 인텐트 생성
                                intent.putExtra("video_title", video_title);
                                intent.putExtra("video_url",video_url);*/

                               /* //데이터 저장
                                SharedPreferences sharedPreferences = getSharedPreferences("video_item",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("video_title","");
                                editor.putString("video_url","");
                                editor.apply();*/

                //작성창 이미지 버튼 눌렀을 때
                else if (i == R.id.radioButton_image) {
                    // editText_video_image_url.setVisibility(View.GONE);
                    // textView_video_thumbnail.setVisibility(View.GONE);
                    editText_content_write_url.setVisibility(View.VISIBLE);
                    textView_content_url.setVisibility(View.VISIBLE);
                    button_write_finish.setVisibility(View.VISIBLE);
                    button_write_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //조건은 key값의 데이터가 null값일 때 돌아간다  , key값의 데이터가 null이 아닐때 돌아간다로 2개 만들기
                            //shared > 어레이 > 오브젝트
                            String readData;
                            SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
                            readData = sharedPreferences.getString("image_item", "");
                            if (readData.isEmpty()) {

                                // SharedPreferences 생성
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONArray jsonArray = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("image_title", editText_write_content_title.getText().toString());
                                    jsonObject.put("url_image", editText_content_write_url.getText().toString());
                                    jsonArray.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.putString("image_item", jsonArray.toString());
                                editor.apply();
                            } else if(!readData.isEmpty()){

                                // SharedPreferences 생성
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONArray jsonArray = null;
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonArray = new JSONArray(readData);
                                    jsonObject.put("image_title", editText_write_content_title.getText().toString());
                                    jsonObject.put("url_image", editText_content_write_url.getText().toString());
                                    jsonArray.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.putString("image_item", jsonArray.toString());
                                editor.apply();
                            }
                            //화면전환 (intent 객체 생성)
                            Intent intent = new Intent(WriteVideo.this, FunnyStory.class);

                            if (editText_write_content_title.getText().toString().equals("")) {
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                                dialogBuilder.setMessage("게시물을 잘 넣었는지 확인해 주세요");

                                //다이얼로그 확인 버튼
                                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.show();
                            } else {
                                //보내는 Activity 시작
                                startActivity(intent);
                                //데이터 추가 확인 토스트 띄우기
                                Toast.makeText(WriteVideo.this, "목록에 추가되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                           /* //화면전환 (intent 객체 생성)
                            Intent i_funnyStory = new Intent(WriteVideo.this, FunnyStory.class);
                            Intent i_enterStory = new Intent(WriteVideo.this, FunnyStory.class);

                            //입력된 데이터 받기 - EditText의 text를 저장
                            String image_title_funnyStory = editText_write_content_title.getText().toString();
                            String image_title_enterStory = editText_write_content_title.getText().toString();
                            //Intent를 통하여 전달하는 방식 - EnterVideo로 전달할 인텐트 생성
                            i_funnyStory.putExtra("image_title", image_title_funnyStory);
                            i_enterStory.putExtra("image_title", image_title_enterStory);

                            //보내는 Activity 시작
                            startActivity(i_funnyStory);
                            startActivity(i_enterStory);*/
                        }
                    });
                }
            }
        });

       /* button_write_finish.setOnClickListener(new View.OnClickListener() {
             @Override
              public void onClick(View v) {

//                  제목 내용을 포함한 에디트 텍스트 주소 설정.
//                editText = (EditText) findViewById(R.id.memo_title_content);
//                // 에디트 텍스트에 입력된 문자열 값을 받는 TEXT 변수.
//                TEXT = editText.getText().toString();
//                //TEXT 변수 값을 putExtra로 인텐트.
//                intent.putExtra("memo_title_content", TEXT);

                 //화면전환 (intent 객체 생성)
                 Intent i=new Intent(WriteVideo.this, FunnyVideo.class);

                //입력된 데이터 받기 - EditText의 text를 저장
                 String video_title = textView_video_title.getText().toString();
                 //String video_title_url = editText_video_url.getText().toString();

                 //Intent를 통하여 전달하는 방식 - EnterVideo로 전달할 인텐트 생성
                i.putExtra("video_title",video_title);
                //i.putExtra("video)title_url",video_title_url);

                //보내는 Activity 시작
                setResult(0,i);
                finish();
            }
        });*/
    }
    /*
    //ArrayList를 Json으로 변환하여 SharedPreferences에 String을 저장하는 코드
    private void setStringArrayPref(String key, values) {
        SharedPreferences prefs = getSharedPreferences("video_item",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }*/

    //웃긴동영상 모음 화면으로 전환
    public void clickMethod_video_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);

        startActivity(intent);
    }

    //URL 받는 함수
    private void initIntent() {

        // 인텐트를 얻어오고, 액션과 MIME 타입을 가져온다
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // 인텐트 정보가 있는 경우 실행
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {

                // 가져온 인텐트의 텍스트 정보
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

                // TODO : 위에 받아온 텍스트로 원하는 기능 구현
                editText_content_write_url.setText(sharedText);
            } else if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                editText_content_write_url.setText((CharSequence) imageUri);
            } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
                if (type.startsWith("image/")) {
                    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
                    editText_content_write_url.setText((CharSequence) imageUris);
                }
            }
        }
    }
}
