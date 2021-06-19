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
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FunnyVideo extends AppCompatActivity  {

    MainAdapter adapter;
    RecyclerView recyclerView;
    EditText editText_content_title, editText_edit_title;
    SharedPreferences sharedPreferences;
    ArrayList<Contents> contents;
    Context context;
    private TextView textView_video_title;
    private ImageView imageView_video_image;
    String readData, url_video_id;
    TextView banner_tip;
    private static Handler handler;
    int num = 0;

    public VideoView item_video_play;

    /*  //유투브 썸네일
      public String urlYouTube = "";   //썸네일뷰에서 보여질 유투브 ID(v= 다음에 나오는 ID)를 넣을 변수
      String youtube_key = "AIzaSyAtD8h2PT8AqDwz_R2H9sKFYzpXTPMqH2U";*/

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_video);

        contents = new ArrayList<>();
        editText_edit_title = findViewById(R.id.editText_edit_title);   //Edit클래스 제목 값
        editText_content_title = findViewById(R.id.editText_content_title);  // 작성창 제목 입력값
        textView_video_title = findViewById(R.id.item_video_title); // item 클래스 제목 값
        imageView_video_image = findViewById(R.id.item_video_image); //item 클래스 이미지 값

        recyclerView = findViewById(R.id.recyclerview_video);   //FunnyVideo 클래스 리싸이클러뷰 창

       // item_video_play=findViewById(R.id.item_video_play);

        //RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        //FunnyStory로 화면전환
        Button moveButton_funny_story = (Button) findViewById(R.id.button_move_story);
        moveButton_funny_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FunnyStory.class);
                startActivity(intent);
            }
        });

        Button moveButton_write_video = (Button) findViewById(R.id.button_move_write_video);
        //내용 추가 버튼 눌렀을 때 WirteVideo로 화면전환
        moveButton_write_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteVideo.class);
                startActivity(intent);
            }
        });

        //설명 쓰레드 생성
        Thread tipThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String tip_1 = "Tip.유투브 동영상을 저장하는 공간입니다";
                String tip_2 = "Tip.동영상URL은 유투브에서만 가져와주세요";
                String tip_3 = "Tip.공유하기를 통해 카카오톡으로 자료를 공유할 수 있습니다";
                String tip_4 = "Tip.게시글을 편집하시려면 제목을 꾹 눌러주세요";

                banner_tip = findViewById(R.id.banner_tip);
                //쓰레드 반복
                while (true) {
                    if (num == 0) {
                        banner_tip.setText(tip_1);
                        num++;
                    } else if (num == 1) {
                        banner_tip.setText(tip_2);
                        num++;
                    } else if (num == 2) {
                        banner_tip.setText(tip_3);
                        num++;
                    } else if (num == 3) {
                        banner_tip.setText(tip_4);
                        num = 0;
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //설명 쓰레드 시작
        tipThread.start();


        //리싸이클러뷰 만들기
        // SharedPreferences 생성
        sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);

        //video_item 폴더에서 "video_item"로 저장되어 있는 리스트 항목을 읽는다.
        readData = sharedPreferences.getString("video_item", "");
        Log.d("Tag", "readData 검사");
        //SharedPreferences 에 "video_item"에 저장되어 있는 데이터를 모두 읽어온다.
        //데이터 형식은 Array
        try {
            JSONArray jsonArray = new JSONArray(readData);
            //SharedPreferences 에서 불러온 데이터를 JSONArray 형식으로 읽는다.
            for (int i = 0; i < jsonArray.length(); i++) {
                //jsonArray 에 포함되어 있는 리스트의 갯수를 파악하여 객체형태로 변경한다.
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                contents.add(new Contents(
                        jsonObject.getString("video_title"),
                        jsonObject.getString("url_video")));
            /*    String id = jsonObject.getString("url_video");
                String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";  //유튜브 썸네일 불러오는 방법
                Glide.with(context).load(url).into(MainAdapter.ItemViewHo);*/
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //로그 사용법 Log.d("video_title", jsonObject.getString("video_title"));

        //RecyclerView의 Adapter 세팅
        adapter = new MainAdapter(contents, this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

       /* imageView_video_image.setFocusable(true);
        //FocusChangeEvent > Focus의 status가 바뀌는 Event를 인식하는 Listener
        imageView_video_image.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view,boolean hasFocus){//포커스가 한 뷰에서 다른 뷰로 바뀔 때
                if(hasFocus){
                    imageView_video_image.setVisibility(View.GONE);
                    item_video_play.setVisibility(View.VISIBLE);
                }
            }
        });*/

    /*    //포커스 변경
        setFocusEvent();
    */
    }

   /* private void setFocusEvent() {
        View.OnFocusChangeListener focusChangeListener =new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                  v.setVisibility(View.GONE);
                    //                  //v.setVisibility(View.VISIBLE);
                  }
            }
        };
        imageView_video_image.setOnFocusChangeListener(focusChangeListener);
    }*/

    //EnterVideo로 화면전환
    public void clickMethod_video(View view) {

        Intent intent = new Intent(getApplicationContext(), EnterVideo.class);

        startActivity(intent);
    }

}

              /*  String video_title = data.getStringExtra("video_title");
                //String video_url = data.getStringExtra("video_url");

                Contents contents_video_title = new Contents(video_title);
                //Contents contents_video_url = new Contents(video_url);
                adapter.notifyDataSetChanged();
                items.add(0, contents_video_title); //RecyclerView의 첫 줄에 삽입
*/

        /*유투브 썸네일
        YouTubeThumbnailView thumbnailView;
        thumbnailView = (YouTubeThumbnailView)findViewById(R.id.imageView_video_image);

        setYoutubeThumnail(thumbnailView, videoID);

        setListener();*/


              /* String video_title = data.getStringExtra("video_title");

                //getSharedPreference로 원하는 이름의 Preference를 가져올 수 있다.
                //title이란 xml파일에 데이터를 저장 시킨다.
                SharedPreferences sharedPreferences = getSharedPreferences("title",MODE_PRIVATE);
                SharedPreferences.Editor dataEditor = sharedPreferences.edit();
                JSONArray a = new JSONArray();
                //------------GSON 추가해보자
                for (int i = 0; i < items.size(); i++) {
                    a.put(items.get(i));
                }
                if (!items.isEmpty()) {
                    dataEditor.putString("video_title",video_title);
                } else {
                    dataEditor.putString(video_title, null);
                }
                dataEditor.apply();*/

                /*String video_title = data.getStringExtra("video_title");
                // textView_video_title.setText(video_title);
                Cnts contents =new Contents(video_title);
                adapter.notifyDataSetChanged();
                items.add(0, contents); //RecyclerView의 첫 줄에 삽입
*/

/*
                //데이터 쉐어드에 주지 않고 그냥 아이템 추가
                //String video_title = data.getStringExtra("video_title");
                Contents contents =new Contents(video_title);
                adapter.notifyDataSetChanged();
                items.add(0, contents); //RecyclerView의 첫 줄에 삽입
*/


/*      //유투브 썸네일
        //onInitializationSuccess,onInitializationFailure method는 아래 thumbnailView를 initialize 해야 동작한다
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
        youTubeThumbnailLoader.setVideo(urlYouTube);

        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
             //   Log.d(TAG, "onThumbnailLoaded");
                youTubeThumbnailLoader.release();
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
               // Log.d(TAG, "onThumbnailfail");
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
//                if (errorDialog == null || !errorDialog.isShowing()) {
//                    errorDialog = youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST);
//                    errorDialog.show();
//                }
            //Log.d(TAG, "error Dialog");
        } else {
            String errorMessage = String.format("에러", youTubeInitializationResult.toString());
            //Log.d(TAG,"errorMessage");
        }
    }

    // 코드는 유투브ID가 유효하면 동작. String이 비어있지 않으면 동작.
    public void setYoutubeThumnail(YouTubeThumbnailView thumbnailView, final String urlYouTube){

        // 만약 firebase에서 값을 가져오지못한다면...

        if (!"".equals(urlYouTube)) {

            thumbnailView.initialize(getString(R.string.youtube_key), this);

        }
    }*/



    /*@Override
    protected void onPause() {
        super.onPause();

        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, array);
        Log.d(TAG, "Put jsonData");
    }
*/

  /*@Override
    protected void onPause() {
        super.onPause();
        finish();
    }*/
/*
    //onResume은 onPause에서 sharedpreferences에 임시저장된 데이터를 불러오는데 주로 사용
    @Override
    protected void onResume() {
        super.onResume();
    }*/


/*
    //텍스트뷰 제목 인텐트 받아서 데이터 올리기

    Intent intent = getIntent();
    String video_title = intent.getStringExtra("video_title");

        textView_video_title.setText(video_title);

        *//*if (textView_video_title != null) {
            textView_video_title.setText(video_title);*/



 /*   public void OnclickHandler(View view){
        Intent intent =new Intent(this,WriteVideo.class);
        startActivityForResult(intent,Code.requestCode);
    }

    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent resultIntent){
        if(requsetCode==Code.requestCode && resultCode == Code.resultCode){
            list_textView_video_title.setText(resultIntent.getStringExtra("video_title"));
        }
    }*/





//toolbar 관련
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.content_add:
                Intent intent = new Intent(getApplicationContext(),WriteVideo.class);
                startActivity(intent);
                //비디오 작성창 선택
                break;
            case R.id.content_delete:
                //내용 삭제
                break;
             }
        return super.onOptionsItemSelected(item);
    }*/



/*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funny_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main_menu);
        toolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.content_add :
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ActionBar icon_menu = getSupportActionBar();
    icon_menu.setIcon(R.drawable.menu);
    icon_menu.setDisplayUseLogoEnabled(true);
    icon_menu.setDisplayShowHomeEnabled(true)*/










