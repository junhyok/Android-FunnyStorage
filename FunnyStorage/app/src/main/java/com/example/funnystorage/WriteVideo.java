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

                //????????? ????????? ?????? ????????? ???
                if (i == R.id.radioButton_video) {
                    editText_content_write_url.setVisibility(View.VISIBLE);
                    textView_content_url.setVisibility(View.VISIBLE);
                    // editText_video_image_url.setVisibility(View.INVISIBLE);
                    // textView_video_thumbnail.setVisibility(View.VISIBLE);
                    button_write_finish.setVisibility(View.VISIBLE);
                    button_write_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //????????? key?????? ???????????? null?????? ??? ????????????  , key?????? ???????????? null??? ????????? ??????????????? 2??? ?????????
                            //shared > ????????? > ????????????
                            String readData;
                            SharedPreferences sharedPreferences = getSharedPreferences("video_item", MODE_PRIVATE);
                            readData = sharedPreferences.getString("video_item", "");
                            if (readData.isEmpty()) {

                                // SharedPreferences ??????
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

                                // SharedPreferences ??????
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                JSONArray jsonArray = null;
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonArray = new JSONArray(readData);
                                    jsonObject.put("video_title", editText_write_content_title.getText().toString());
                                    jsonObject.put("url_video", editText_content_write_url.getText().toString());
                                   /* String id = jsonObject.getString("url_video");
                                    String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";  //????????? ????????? ???????????? ??????
                                    Glide.with(context).load(url).into();*/
                                    jsonArray.put(jsonObject);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.putString("video_item", jsonArray.toString());
                                editor.apply();
                            }
                            //???????????? (intent ?????? ??????)
                            Intent intent = new Intent(WriteVideo.this, FunnyVideo.class);

                            if (editText_write_content_title.getText().toString().equals("")) {
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                                dialogBuilder.setMessage("???????????? ??? ???????????? ????????? ?????????");

                                //??????????????? ?????? ??????
                                dialogBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.show();
                            } else {
                                //????????? Activity ??????
                                startActivity(intent);
                                //????????? ?????? ?????? ????????? ?????????
                                Toast.makeText(WriteVideo.this, "????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                }

                ////???????????? ??????????????? ??????????????? ?????? > ?????? ????????? ???????????????
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
                                //????????? ????????? ?????? - WriteVide???????????? EditText??? video_title?????? ??????
                                String video_title = editText_write_content_title.getText().toString();
                                String video_url = editText_content_write_url.getText().toString();

                                //Intent??? ????????? ???????????? ?????? - EnterVideo??? ????????? ????????? ??????
                                intent.putExtra("video_title", video_title);
                                intent.putExtra("video_url",video_url);*/

                               /* //????????? ??????
                                SharedPreferences sharedPreferences = getSharedPreferences("video_item",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("video_title","");
                                editor.putString("video_url","");
                                editor.apply();*/

                //????????? ????????? ?????? ????????? ???
                else if (i == R.id.radioButton_image) {
                    // editText_video_image_url.setVisibility(View.GONE);
                    // textView_video_thumbnail.setVisibility(View.GONE);
                    editText_content_write_url.setVisibility(View.VISIBLE);
                    textView_content_url.setVisibility(View.VISIBLE);
                    button_write_finish.setVisibility(View.VISIBLE);
                    button_write_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //????????? key?????? ???????????? null?????? ??? ????????????  , key?????? ???????????? null??? ????????? ??????????????? 2??? ?????????
                            //shared > ????????? > ????????????
                            String readData;
                            SharedPreferences sharedPreferences = getSharedPreferences("image_item", MODE_PRIVATE);
                            readData = sharedPreferences.getString("image_item", "");
                            if (readData.isEmpty()) {

                                // SharedPreferences ??????
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

                                // SharedPreferences ??????
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
                            //???????????? (intent ?????? ??????)
                            Intent intent = new Intent(WriteVideo.this, FunnyStory.class);

                            if (editText_write_content_title.getText().toString().equals("")) {
                                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                                dialogBuilder.setMessage("???????????? ??? ???????????? ????????? ?????????");

                                //??????????????? ?????? ??????
                                dialogBuilder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogBuilder.show();
                            } else {
                                //????????? Activity ??????
                                startActivity(intent);
                                //????????? ?????? ?????? ????????? ?????????
                                Toast.makeText(WriteVideo.this, "????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                           /* //???????????? (intent ?????? ??????)
                            Intent i_funnyStory = new Intent(WriteVideo.this, FunnyStory.class);
                            Intent i_enterStory = new Intent(WriteVideo.this, FunnyStory.class);

                            //????????? ????????? ?????? - EditText??? text??? ??????
                            String image_title_funnyStory = editText_write_content_title.getText().toString();
                            String image_title_enterStory = editText_write_content_title.getText().toString();
                            //Intent??? ????????? ???????????? ?????? - EnterVideo??? ????????? ????????? ??????
                            i_funnyStory.putExtra("image_title", image_title_funnyStory);
                            i_enterStory.putExtra("image_title", image_title_enterStory);

                            //????????? Activity ??????
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

//                  ?????? ????????? ????????? ????????? ????????? ?????? ??????.
//                editText = (EditText) findViewById(R.id.memo_title_content);
//                // ????????? ???????????? ????????? ????????? ?????? ?????? TEXT ??????.
//                TEXT = editText.getText().toString();
//                //TEXT ?????? ?????? putExtra??? ?????????.
//                intent.putExtra("memo_title_content", TEXT);

                 //???????????? (intent ?????? ??????)
                 Intent i=new Intent(WriteVideo.this, FunnyVideo.class);

                //????????? ????????? ?????? - EditText??? text??? ??????
                 String video_title = textView_video_title.getText().toString();
                 //String video_title_url = editText_video_url.getText().toString();

                 //Intent??? ????????? ???????????? ?????? - EnterVideo??? ????????? ????????? ??????
                i.putExtra("video_title",video_title);
                //i.putExtra("video)title_url",video_title_url);

                //????????? Activity ??????
                setResult(0,i);
                finish();
            }
        });*/
    }
    /*
    //ArrayList??? Json?????? ???????????? SharedPreferences??? String??? ???????????? ??????
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

    //??????????????? ?????? ???????????? ??????
    public void clickMethod_video_list(View view) {
        Intent intent = new Intent(getApplicationContext(), FunnyVideo.class);

        startActivity(intent);
    }

    //URL ?????? ??????
    private void initIntent() {

        // ???????????? ????????????, ????????? MIME ????????? ????????????
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        // ????????? ????????? ?????? ?????? ??????
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {

                // ????????? ???????????? ????????? ??????
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);

                // TODO : ?????? ????????? ???????????? ????????? ?????? ??????
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
