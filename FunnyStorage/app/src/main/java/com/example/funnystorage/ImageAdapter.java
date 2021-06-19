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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.funnystorage.R.layout.recyclerview_item_story;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemViewHolder> {
    int position;
    ArrayList<ContentsStory> contents;  //아이템 데이터
    Context context = null;
    private String readData;

    public ImageAdapter() {

    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    //리스너 객체 참조를 저장하는 변수
    private ImageAdapter.OnItemClickListener mListener = null;

    public ImageAdapter(ArrayList<ContentsStory> contents, Context context) {
        this.contents = contents;
        this.context = context;
    }

    @NonNull
    @Override
    //아이템 뷰를 관리하는 ViewHolder객체 생성
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //LayoutInflater를 이용해서 원하는 레이아웃을 띄워줍니다.
        View view_image = LayoutInflater.from(viewGroup.getContext()).
                inflate(recyclerview_item_story, viewGroup, false);

        return new ItemViewHolder(view_image);
    }
    //onBindViewHolder에서는 데이터를 레이아웃에 어떻게 넣어줄지


    @Override
    //position에 해당하는 데이터를 ViewHolder가 관리하는 아이템View에 표시
    //final int position을 사용해서 위치가 바뀌거나 지워질때가 있다.=>holder.getAdapterPosition()을 이용.
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, final int position) {
        Log.d("로그", String.valueOf(position));
        //ItemViewHolder가 생성되고 넣어야할 코드들을 넣어줍다.
        // 보통 onBind 함수 안에 모두 넣어줍니다.
        ContentsStory item = contents.get(position);
        //제목 리싸이클러뷰에 보여주는 코드
        itemViewHolder.item_image_title.setText(contents.get(position).getImage_title());

        //이미지에 리싸이클러뷰에 보여주는 코드
        Glide.with(itemViewHolder.itemView.getContext())
                .load(item.getUrl_image())
                .into(itemViewHolder.item_image_image);
         //itemViewHolder.item_image_image.setImageResource(contents.get(position).getImage_image());

        itemViewHolder.itemView.setTag(position);

        //스토리 리싸이클러뷰 롱클릭 (추가, 수정, 삭제)
        itemViewHolder.item_image_title.setOnLongClickListener(new View.OnLongClickListener() {
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
                        SharedPreferences sharedPreferences = context.getSharedPreferences("image_item", MODE_PRIVATE);
                        readData = sharedPreferences.getString("image_item", "");

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(readData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        jsonArray.remove(position);

                        SharedPreferences.Editor dataEditor = sharedPreferences.edit();

                        dataEditor.putString("image_item", jsonArray.toString());
                        dataEditor.apply();
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
                        Intent intent = new Intent(context, EditImage.class);

                        //선택한 컨텐츠 포지션 가져와 변수 설정하여 값 가져오기.
                        String image_title = contents.get(position).getImage_title();
                        int image_position = position;
                        String url_image = contents.get(position).getUrl_image();

                        //인텐트로 변수에서 가져온 값 넣어주기
                        intent.putExtra("image_title", image_title);     //이미지 제목
                        intent.putExtra("image_position", String.valueOf(image_position));//이미지 포지션
                        intent.putExtra("url_image", url_image);

                        context.startActivity(intent);  //인탠트는 엑티비티에서 엑티비티 이동.

                        dialog.dismiss();
                    }
                });

                dialogBuilder.show();

                //   Toast.makeText(context, "예를 선택 했습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }

        });
        itemViewHolder.item_image_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 EnterImage에 값 보내기
                Intent intentEnterImage = new Intent(context, EnterStory.class);
                //선택한 컨텐츠 포지션 가져와 변수 설정하여 값 가져오기.
                int image_position = position;
                String url_image = contents.get(position).getUrl_image();
                String image_title=contents.get(position).getImage_title();
                //인테트로 변수에서 가져온 값 넣어주기
                intentEnterImage.putExtra("image_position", String.valueOf(image_position));//이미지 포지션
                intentEnterImage.putExtra("url_image", url_image);
                intentEnterImage.putExtra("image_title",image_title);

                context.startActivity(intentEnterImage);
            }
        });
    }

    @Override
    //Adapter가 관리하고 있는 데이터 집합의 전체 개수를 리턴
    public int getItemCount() {
        return contents.size();
    }

    /*//추가
    void addItem(Contents contents){
        //items에 Contents객체 추가
        contents.add (contents);
    }
*/
    //아이템 뷰를 저장하는 뷰홀더 클래스
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_image_title;
        public ImageView item_image_image;
        public TextView url_image;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item_image_title = itemView.findViewById(R.id.item_image_title);
            item_image_image = itemView.findViewById(R.id.item_image_image);
            url_image = itemView.findViewById(R.id.editText_content_write_url);

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
        }

    }
}




