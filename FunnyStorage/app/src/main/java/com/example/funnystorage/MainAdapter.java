
package com.example.funnystorage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.annotation.GlideExtension;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.funnystorage.R.layout.recyclerview_item_play;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemViewHolder> /*implements Filterable*/ {
    String url_video, url_video_id, video_position;
    int position;
    ArrayList<Contents> contents;  //아이템 데이터        //unFilteredList
    ArrayList<Contents> filteredList;
    Context context = null;

    private String readData;
    //  public YouTubeThumbnailLoader youTubeThumbnailLoader;
    // String id ;
    //String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";

    public MainAdapter() {

    }



            //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_video_title;
        public TextView url_video;
        public ImageView item_video_image=null,thumbnailView;
        public EditText search_video;
        public VideoView item_video_play;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_video_title = itemView.findViewById(R.id.item_video_title);
            item_video_image = itemView.findViewById(R.id.item_video_image);
            // item_video_image.setFocusable(true);
//            item_video_play = itemView.findViewById(R.id.item_video_play);
            url_video = itemView.findViewById(R.id.editText_content_write_url);
//            youTubePlayerView=itemView.fin
            thumbnailView = itemView.findViewById(R.id.videoView_thumbnail);
            /*item_video_play.getSettings().setJavaScriptEnabled(true);
            item_video_play.setWebChromeClient(new WebChromeClient());*/
          //  search_video=itemView.findViewById(R.id.search_video);


            //아이템 클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        //리스너 객체의 메서드 호출
                        if (mListener != null) {
                            mListener.onItemClick(v, position);
                        }
                    }
                }
            });

         /*   //FocusChangeEvent > Focus의 status가 바뀌는 Event를 인식하는 Listener
            item_video_image.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                @Override
                public void onFocusChange(View view,boolean hasFocus){//포커스가 한 뷰에서 다른 뷰로 바뀔 때
                    if(hasFocus){
                        item_video_image.setVisibility(View.GONE);
                        //item_video_play.setVisibility(View.VISIBLE);
                    }
                }
            });*/

        }
    }

   /* @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredList = contents;
                } else {
                    ArrayList<Contents> filteringList = new ArrayList<>();
                    for (Contents video_title : contents) {
                        if (.toLowerCase().contains(charString.toLowerCase())) {
                            filteringList.add(video_title);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Contents>) results.values;
                notifyDataSetChanged();
            }
        };
    }
*/
    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;


    public MainAdapter (ArrayList<Contents> contents, Context context) {
        this.contents = contents;       //arrayList
        this.context = context;
        this.filteredList=contents;

    }


  /*  //아이템뷰 바꾸는 것을 판단하는 함수
    @Override
    public int getItemViewType(int position){
        ChatMessage chatMessage = contents.get(position);
        if (chatMessage.getuId().equals(mProfileUid)) {
            return 0;
        } else {
            return 1;
        }
    }*/

    @NonNull
    @Override
    //아이템 뷰를 관리하는 ViewHolder객체 생성
    public ItemViewHolder onCreateViewHolder (@NonNull ViewGroup viewGroup, int viewType) {
        //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줍니다.
      /* switch (viewType){
            case 0:
                View view_video_play = LayoutInflater.from(viewGroup.getContext())
                        .inflate(recyclervie  w_item_play,viewGroup,false);
                return new ItemViewHolder(view_video_play);
            case 1:
            View view_video = LayoutInflater.from(viewGroup.getContext())
                    .inflate(recyclerview_item, viewGroup, false);
            return new ItemViewHolder(view_video);
        }*/

        View view_video = LayoutInflater.from(viewGroup.getContext())
                .inflate(recyclerview_item_play, viewGroup, false);
        return new ItemViewHolder(view_video);
    }


    //onBindViewHolder에서는 데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    //position에 해당하는 데이터를 ViewHolder가 관리하는 아이템View에 표시
    //final int position을 사용해서 위치가 바뀌거나 지워질때가 있다.=>holder.getAdapterPosition()을 이용.
    public void onBindViewHolder (@NonNull final ItemViewHolder itemViewHolder, final int position) {
        Log.d("로그", String.valueOf(position));
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        Contents item = contents.get(position);
        itemViewHolder.item_video_title.setText(item.getVideo_title());

        //Glide를 활용한 썸네일
       Glide.with(itemViewHolder.itemView.getContext())
                .load("https://img.youtube.com/vi/" + item.getUrl_video().substring(item.getUrl_video().lastIndexOf("/") + 1) + "/" + "0.jpg")
                .into(itemViewHolder.thumbnailView);
                itemViewHolder.itemView.setTag(position);

       // itemViewHolder.item_video_play.loadData(item.getUrl_video(),"text/html","utf-8");
     //   itemViewHolder.search_video.setText((CharSequence) filteredList.get(position));




       /*   //유투브 플레이어를 사용하여 리싸이클러뷰에 보여주려면 YoutubeBaseActivity를 extend 받아야 하는데 자바는 하나의 엑티비티에 하나의 상속만 가능하다 이중상속 불가능.
            //implement도 마찬가지로 사용해봤더니 불가능했다.

       // 썸네일 재생
        itemViewHolder.item_video_play.initialize("AIzaSyAtD8h2PT8AqDwz_R2H9sKFYzpXTPMqH2U", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

                //쉐어드에 저장한 데이터 가져오기
                SharedPreferences sharedPreferences = context.getSharedPreferences("video_item", MODE_PRIVATE);
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
                            .substring(position.getString("url_video").lastIndexOf("/") + 1);
                    youTubePlayer.loadVideo(url_video_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (youTubePlayer != null) {
                    youTubePlayer.cueVideo(url_video_id);
                }

                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {
                        itemViewHolder.thumbnailView.setVisibility(View.GONE);
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
                        itemViewHolder.item_video_play.setVisibility(View.GONE);
                        itemViewHolder.thumbnailView.setVisibility(View.VISIBLE);
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
*/


/*
        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                itemViewHolder.thumbnailView.setVisibility(View.VISIBLE);
                //itemViewHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        itemViewHolder.item_video_play.initialize("AIzaSyAtD8h2PT8AqDwz_R2H9sKFYzpXTPMqH2U", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                itemViewHolder.item_video_play.loadVideo.getUrl_video().substring(item.getUrl_video().lastIndexOf("/") + 1);
                itemViewHolder.item_video_play.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }

        });
*/

/*

        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(itemViewHolder.item_video_play);
        itemViewHolder.item_video_play.setMediaController(mediaController);
        itemViewHolder.item_video_play.setKeepScreenOn(true);
        //  itemViewHolder.item_video_play.setVideoPath();
        itemViewHolder.item_video_play.setVideoPath(item.getUrl_video());
        itemViewHolder.item_video_play.requestFocus();
        itemViewHolder.item_video_play.seekTo(1000);
        itemViewHolder.item_video_play.start(); //자동재생

        itemViewHolder.item_video_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                itemViewHolder.thumbnailView.setVisibility(View.GONE);
                //you can Hide progress dialog here when video is start playing;

            }
        });
        itemViewHolder.item_video_play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                itemViewHolder.item_video_play.stopPlayback();
                itemViewHolder.thumbnailView.setVisibility(View.VISIBLE);

            }
        });


*/


        /*
        //비디오뷰 사용하다 망친거
        final MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(itemViewHolder.item_video_play);
        itemViewHolder.item_video_play.setMediaController(mediaController);
        itemViewHolder.item_video_play.setKeepScreenOn(true);
        //  itemViewHolder.item_video_play.setVideoPath();
        Uri video = Uri.parse("android.resource://"+ context.getPackageName() + "\"/\"raw/KakaoTalk_20200701_180134066.mp4");
        itemViewHolder.item_video_play.setVideoURI(video);
               // .setVideoPath("http://sites.google.com/site/ubiaccessmobile/sample_video.mp4");
        itemViewHolder.item_video_play.requestFocus();
       // itemViewHolder.item_video_play.seekTo(1000);
        itemViewHolder.item_video_play.start(); //자동재생

        itemViewHolder.item_video_play.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                itemViewHolder.thumbnailView.setVisibility(View.GONE);
                //you can Hide progress dialog here when video is start playing;

            }
        });
        itemViewHolder.item_video_play.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                itemViewHolder.item_video_play.stopPlayback();
                itemViewHolder.thumbnailView.setVisibility(View.VISIBLE);

            }
        });*/


/*
      //포지션을 못잡는다. 아에 없어지지도 않음
        itemViewHolder.item_video_image.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus){
                        if(position ==0){
                       itemViewHolder.item_video_image.setVisibility(View.GONE);

                    }
                }
            }
        });
*/

       /*   //0번 포지션에 있는 아이템만 없애기
            if (position == 0) {
            itemViewHolder.item_video_image.setVisibility(View.GONE);
            itemViewHolder.item_video_image.requestFocus(FOCUS_DOWN);
            // notifyDataSetChanged();
            //itemViewHolder.item_video_play.setVisibility(View.VISIBLE);
        }
*/


        //비디오 리싸이클러뷰 롱클릭 (추가, 수정, 삭제)
        itemViewHolder.item_video_title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("로그", "롱클릭성공");

                final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(v.getContext());
                dialogBuilder.setTitle("게시글 편집");
                dialogBuilder.setMessage("원하시는 작업을 선택해주세오");

                //다이얼로그 취소 버튼
                dialogBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //다이얼로그 삭제 버튼
                dialogBuilder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //입력된 데이터 받기 - EditText의 text를 저장
                        SharedPreferences sharedPreferences = context.getSharedPreferences("video_item", MODE_PRIVATE);
                        readData = sharedPreferences.getString("video_item", "");

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
                        //컨텐츠가 다 없어지려고 하면 앱이 꺼진다.
                        contents.remove(position);

                        notifyItemRemoved(position);
                        dialog.dismiss();
                    }

                    /*  //리싸이클러뷰 아이템 제거 (쉐어드 사용 없이)
                        removeItem(position);
                        notifyItemChanged(position);*/

                });
                //다이얼로그 수정 버튼
                dialogBuilder.setNeutralButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //인텐트로 Edit클래스에 정보 보내기
                        Intent intent = new Intent(context, Edit.class);

                        //선택한 컨텐츠 포지션 가져와 변수 설정하여 값 가져오기.
                        String video_title = contents.get(position).getVideo_title();
                        int video_position = position;
                        url_video = contents.get(position).getUrl_video();

                        //인텐트로 변수에서 가져온 값 넣어주기
                        intent.putExtra("video_title", video_title);     //비디오 제목
                        intent.putExtra("video_position", String.valueOf(video_position));//비디오 포지션
                        intent.putExtra("url_video", url_video);

                        context.startActivity(intent);  //인탠트는 엑티비티에서 엑티비티 이동.

                        dialog.dismiss();
                    }
                });

                dialogBuilder.show();

                //   Toast.makeText(context, "예를 선택 했습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }

        });

        itemViewHolder.thumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 EnterVideo에 값 보내기
                Intent intentEnterVideo = new Intent(context, EnterVideo.class);
                //선택한 컨텐츠 포지션 가져와 변수 설정하여 값 가져오기.
                int video_position = position;
                String url_video = contents.get(position).getUrl_video();
                //인테트로 변수에서 가져온 값 넣어주기
                intentEnterVideo.putExtra("video_position", String.valueOf(video_position));//비디오 포지션
                intentEnterVideo.putExtra("url_video", url_video);

                context.startActivity(intentEnterVideo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

}


    /*//추가
    void addItem(Contents contents){
        //items에 Contents객체 추가
        contents.add (contents);
    }
*/




  /*  //제거
    void removeItem(int position) {
        //items에 Contents객체 추가
        contents.remove(position);
        //추가후 Adapter에 데이터가 변경된것을 알림
        notifyDataSetChanged();
    }*/

 /*   //편집
    void editItem(int position, Contents contents) {
        contents.set(position, contents);
        notifyDataSetChanged();

    }*/




