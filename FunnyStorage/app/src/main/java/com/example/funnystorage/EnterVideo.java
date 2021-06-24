package com.example.funnystorage;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TemplateParams;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EnterVideo extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    Button youtubeButton;
    YouTubePlayer.OnInitializedListener listener;
    String video_position, url_video,url_video_id;
    View kakao_link,facebook_link;

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_video);

        /* //페이스북 sdk사용하기
         FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/

      //  youtubeButton = findViewById(R.id.youtubeButton);
        facebook_link=findViewById(R.id.sharebutton_facebook_link);
         kakao_link = findViewById(R.id.imageView_kakao_link);
        youTubePlayerView = findViewById(R.id.youtubeView);
    //    ShareButton fb_share_button = (ShareButton)findViewById(R.id.button_share_facebook_button);


        final MainAdapter adapter = new MainAdapter();

        //어댑터에서 보내온 인텐트 데이터 가져오기
        final Intent intent = getIntent();
        video_position = intent.getStringExtra("video_position");
        url_video = intent.getStringExtra("url_video");

        youTubePlayerView.initialize("", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

/* //TODO.기능 어느정도 완성되면 전체화면 하기
                //시스템에서 자동조절
                youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                // 유튜브 영상 최대화 허용
                // 기기가 가로방향이 될때 자동전체화면
                youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);
*/
                //쉐어드에 저장한 데이터 가져오기
                SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
                String readData = sharedPreferences.getString("video_item", "");

                JSONObject jsonObject = new JSONObject();

                try {
                    JSONArray jsonArray = new JSONArray(readData);
                    //선택한 비디오 포지션을 변수 position에 넣기
                    //TODO 자꾸 오류난다. 확인 바람. 포지션 값이 제대로 읽혀지지 않는 것 같다.
                    JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(video_position));

                    //포지션에 url_video 정보 넣기
                    position.getString("url_video");

                    jsonArray.put(Integer.parseInt(video_position), jsonObject);

                    //유투브 영상 URL 연결
                    //특정문자 이후의 문자열 제거
                    url_video_id = position.getString("url_video")
                            .substring(position.getString("url_video").lastIndexOf("/")+1);
                    youTubePlayer.loadVideo(url_video_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(youTubePlayer!=null){
                    youTubePlayer.cueVideo(url_video_id);
                }

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String url_video_id) {

                        youTubePlayer.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

            //유투브 URL 링크 입력
            //사용자가 선택한 아이템 포지션 가져와서
            //그 포지션에 해당하는 url_video 데이터 가져와서 동영상 틀어주기
            //쉐어드에 저장되어 있는 url_video를 가져와서 연결

    }

       /* youtubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //유투브 API 키 값
           //     youTubePlayerView.initialize("AIzaSyAtD8h2PT8AqDwz_R2H9sKFYzpXTPMqH2U", listener);
            }
        });*/


    //화살표 이미지를 누르면 FunnyVideo로 화면 전환
    public void clickMethod_video_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);
        intent.putExtra("url_video_id",url_video_id);
        startActivity(intent);
    }



    /*
    //toolbar 관련
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.enter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.enter_edit:
                Intent intent = new Intent(getApplicationContext(),WriteVideo.class);
                startActivity(intent);
                //내용 수정하기
                break;
           }
        return super.onOptionsItemSelected(item);
    }
    */


    //페이스북 링크 보내기 메소드
    public void clickMethod_facebook_link(View view){
        //쉐어드에 저장한 데이터 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
        String readData = sharedPreferences.getString("video_item", "");

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray(readData);
            //선택한 비디오 포지션을 변수 position에 넣기
            //TODO 자꾸 오류난다. 확인 바람
            JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(video_position));

            //포지션에 url_video 정보 넣기
            position.getString("url_video");

            jsonArray.put(Integer.parseInt(video_position), jsonObject);

            url_video = position.getString("url_video");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ShareLinkContent content = new ShareLinkContent.Builder()
                //공유될 링크
                .setContentUrl(Uri.parse(url_video))
                .build();
        ShareDialog shareDialog=new ShareDialog(this);
        shareDialog.show(content,ShareDialog.Mode.FEED);
        
    }


    //카카오 링크 보내기 메소드
    public void clickMethod_kakao_link(View view) {
        //쉐어드에 저장한 데이터 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
        String readData = sharedPreferences.getString("video_item", "");

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray(readData);
            //선택한 비디오 포지션을 변수 position에 넣기
            //TODO 자꾸 오류난다. 확인 바람
            JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(video_position));

            //포지션에 url_video 정보 넣기
            position.getString("url_video");

            jsonArray.put(Integer.parseInt(video_position), jsonObject);

            url_video = position.getString("url_video");
        } catch (JSONException e) {
            e.printStackTrace();
        }
      //텍스트템플릿 (글)
      LinkObject link = LinkObject.newBuilder()
            //  .setWebUrl("https://developers.kakao.com")
              //  .setMobileWebUrl("https://www.youtube.com/")
                .build();
        TemplateParams params = TextTemplate.newBuilder("웃긴 모음 저장소 입니다.         "+"    "+"     URL 들어가셔서 재밌게 봐주세요                 "+url_video,link)
                .setButtonTitle("")
                .build();

        //기본 템플릿으로 카카오 링크 보내기
        KakaoLinkService.getInstance()
                .sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {

            //카카오링크 공유 실패
            @Override
            public void onFailure(ErrorResult errorResult) {
                Logger.e(errorResult.toString());
            }

            //카카오링크 공유 성공
            @Override
            public void onSuccess(KakaoLinkResponse result) {
                // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
            }
        });
    }
}
/*      //피드템플릿 (이미지+글)
        FeedTemplate params = FeedTemplate
                .newBuilder(ContentObject.newBuilder("디저트 사진",
                        "http://mud-kage.kakao.co.kr/dn/NTmhS/btqfEUdFAUf/FjKzkZsnoeE4o19klTOVI1/openlink_640x640s.jpg",
                        LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                .setMobileWebUrl("https://developers.kakao.com").build())
                        .setDescrption("아메리카노, 빵, 케익")
                        .build())
                .setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                        .setSharedCount(30).setViewCount(40).build())
                .addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                .addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("'https://developers.kakao.com")
                        .setMobileWebUrl("'https://developers.kakao.com")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()))
                .build();*/

//공유할 URL
// String url="https://developers.kakao.com";

        /*Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");*/