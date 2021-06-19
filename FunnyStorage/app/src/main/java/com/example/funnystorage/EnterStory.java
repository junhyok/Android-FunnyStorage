package com.example.funnystorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
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

public class EnterStory extends AppCompatActivity {
    private TextView textview_story_title;
    View kakao_link_image,facebook_link_image;
    String image_position, url_image, image_title, image_story_position,_image_id;
    TextView enterStory_title;
    private ImageView enterStory_image;
    String imageUrl;
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_story);

        kakao_link_image = findViewById(R.id.imageView_kakao_link_story);
        facebook_link_image=findViewById(R.id.imageView_facebook_link_story);
        enterStory_image = findViewById(R.id.enterStory_image);
        enterStory_title=findViewById(R.id.textView_story_title);
        /*Button button_share_image = (Button) findViewById(R.id.button_share_image);
        button_share_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra(Intent.EXTRA_TITLE,"웃긴모음 저장소");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "앱을 선택해 주세요"));
            }
        });*/

        //어댑터에서 보내온 인텐트 데이터 가져오기
        final Intent intent = getIntent();
        image_title = intent.getStringExtra("image_title");
        image_position = intent.getStringExtra("image_position");
        url_image = intent.getStringExtra("url_image");


        //쉐어드에 저장한 데이터 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
        String readData = sharedPreferences.getString("image_item", "");

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray(readData);
            //선택한 이미지 포지션을 변수 position에 넣기
            //TODO 자꾸 오류난다. 확인 바람
            JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(image_position));
            //포지션에 url_image,image_title 정보 넣기
            position.getString("url_image");

            jsonArray.put(Integer.parseInt(image_position), jsonObject);

           //가져온 데이터를 image_title이라는 String 변수에 넣기
            image_title= position.getString("image_title");
            //텍스트뷰에 데이터를 붙임
            enterStory_title.setText("제목: "+image_title);

            // Glide로 이미지 표시하기
            // 이미지는 사이트로 들어가서 직접 url을 가져와야한다.
            // 직접 블로그나 사이트를 들어가서 url을 가져오지 않으면 사진이 나오지 않는다.
            String imageUrl = position.getString("url_image");
            Glide.with(this).load(imageUrl).into(enterStory_image);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }   //onCreate



    //화살표 이미지를 누르면 FunnyStory로 화면 전환
    public void clickMethod_story_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyStory.class);
        startActivity(intent);
    }

    // 카카오 링크 보내기-스토리
    public void clickMethod_kakao_link_story(View view) {

        //쉐어드에 저장한 데이터 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
        String readData = sharedPreferences.getString("image_item", "");

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray(readData);
            //선택한 이미지 포지션을 변수 position에 넣기
            //TODO 자꾸 오류난다. 확인 바람
            JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(image_position));

            //포지션에 url_image 정보 넣기
            position.getString("url_image");

            jsonArray.put(Integer.parseInt(image_position), jsonObject);

            url_image = position.getString("url_image");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        //텍스트템플릿 (글)
        LinkObject link = LinkObject.newBuilder()
                //  .setWebUrl("https://developers.kakao.com")
                //  .setMobileWebUrl("https://www.youtube.com/")
                .build();
        TemplateParams params = TextTemplate.newBuilder("웃긴 모음 저장소 입니다.         "+"    "+"     URL 들어가셔서 재밌게 봐주세요                 "+url_image,link)
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

    //페이스북 링크 보내기 메소드-스토리
    public void clickMethod_facebook_link_story(View view){
        //쉐어드에 저장한 데이터 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
        String readData = sharedPreferences.getString("image_item", "");

        JSONObject jsonObject = new JSONObject();

        try {
            JSONArray jsonArray = new JSONArray(readData);
            //선택한 이미지 포지션을 변수 position에 넣기
            //TODO 자꾸 오류난다. 확인 바람
            JSONObject position = (JSONObject) jsonArray.get(Integer.parseInt(image_position));

            //포지션에 url_image 정보 넣기
            position.getString("url_image");

            jsonArray.put(Integer.parseInt(image_position), jsonObject);

            url_image = position.getString("url_image");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ShareLinkContent content = new ShareLinkContent.Builder()
                //공유될 링크
                .setContentUrl(Uri.parse(url_image))
                .build();
        ShareDialog shareDialog=new ShareDialog(this);
        shareDialog.show(content,ShareDialog.Mode.FEED);
    }
}
        /*//피드템플릿 (이미지+글)
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
                .build();
*/
        //공유할 URL
        // String url="https://developers.kakao.com";

        /*Map<String, String> serverCallbackArgs = new HashMap<String, String>();
        serverCallbackArgs.put("user_id", "${current_user_id}");
        serverCallbackArgs.put("product_id", "${shared_product_id}");*/



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
                Intent intent = new Intent(getApplicationContext(),WriteStory.class);
                startActivity(intent);
                //내용 수정하기
                break;
        }
        return super.onOptionsItemSelected(item);
    }*/

